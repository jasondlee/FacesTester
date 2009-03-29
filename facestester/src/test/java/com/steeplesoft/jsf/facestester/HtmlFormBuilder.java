package com.steeplesoft.jsf.facestester;

import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.UIComponent;
import java.util.Collection;
import java.util.ArrayList;

public class HtmlFormBuilder {
    private Collection<UIComponent> components = new ArrayList<UIComponent>();

    public static HtmlFormBuilder newHtmlForm() {
        return new HtmlFormBuilder();
    }

    public HtmlFormBuilder withInputText(String id) {
        UIComponent textField = new HtmlInputText();
        textField.setId(id);
        components.add(textField);

        return this;
    }

    public HtmlFormBuilder withButton(String id) {
        UIComponent button = new HtmlCommandButton();
        button.setId(id);
        components.add(button);

        return this;
    }

    public HtmlForm build() {
        HtmlForm form = new HtmlForm();
        form.getChildren().addAll(components);

        return form;
    }
}
