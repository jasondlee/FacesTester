package com.steeplesoft.jsf.facestester;

import javax.faces.context.FacesContext;

public class FakeFacesContextBuilder implements FacesContextBuilder {
    public FacesContext createFacesContext(String uri, String method, FacesLifecycle lifecycle) {
        return null;
    }

    public FacesContext createFacesContext(FacesForm form, FacesLifecycle lifecycle) {
        return null;
    }
}
