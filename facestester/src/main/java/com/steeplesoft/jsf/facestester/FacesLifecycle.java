package com.steeplesoft.jsf.facestester;

import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;

public interface FacesLifecycle {
    void execute(FacesContext context);

    void render(FacesContext context);

    Lifecycle getUnderlyingLifecycle();
}
