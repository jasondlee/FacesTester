package com.steeplesoft.jsf.facestester;

import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.UIComponent;
import java.util.Collection;
import java.util.ArrayList;

public class FacesFormBuilder {
    private Collection<UIComponent> components = new ArrayList<UIComponent>();

    public static FacesFormBuilder newFacesForm() {
        return new FacesFormBuilder();
    }

    public FacesFormBuilder withInputText(String id) {
        UIComponent textField = new HtmlInputText();
        textField.setId(id);
        components.add(textField);

        return this;
    }

    public FacesFormBuilder withButton(String id) {
        UIComponent button = new HtmlCommandButton();
        button.setId(id);
        components.add(button);

        return this;
    }

    public FacesForm build() {
        HtmlForm form = new HtmlForm();
        form.getChildren().addAll(components);

        return new FacesForm(form, new FakeFacesContextBuilder(), new FakeFacesLifecycle());
    }
}
