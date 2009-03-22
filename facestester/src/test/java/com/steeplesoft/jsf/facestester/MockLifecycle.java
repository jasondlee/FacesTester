package com.steeplesoft.jsf.facestester;

import javax.faces.lifecycle.Lifecycle;
import javax.faces.event.PhaseListener;
import javax.faces.context.FacesContext;
import javax.faces.FacesException;

public class MockLifecycle extends Lifecycle {
    public void addPhaseListener(PhaseListener listener) {
    }

    public void execute(FacesContext context) throws FacesException {
    }

    public PhaseListener[] getPhaseListeners() {
        return new PhaseListener[0];
    }

    public void removePhaseListener(PhaseListener listener) {
    }

    public void render(FacesContext context) throws FacesException {
    }
}
