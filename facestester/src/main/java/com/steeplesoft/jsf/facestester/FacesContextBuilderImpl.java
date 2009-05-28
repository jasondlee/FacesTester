package com.steeplesoft.jsf.facestester;

import com.steeplesoft.jsf.facestester.servlet.WebDeploymentDescriptor;
import com.sun.faces.config.ConfigureListener;
import java.util.EventListener;
import java.util.List;
import javax.faces.FacesException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.faces.FactoryFinder;
import static javax.faces.FactoryFinder.FACES_CONTEXT_FACTORY;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpSessionEvent;
import java.util.Map;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionListener;

public class FacesContextBuilderImpl implements FacesContextBuilder {

    private static boolean initialized = false;
    private final ConfigureListener mojarraListener = new ConfigureListener();
    private FacesContextFactory facesContextFactory;
    private MockHttpSession session;
    private ServletContext servletContext;
    private WebDeploymentDescriptor webDescriptor;

    public FacesContextBuilderImpl(ServletContext servletContext, WebDeploymentDescriptor webDescriptor) {
        System.setProperty("com.sun.faces.InjectionProvider", "com.steeplesoft.jsf.facestester.injection.FacesTesterInjectionProvider");
        this.servletContext = servletContext;
        this.session = new MockHttpSession();
        this.webDescriptor = webDescriptor;

        initializeFaces(servletContext);
        facesContextFactory = (FacesContextFactory) FactoryFinder.getFactory(FACES_CONTEXT_FACTORY);
    }

    public FacesContext createFacesContext(String uri, String method,
            FacesLifecycle lifecycle) {
        MockHttpServletRequest request = mockServletRequest(uri, method);

        return buildFacesContext(request, lifecycle);
    }

    public FacesContext createFacesContext(FacesForm form,
            FacesLifecycle lifecycle) {
        MockHttpServletRequest request = mockServletRequest(form.getUri(), "POST");

        for (Map.Entry<String, String> each : form.getParameterMap().entrySet()) {
            request.addParameter(each.getKey(), each.getValue());
        }

        return buildFacesContext(request, lifecycle);
    }

    protected FacesContext buildFacesContext(MockHttpServletRequest request, FacesLifecycle lifecycle) throws FacesException {
        FacesContext context = facesContextFactory.getFacesContext(servletContext, request,
                new MockHttpServletResponse(), lifecycle.getUnderlyingLifecycle());
        return context;
    }

    /*
     * This is a pretty simple solution that will likely need to be replaced,
     * but should get us going for now.
     */
    private void addQueryParameters(MockHttpServletRequest servletRequest, String uri) {
        int qmark = uri.indexOf("?");

        if (qmark > -1) {
            String queryString = uri.substring(qmark + 1);
            String[] params = queryString.split("&");

            for (String param : params) {
                String[] parts = param.split("=");

                if (parts.length == 1) {
                    servletRequest.addParameter(parts[0], "");
                } else {
                    servletRequest.addParameter(parts[0], parts[1]);
                }
            }

            servletRequest.setQueryString(queryString);
        }
    }

    private void initializeFaces(ServletContext servletContext) {
        ServletContextEvent sce = new ServletContextEvent(servletContext);
        HttpSessionEvent hse = new HttpSessionEvent(session);
        List<EventListener> listeners = webDescriptor.getListeners();

        synchronized (mojarraListener) {
            if (!initialized) {
                mojarraListener.contextInitialized(new ServletContextEvent(
                        servletContext));
                mojarraListener.sessionCreated(new HttpSessionEvent(session));
                initialized = true;
            }
        }

        for (EventListener listener : listeners) {
            if (listener instanceof ServletContextListener) {
                ((ServletContextListener) listener).contextInitialized(sce);
            }
        }
        // Do all SCLs need to be called first?  Probably can't hurt...
        for (EventListener listener : listeners) {
            if (listener instanceof HttpSessionListener) {
                ((HttpSessionListener) listener).sessionCreated(hse);
            }
        }
    }

    private MockHttpServletRequest mockServletRequest(String uri, String method) {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest(method,
                uri);
        servletRequest.setServletPath(uri);
        servletRequest.setSession(session);
        mojarraListener.requestInitialized(new ServletRequestEvent(
                servletContext, servletRequest));

        if (uri != null) {
            addQueryParameters(servletRequest, uri);
        }

        return servletRequest;
    }
}
