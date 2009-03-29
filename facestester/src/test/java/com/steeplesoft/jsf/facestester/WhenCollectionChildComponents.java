package com.steeplesoft.jsf.facestester;

import static com.steeplesoft.jsf.facestester.HtmlFormBuilder.newHtmlForm;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.ActionSource;
import javax.faces.component.html.HtmlForm;
import static java.util.Collections.singletonList;
import java.util.List;

public class WhenCollectionChildComponents {
    private FacesForm facesForm;

    @Before
    public void setUp() {
        HtmlForm form = newHtmlForm().withInputText("input").withButton("button").build();

        facesForm = new FacesForm(form, new FakeFacesContextBuilder(), new FakeFacesLifecycle());
    }

    @Test
    public void shouldCollectInputTextComponents() {
        List<String> children = facesForm.collectChildrenOfType(EditableValueHolder.class);
        assertThat(children, is(singletonList("input")));
    }

    @Test
    public void shouldCollectCommandButtonComponents() {
        List<String> children = facesForm.collectChildrenOfType(ActionSource.class);
        assertThat(children, is(singletonList("button")));
    }
}
