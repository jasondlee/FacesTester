package com.steeplesoft.jsf.facestester;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;

public class FacesComponent {
    protected UIComponent component;

    public FacesComponent(UIComponent component) {
        this.component = component;
    }

    public boolean isRendered() {
        return component.isRendered();
    }

    /**
     * Returns the component identified by the specified id if one can be found as a child of the component
     * tree for the page
     *
     * @param id component to return
     * @return specified component
     * @throws AssertionError if component cannot be found with the specified id
     */
    public FacesComponent getComponentWithId(String id) {
        UIComponent desiredComponent = this.component.findComponent(id);
        if (desiredComponent == null) {
            throw new AssertionError("UIComponent " + id + " does not exist on page.");
        }
        return new FacesComponent(desiredComponent);
    }

    public String getValueAsString() {
        if (component instanceof ValueHolder) {
            Object value = ((ValueHolder) component).getValue();
            return value == null ? null : value.toString();
        }
        throw new AssertionError("UIComponent " + component.getId() + " does not hold values.");
    }
}
