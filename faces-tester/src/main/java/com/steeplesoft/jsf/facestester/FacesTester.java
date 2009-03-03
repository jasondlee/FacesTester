package com.steeplesoft.jsf.facestester;

import com.sun.facelets.FaceletViewHandler;
import com.sun.faces.application.ViewHandlerImpl;
import com.sun.faces.config.ConfigureListener;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletContextEvent;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;

import static javax.faces.FactoryFinder.FACES_CONTEXT_FACTORY;
import static javax.faces.FactoryFinder.LIFECYCLE_FACTORY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author jasonlee
 */
public class FacesTester {
    private static final String LIFECYCLE_FACTORY_KEY = "javax.faces.lifecycle.LifecycleFactory";
    private static final String LIFECYCLE_FACTORY_IMPL = "com.sun.faces.lifecycle.LifecycleFactoryImpl";

    private static final String APPLICATION_FACTORY_KEY = "javax.faces.application.ApplicationFactory";
    private static final String APPLICATION_FACTORY_IMPL = "com.sun.faces.application.ApplicationFactoryImpl";

    private static final String FACES_CONTEXT_FACTORY_KEY = "javax.faces.context.FacesContextFactory";
    private static final String FACES_CONTEXT_FACTORY_IMPL = "com.sun.faces.context.FacesContextFactoryImpl";

    private static final String RENDERKIT_FACTORY_KEY = "javax.faces.render.RenderKitFactory";
    private static final String RENDERKIT_FACTORY_IMPL = "com.sun.faces.renderkit.RenderKitFactoryImpl";

    private MockServletContext servletContext;
    private LifecycleFactory lifecycleFactory;
    private FacesContextFactory facesContextFactory;

    public FacesTester() {
        initializeServletContext();

        // TODO:  This probably shouldn't be tied to Mojarra, but we can fix that later once the library actually works
        FactoryFinder.setFactory(LIFECYCLE_FACTORY_KEY, LIFECYCLE_FACTORY_IMPL);
        FactoryFinder.setFactory(APPLICATION_FACTORY_KEY, APPLICATION_FACTORY_IMPL);
        FactoryFinder.setFactory(FACES_CONTEXT_FACTORY_KEY, FACES_CONTEXT_FACTORY_IMPL);
        FactoryFinder.setFactory(RENDERKIT_FACTORY_KEY, RENDERKIT_FACTORY_IMPL);

        lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(LIFECYCLE_FACTORY);
        facesContextFactory = (FacesContextFactory) FactoryFinder.getFactory(FACES_CONTEXT_FACTORY);
    }

    public FacesPage requestPage(String uri) {
        Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
        FacesContext context = createFacesContext(uri, lifecycle);

        lifecycle.execute(context);
        lifecycle.render(context);

        return new FacesPage(context.getViewRoot());
    }
    
    private FacesContext createFacesContext(String uri, Lifecycle lifecycle) {
        FacesContext context = facesContextFactory.getFacesContext(servletContext,
                mockServletRequest(uri), new MockHttpServletResponse(), lifecycle);

        Application application = context.getApplication();
        application.setViewHandler(new FaceletViewHandler(new ViewHandlerImpl()));

        return context;
    }

    private void initializeServletContext() {
        servletContext = new MockServletContext();
        servletContext.addInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
        new ConfigureListener().contextInitialized(new ServletContextEvent(servletContext));
    }

    private MockHttpServletRequest mockServletRequest(String uri) {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest("GET", uri);
        servletRequest.setServletPath(uri);
        return servletRequest;
    }
}