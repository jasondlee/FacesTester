package com.steeplesoft.jsf.facestester;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import org.springframework.mock.web.MockHttpServletResponse;

public class FacesPage extends FacesComponent {

    private FacesContextBuilder facesContextBuilder;
    private FacesContext facesContext;
    private FacesLifecycle lifecycle;
    private String uri;

    public FacesPage(FacesContext facesContext, FacesContextBuilder facesContextBuilder, FacesLifecycle lifecycle,
                     String uri) {
        super(facesContext.getViewRoot());

        this.facesContextBuilder = facesContextBuilder;
        this.facesContext = facesContext;
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
        throw new AssertionError("HtmlForm '" + id + "' does not exist on page.");
    }

    public String getRenderedPage() {
        String renderedPage = "";
        try {
            renderedPage = ((MockHttpServletResponse) facesContext.getExternalContext().getResponse()).getContentAsString();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FacesPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return renderedPage;
    }

    @Override
    public String getValueAsString() {
        return null;
    }

    public String getParameterValue(String key) {
        return facesContext.getExternalContext().getRequestParameterMap().get(key);
    }

    public String getMessageFor(String id) {
        Iterator<FacesMessage> iterator = facesContext.getMessages(id);
        return iterator.next().getSummary();
    }
}