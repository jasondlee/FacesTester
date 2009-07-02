package com.steeplesoft.jsf.facestester.context;

import com.steeplesoft.jsf.facestester.*;
import javax.faces.context.FacesContext;


public interface FacesContextBuilder {
    FacesContext createFacesContext(String method,FacesLifecycle lifecycle);
    FacesContext createFacesContext(String uri, String method,FacesLifecycle lifecycle);
    FacesContext createFacesContext(FacesForm form, FacesLifecycle lifecycle);
}
