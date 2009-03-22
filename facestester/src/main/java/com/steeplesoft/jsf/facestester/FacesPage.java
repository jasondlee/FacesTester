package com.steeplesoft.jsf.facestester;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;

public class FacesPage extends FacesComponent {
    private FacesContextBuilder facesContextBuilder;
    private FacesLifecycle lifecycle;
    private String uri;

    public FacesPage(FacesContext facesContext, FacesContextBuilder facesContextBuilder, FacesLifecycle lifecycle,
                     String uri) {
        super(facesContext.getViewRoot());

        this.facesContextBuilder = facesContextBuilder;
        this.lifecycle = lifecycle;
        this.uri = uri;
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
                return new FacesForm((HtmlForm) each, facesContextBuilder, lifecycle, uri);
            }
        }
        throw new AssertionError("HtmlForm " + id + " does not exist.");
    }
}