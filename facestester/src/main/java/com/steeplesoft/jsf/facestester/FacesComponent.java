package com.steeplesoft.jsf.facestester;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.component.html.HtmlForm;

public class FacesComponent extends AbstractFacesComponent {
    public FacesComponent(UIComponent component) {
        this.component = component;
    }

    @Override
    public String getValueAsString() {
        if (component instanceof ValueHolder) {
            return ((ValueHolder) component).getValue().toString();
        }
        throw new AssertionError("UIComponent " + component.getId() + " does not hold values.");
    }

    @Override
    public FacesForm getEnclosingForm() {
        UIComponent parent = component.getParent();
        HtmlForm form = null;

        while (parent != null) {
            if (parent instanceof HtmlForm) {
                form = (HtmlForm) parent;
            } else {
                parent = parent.getParent();
            }
        }

        return new FacesForm(form);
    }
}
