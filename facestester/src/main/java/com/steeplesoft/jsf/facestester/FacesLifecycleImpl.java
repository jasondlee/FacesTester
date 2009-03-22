package com.steeplesoft.jsf.facestester;

import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;

public class FacesLifecycleImpl implements FacesLifecycle {
    private Lifecycle lifecycle;

    public FacesLifecycleImpl(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    public void execute(FacesContext context) {
        lifecycle.execute(context);
    }

    public void render(FacesContext context) {
        lifecycle.render(context);
    }

    public Lifecycle getUnderlyingLifecycle() {
        return lifecycle;
    }
}
