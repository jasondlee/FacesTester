package com.steeplesoft.jsf.facestester;

import static com.steeplesoft.jsf.facestester.servlet.ServletContextFactory.createServletContext;

import javax.faces.FactoryFinder;
import static javax.faces.FactoryFinder.LIFECYCLE_FACTORY;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.LifecycleFactory;
import static javax.faces.lifecycle.LifecycleFactory.DEFAULT_LIFECYCLE;

/**
 * @author jasonlee
 */
public class FacesTester {
    private FacesLifecycle lifecycle;
    private FacesContextBuilder facesContextBuilder;

    public FacesTester() {
        facesContextBuilder = new FacesContextBuilderImpl(createServletContext());

        LifecycleFactory factory = (LifecycleFactory) FactoryFinder.getFactory(LIFECYCLE_FACTORY);
        lifecycle = new FacesLifecycleImpl(factory.getLifecycle(DEFAULT_LIFECYCLE));
    }

    public FacesPage requestPage(String uri) {
        FacesContext context = facesContextBuilder.createFacesContext(uri, "GET", lifecycle);

        lifecycle.execute(context);
        lifecycle.render(context);

        return new FacesPage(context, facesContextBuilder, lifecycle, uri);
    }
}
