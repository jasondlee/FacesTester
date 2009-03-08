package com.steeplesoft.jsf.facestester;

import javax.faces.component.UIInput;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import java.util.Map;
import java.util.HashMap;

public class FacesForm {
    private HtmlForm form;

    public FacesForm(HtmlForm htmlForm) {
        form = htmlForm;
    }

    public void setValue(String fieldName, String value) {
        UIInput component = (UIInput) form.findComponent(fieldName);
        component.setValue(value);
    }

    public void submit() {
    }

    public Map<String, String> getParameters() {
        Map<String, String> parameters = new HashMap<String, String>();
        for (UIComponent each : form.getChildren()) {
            if (each instanceof UIInput) {
                parameters.put(each.getId(), ((UIInput) each).getValue().toString());
            }
        }
        return parameters;
    }
}
