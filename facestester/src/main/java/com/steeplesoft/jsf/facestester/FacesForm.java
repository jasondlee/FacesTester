package com.steeplesoft.jsf.facestester;

import javax.faces.FactoryFinder;
import static javax.faces.FactoryFinder.LIFECYCLE_FACTORY;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import static javax.faces.lifecycle.LifecycleFactory.DEFAULT_LIFECYCLE;
import static javax.faces.render.ResponseStateManager.VIEW_STATE_PARAM;
import java.util.HashMap;
import java.util.Map;

public class FacesForm extends FacesComponent {
    private Lifecycle lifecycle;
    private FacesContextBuilder facesContextBuilder;
    private String uri;
    private Map<String, String> parameterMap = new HashMap<String, String>();

    FacesForm(HtmlForm htmlForm) {
        super(htmlForm);

        parameterMap.put(VIEW_STATE_PARAM, "j_id1:j_id2");
    }

    public FacesForm(HtmlForm htmlForm, FacesContextBuilder facesContextBuilder, String uri) {
        this(htmlForm);

        this.facesContextBuilder = facesContextBuilder;
        this.uri = uri;

        // TODO Extract interface
        LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(LIFECYCLE_FACTORY);
        lifecycle = lifecycleFactory.getLifecycle(DEFAULT_LIFECYCLE);
    }


    public void setValue(String key, String value) {
    }

    public void submit() {
        FacesContext context = facesContextBuilder.createFacesContext(this, lifecycle);

        lifecycle.execute(context);
        lifecycle.render(context);
    }

    public Map<String, String> getParameterMap() {
        return parameterMap;
    }

    public String getUri() {
        return uri;
    }
}
