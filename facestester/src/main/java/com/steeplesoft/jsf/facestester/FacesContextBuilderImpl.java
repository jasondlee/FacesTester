package com.steeplesoft.jsf.facestester;

import com.sun.faces.config.ConfigureListener;
import java.util.HashMap;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.faces.FactoryFinder;
import static javax.faces.FactoryFinder.FACES_CONTEXT_FACTORY;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpSessionEvent;

public class FacesContextBuilderImpl implements FacesContextBuilder {

    private FacesContextFactory facesContextFactory;
    private ServletContext servletContext;
    private MockHttpSession session;
    private static boolean initialized = false;
    private final ConfigureListener mojarraListener = new ConfigureListener();

    public FacesContextBuilderImpl(ServletContext servletContext) {
        this.servletContext = servletContext;
        this.session = new MockHttpSession();

        initializeFaces(servletContext);
        facesContextFactory = (FacesContextFactory) FactoryFinder.getFactory(FACES_CONTEXT_FACTORY);
    }

    private void initializeFaces(ServletContext servletContext) {
        synchronized (mojarraListener) {
            if (!initialized) {
                mojarraListener.contextInitialized(new ServletContextEvent(servletContext));
                mojarraListener.sessionCreated(new HttpSessionEvent(session));
                initialized = true;
            }
        }
    }

    public FacesContext createFacesContext(String uri, String method, FacesLifecycle lifecycle) {
        HttpServletRequest request = mockServletRequest(uri, method);

        return facesContextFactory.getFacesContext(servletContext, request, new MockHttpServletResponse(),
                                                   lifecycle.getUnderlyingLifecycle());
    }

    public FacesContext createFacesContext(FacesForm form, FacesLifecycle lifecycle) {
        MockHttpServletRequest request = mockServletRequest(form.getUri(), "POST");
        for (Map.Entry<String, String> each : form.getParameterMap().entrySet()) {
            request.addParameter(each.getKey(), each.getValue());
        }

        return facesContextFactory.getFacesContext(servletContext, request, new MockHttpServletResponse(),
                                                   lifecycle.getUnderlyingLifecycle());
    }

    private MockHttpServletRequest mockServletRequest(String uri, String method) {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest(method, uri);
        servletRequest.setServletPath(uri);
        servletRequest.setSession(session);
        mojarraListener.requestInitialized(new ServletRequestEvent(servletContext, servletRequest));
        if (uri != null) {
            addQueryParameters(servletRequest, uri);
        }
        return servletRequest;
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
                String key = null;
                String value = null;
                String parts[] = param.split("=");
                if (parts.length == 1) {
                    servletRequest.addParameter(parts[0], "");
                } else {
                    servletRequest.addParameter(parts[0], parts[1]);
                }
            }

            servletRequest.setQueryString(queryString);
        }
    }
}