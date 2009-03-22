package com.steeplesoft.jsf.facestester;

import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.event.PhaseListener;
import javax.faces.FacesException;

public class FakeFacesLifecycle implements FacesLifecycle {
    public void execute(FacesContext context) {
    }

    public void render(FacesContext context) {
    }

    public Lifecycle getUnderlyingLifecycle() {
        return new Lifecycle() {
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
        };
    }
}
