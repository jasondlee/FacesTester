package com.steeplesoft.jsf.facestester;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;

public class FacesComponent {
    private UIComponent component;

    public FacesComponent(UIComponent component) {
        this.component = component;
    }

    public String getValueAsString() {
        if (component instanceof ValueHolder) {
            return ((ValueHolder) component).getValue().toString();
        }
        throw new AssertionError("UIComponent " + component.getId() + " does not hold values.");
    }

    public boolean isRendered() {
        return component.isRendered();
    }
}
