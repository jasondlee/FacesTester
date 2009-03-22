package com.steeplesoft.jsf.facestester;

import com.sun.faces.config.ConfigureListener;
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

public class FacesContextBuilderImpl implements FacesContextBuilder {
    private FacesContextFactory facesContextFactory;
    private ServletContext servletContext;
    private MockHttpSession session;

    public FacesContextBuilderImpl(ServletContext servletContext) {
        this.servletContext = servletContext;
        this.session = new MockHttpSession();

        initializeFaces(servletContext);
        facesContextFactory = (FacesContextFactory) FactoryFinder.getFactory(FACES_CONTEXT_FACTORY);
    }

    private void initializeFaces(ServletContext servletContext) {
        ConfigureListener mojarraListener = new ConfigureListener();
        mojarraListener.contextInitialized(new ServletContextEvent(servletContext));
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
        return servletRequest;
    }
}
