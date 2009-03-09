/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;

/**
 *
 * @author jasonlee
 */
public abstract class AbstractFacesComponent {
    protected UIComponent component;

    public UIComponent getWrappedComponent() {
        return component;
    }

    public String getValueAsString() {
        throw new UnsupportedOperationException();
    }

    public boolean isRendered() {
        return component.isRendered();
    }

    public FacesForm getEnclosingForm() {
        throw new UnsupportedOperationException();
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

}