package com.steeplesoft.jsf.facestester;

import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;

public class FacesPage extends FacesComponent {
    public FacesPage(UIViewRoot viewRoot) {
        super (viewRoot);
    }

    /**
     * Returns the form identified by the specified id if one can be found as a child of the component
     * tree for the page
     *
     * @param id form to return
     * @return specified form
     * @throws AssertionError if form cannot be found with the specified id
     */
    public FacesForm getFormById(String id) {
        for (UIComponent each : component.getChildren()) {
            if (each instanceof HtmlForm && id.equals(each.getId())) {
                return new FacesForm((HtmlForm) each);
            }
        }
        throw new AssertionError("HtmlForm " + id + " does not exist.");
    }
}
