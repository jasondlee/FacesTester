package com.steeplesoft.jsf.facestester;

import com.steeplesoft.jsf.facestester.context.FacesContextBuilder;
import javax.faces.context.FacesContext;

public class FakeFacesContextBuilder implements FacesContextBuilder {
    public FacesContext createFacesContext(String method, FacesLifecycle lifecycle) {
        return new FakeFacesContext();
    }
    public FacesContext createFacesContext(String uri, String method, FacesLifecycle lifecycle) {
        return new FakeFacesContext();
    }

    public FacesContext createFacesContext(FacesForm form, FacesLifecycle lifecycle) {
        return new FakeFacesContext();
    }
}
