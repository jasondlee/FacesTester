package com.steeplesoft.jsf.facestester;

import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;


public interface FacesLifecycle {
    void execute(FacesContext context);

    Lifecycle getUnderlyingLifecycle();

    void render(FacesContext context);
}
