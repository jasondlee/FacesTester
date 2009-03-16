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

    public void dumpComponentTree() {
        System.out.println("/ " + this.getWrappedComponent().getId());
        for (UIComponent child : this.getWrappedComponent().getChildren()) {
            dumpChildren(child, "----");
        }
    }

    private void dumpChildren(UIComponent component, String prefix) {
        System.out.println("|" + prefix + "----" + component.getId());
        for (UIComponent child : component.getChildren()) {
            dumpChildren(child, prefix+"----");
        }
    }
}
