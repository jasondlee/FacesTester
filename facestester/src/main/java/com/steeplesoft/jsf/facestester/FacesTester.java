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
        FacesContext context = createFacesContext(uri, lifecycle);

        lifecycle.execute(context);
        lifecycle.render(context);

        return new FacesPage(context.getViewRoot());
    }

    private FacesContext createFacesContext(String uri, Lifecycle lifecycle) {
        HttpServletRequest servletRequest = mockServletRequest(uri);

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

    private MockHttpServletRequest mockServletRequest(String uri) {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest("GET", uri);
        servletRequest.setServletPath(uri);
        return servletRequest;
    }
}
