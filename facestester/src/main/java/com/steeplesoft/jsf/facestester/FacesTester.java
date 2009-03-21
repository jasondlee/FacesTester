package com.steeplesoft.jsf.facestester;

import com.steeplesoft.jsf.facestester.servlet.ServletContextFactory;
import com.sun.faces.config.ConfigureListener;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jasonlee
 */
public class FacesTester {
    private ServletContext servletContext;
    private LifecycleFactory lifecycleFactory;
    private FacesContextFactory facesContextFactory;

    public FacesTester() {
        initializeServletContext();
    }

    public FacesPage requestPage(String uri) {
        Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
        FacesContext context = createFacesContext(uri, "GET", lifecycle);

        lifecycle.execute(context);
        lifecycle.render(context);

        return new FacesPage(context);
    }

    public FacesContext createFacesContext(String uri, String method, Lifecycle lifecycle) {
        HttpServletRequest servletRequest = mockServletRequest(uri, method);

        return facesContextFactory.getFacesContext(servletContext,
                servletRequest, new MockHttpServletResponse(), lifecycle);

    }

    private void initializeServletContext() {
        servletContext = new ServletContextFactory().createContext();
        ConfigureListener mojarraListener = new ConfigureListener();
        mojarraListener.contextInitialized(new ServletContextEvent(servletContext));
        lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        facesContextFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
    }

    private static MockHttpServletRequest mockServletRequest(String uri, String method) {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest(method, uri);
        servletRequest.setServletPath(uri);
        return servletRequest;
    }
}
