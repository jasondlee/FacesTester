package com.steeplesoft.jsf.facestester;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;

public class FacesComponent {
    private UIComponent component;

    public FacesComponent(UIComponent component) {
        this.component = component;
    }

    public String getValueAsString() {
        if (component instanceof UIOutput) {
            return ((UIOutput) component).getValue().toString();
        }
        throw new AssertionError("UIComponent " + component.getId() + " does not hold values.");
    }
}
