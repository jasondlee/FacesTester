package com.steeplesoft.jsf.facestester;

import static java.lang.String.format;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.ActionSource;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import static javax.faces.render.ResponseStateManager.VIEW_STATE_PARAM;


public class FacesForm extends FacesComponent {
    private static final String UNDEFINED = "undefined";
    private static final String DEFAULT_VIEW_STATE = "j_id1:j_id2";
    private FacesContextBuilder facesContextBuilder;
    private FacesLifecycle lifecycle;
    private Map<String, String> parameterMap = new HashMap<String, String>();
    private String formId;
    private String uri;

    public FacesForm(HtmlForm htmlForm,
        FacesContextBuilder facesContextBuilder, FacesLifecycle lifecycle,
        String uri) {
        this(htmlForm, facesContextBuilder, lifecycle);
        this.uri = uri;
    }

    FacesForm(HtmlForm htmlForm, FacesContextBuilder facesContextBuilder,
        FacesLifecycle lifecycle) {
        super(htmlForm);
        this.facesContextBuilder = facesContextBuilder;
        this.lifecycle = lifecycle;

        parameterMap.put(VIEW_STATE_PARAM, DEFAULT_VIEW_STATE);
        formId = htmlForm.getId();
        parameterMap.put(formId, UNDEFINED);
    }

    public Map<String, String> getParameterMap() {
        return parameterMap;
    }

    public String getUri() {
        return uri;
    }

    public void setValue(String key, String value) {
        if (!collectChildrenOfType(EditableValueHolder.class).contains(key)) {
            throw new FacesTesterException(format("There is no input component with id '%s' on form '%s'",
                    key, formId));
        }

        parameterMap.put(qualifiedIdFor(key), value);
    }

    public void submit(String submittedId) {
        if (!collectChildrenOfType(ActionSource.class).contains(submittedId)) {
            throw new FacesTesterException(format("There is no action component with id '%s' on form '%s'",
                    submittedId, formId));
        }

        parameterMap.put(qualifiedIdFor(submittedId), UNDEFINED);

        FacesContext context = facesContextBuilder.createFacesContext(this,
                lifecycle);

        lifecycle.execute(context);
        lifecycle.render(context);
    }

    private String qualifiedIdFor(String key) {
        return formId + ":" + key;
    }
}
