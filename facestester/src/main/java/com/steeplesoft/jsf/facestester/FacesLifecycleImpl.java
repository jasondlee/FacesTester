package com.steeplesoft.jsf.facestester;

import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;


/**
 * TODO:  This class needs its raison d'etre documented. :)
 * @author jasonlee
 */
public class FacesLifecycleImpl implements FacesLifecycle {
    private Lifecycle lifecycle;

    public FacesLifecycleImpl(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    public void execute(FacesContext context) {
        lifecycle.execute(context);
    }

    public Lifecycle getUnderlyingLifecycle() {
        return lifecycle;
    }

    public void render(FacesContext context) {
        lifecycle.render(context);
    }
}
