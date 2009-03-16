package com.steeplesoft.jsf.facestester;

import java.io.UnsupportedEncodingException;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.springframework.mock.web.MockHttpServletResponse;

public class FacesPage extends FacesComponent {
    MockHttpServletResponse servletResponse;
    public FacesPage(FacesContext facesContext) {
        super (facesContext.getViewRoot());
        this.servletResponse = (MockHttpServletResponse)facesContext.getExternalContext().getResponse();
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

    public String getRenderedPage() throws UnsupportedEncodingException {
        return servletResponse.getContentAsString();
    }
}
