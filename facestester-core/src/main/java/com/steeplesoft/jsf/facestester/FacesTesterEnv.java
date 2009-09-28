/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.jsf.facestester;

import com.steeplesoft.jsf.facestester.context.FacesContextBuilder;
import com.steeplesoft.jsf.facestester.context.FacesContextBuilderFactory;
import com.steeplesoft.jsf.facestester.servlet.CookieManager;
import com.steeplesoft.jsf.facestester.servlet.ServletContextFactory;
import com.steeplesoft.jsf.facestester.servlet.WebDeploymentDescriptor;
import com.steeplesoft.jsf.facestester.servlet.impl.FacesTesterHttpSession;
import com.steeplesoft.jsf.facestester.servlet.impl.FacesTesterServletContext;
import com.steeplesoft.jsf.facestester.util.ServiceProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author jasonlee
 */
public class FacesTesterEnv {

    protected WebDeploymentDescriptor webDescriptor;
    protected FacesContextBuilder facesContextBuilder;
    protected FacesTesterServletContext servletContext;
    protected CookieManager cookieManager;
    protected FacesLifecycle lifecycle;
    private HttpSession session;

    public FacesTesterEnv(WebDeploymentDescriptor webDeploymentDescriptor, CookieManager cookieManager) {
        this.webDescriptor = webDeploymentDescriptor;
        this.cookieManager = cookieManager;

        servletContext = ServletContextFactory.createServletContext(webDeploymentDescriptor);
        // TODO: init servlets?, filters, etc.
        session = new FacesTesterHttpSession(servletContext);

        FacesContextBuilderFactory facesContextBuilderFactory = ServiceProvider.getUniqueProvider(FacesContextBuilderFactory.class);
        this.facesContextBuilder = facesContextBuilderFactory.createFacesContextBuilder(servletContext, this.session, webDeploymentDescriptor, cookieManager);
        startupServletContext(servletContext, session, webDeploymentDescriptor);

        FacesLifecycleFactory facesLifecycleFactory = null;
        try {
            facesLifecycleFactory = ServiceProvider.getUniqueProvider(FacesLifecycleFactory.class);
        } catch (FacesTesterException e) { //consider making a more specific exception
            Logger.getLogger(FacesTester.class.getName()).warning("No SPI found for " + FacesLifecycleFactory.class.getName() + ". Trying the default");
            facesLifecycleFactory = new FacesTesterLifecycleFactory();
        }
        lifecycle = facesLifecycleFactory.createLifecycle();

    }

    public void release() {
        shutdownServletContext(servletContext, webDescriptor);
        facesContextBuilder.release();
    }

    private void startupServletContext(ServletContext servletContext, HttpSession session, WebDeploymentDescriptor descriptor) {
        ServletContextEvent sce = new ServletContextEvent(servletContext);
        HttpSessionEvent hse = new HttpSessionEvent(session);

        List<EventListener> listeners = descriptor.getListeners();

        callInitializedOnListeners(ServletContextListener.class, listeners, sce);
        callInitializedOnListeners(HttpSessionListener.class, listeners, hse);
    }

    public void shutdownServletContext(ServletContext servletContext, WebDeploymentDescriptor descriptor) {
        FacesContext facesContext = facesContextBuilder.getCurrentFacesContext();
        ServletContextEvent sce = new ServletContextEvent(servletContext);
        List<EventListener> listeners = new ArrayList<EventListener>();
        Collections.addAll(listeners, descriptor.getListeners().toArray(new EventListener[0]));
        Collections.reverse(listeners);
        if (facesContext != null) {
            HttpSessionEvent hse = new HttpSessionEvent((HttpSession) facesContext.getExternalContext().getSession(false));
            ServletRequestEvent sre = new ServletRequestEvent(servletContext, (ServletRequest) facesContext.getExternalContext().getRequest());
            callDestroyedOnListeners(ServletRequestListener.class, listeners, sre);
            callDestroyedOnListeners(HttpSessionListener.class, listeners, hse);
        }
        callDestroyedOnListeners(ServletContextListener.class, listeners, sce);

    }

    private <T> void callInitializedOnListeners(Class<T> type, List<EventListener> list, Object param) {
        for (EventListener listener : list) {
            // First determine if the listener is one we care about right now
            if (type.isInstance(listener)) {
                // It is, so we test to see what kind it is, then call the desired method, using
                // the appropriate cast
                if (type.isAssignableFrom(ServletRequestListener.class)) {
                    ((ServletRequestListener) listener).requestInitialized((ServletRequestEvent) param);
                } else if (type.isAssignableFrom(HttpSessionListener.class)) {
                    ((HttpSessionListener) listener).sessionCreated((HttpSessionEvent) param);
                } else if (type.isAssignableFrom(ServletContextListener.class)) {
                    ((ServletContextListener) listener).contextInitialized((ServletContextEvent) param);
                }
            }
        }
    }

    private static <T> void callDestroyedOnListeners(Class<T> type, List<EventListener> list, Object param) {
        for (EventListener listener : list) {
            // See callInitializedOnListeners for an explanation of this logic
            if (type.isInstance(listener)) {
                if (type.isAssignableFrom(ServletRequestListener.class)) {
                    ((ServletRequestListener) listener).requestDestroyed((ServletRequestEvent) param);
                } else if (type.isAssignableFrom(HttpSessionListener.class)) {
                    ((HttpSessionListener) listener).sessionDestroyed((HttpSessionEvent) param);
                } else if (type.isAssignableFrom(ServletContextListener.class)) {
                    ((ServletContextListener) listener).contextDestroyed((ServletContextEvent) param);
                }
            }
        }
    }

    public FacesContextBuilder getFacesContextBuilder() {
        return facesContextBuilder;
    }

    public WebDeploymentDescriptor getWebDeploymentDescriptor() {
        return webDescriptor;
    }

    public CookieManager getCookieManager() {
        return cookieManager;
    }

    public FacesLifecycle getLifecycle() {
        return lifecycle;
    }

    public FacesTesterServletContext getServletContext() {
        return servletContext;
    }
}
