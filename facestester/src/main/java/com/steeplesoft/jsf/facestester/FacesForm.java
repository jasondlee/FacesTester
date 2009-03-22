package com.steeplesoft.jsf.facestester;

import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import static javax.faces.render.ResponseStateManager.VIEW_STATE_PARAM;
import java.util.HashMap;
import java.util.Map;

public class FacesForm extends FacesComponent {
    private FacesLifecycle lifecycle;
    private FacesContextBuilder facesContextBuilder;
    private Map<String, String> parameterMap = new HashMap<String, String>();
    private String uri;
    private static final String UNDEFINED = "undefined";
    private static final String DEFAULT_VIEW_STATE = "j_id1:j_id2";
    private String formId;

    FacesForm(HtmlForm htmlForm, FacesContextBuilder facesContextBuilder, FacesLifecycle lifecycle) {
        super(htmlForm);
        this.facesContextBuilder = facesContextBuilder;
        this.lifecycle = lifecycle;

        parameterMap.put(VIEW_STATE_PARAM, DEFAULT_VIEW_STATE);
        formId = htmlForm.getId();
        parameterMap.put(formId, UNDEFINED);
    }

    public FacesForm(HtmlForm htmlForm, FacesContextBuilder facesContextBuilder, FacesLifecycle lifecycle, String uri) {
        this(htmlForm, facesContextBuilder, lifecycle);
        this.uri = uri;
    }


    public void setValue(String key, String value) {
        parameterMap.put(qualifiedIdFor(key), value);
    }

    public void submit(String submittedId) {
        parameterMap.put(qualifiedIdFor(submittedId), UNDEFINED);

        FacesContext context = facesContextBuilder.createFacesContext(this, lifecycle);

        lifecycle.execute(context);
        lifecycle.render(context);
    }

    private String qualifiedIdFor(String key) {
        return formId + ":" + key;
    }

    public Map<String, String> getParameterMap() {
        return parameterMap;
    }

    public String getUri() {
        return uri;
    }
}
