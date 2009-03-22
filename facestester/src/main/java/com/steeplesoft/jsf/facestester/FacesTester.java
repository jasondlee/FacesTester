package com.steeplesoft.jsf.facestester;

import static com.steeplesoft.jsf.facestester.servlet.ServletContextFactory.createServletContext;

import javax.faces.FactoryFinder;
import static javax.faces.FactoryFinder.LIFECYCLE_FACTORY;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import static javax.faces.lifecycle.LifecycleFactory.DEFAULT_LIFECYCLE;

/**
 * @author jasonlee
 */
public class FacesTester {
    private LifecycleFactory lifecycleFactory;
    private FacesContextBuilder facesContextBuilder;

    public FacesTester() {
        facesContextBuilder = new FacesContextBuilder(createServletContext());
        lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(LIFECYCLE_FACTORY);
    }

    public FacesPage requestPage(String uri) {
        Lifecycle lifecycle = lifecycleFactory.getLifecycle(DEFAULT_LIFECYCLE);
        FacesContext context = facesContextBuilder.createFacesContext(uri, "GET", lifecycle);

        lifecycle.execute(context);
        lifecycle.render(context);

        return new FacesPage(context, facesContextBuilder, uri);
    }
}
