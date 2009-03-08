package com.steeplesoft.jsf.facestester.servlet;

import com.steeplesoft.jsf.facestester.FacesForm;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import javax.faces.component.html.HtmlForm;
import javax.faces.component.UIInput;
import java.util.Map;

public class WhenLoadingParametersFromForm {
    @Test
    public void shouldFoo() {
        HtmlForm htmlForm = new HtmlForm();
        htmlForm.setId("form");

        UIInput foo = new UIInput();
        foo.setId("foo");
        htmlForm.getChildren().add(foo);

        UIInput bar = new UIInput();
        bar.setId("bar");
        htmlForm.getChildren().add(bar);
        
        FacesForm form = new FacesForm(htmlForm);
        form.setValue("foo", "harry");
        form.setValue("bar", "sally");

        Map<String, String> parameters = form.getParameters();
        assertThat(parameters.get("foo"), is("harry"));
        assertThat(parameters.get("bar"), is("sally"));
    }
}
