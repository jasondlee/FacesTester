package com.steeplesoft.jsf.facestester;

import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;

public class FacesPage {
    private UIViewRoot viewRoot;

    public FacesPage(UIViewRoot viewRoot) {
        this.viewRoot = viewRoot;
    }

    public FacesComponent getComponentWithId(String id) {
        UIComponent component = viewRoot.findComponent(id);
        if (component == null) {
            throw new AssertionError("UIComponent " + id + " does not exist.");
        }
        return new FacesComponent(component);
    }
}
