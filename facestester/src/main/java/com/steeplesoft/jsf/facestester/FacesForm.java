package com.steeplesoft.jsf.facestester;

import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlForm;

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
}
