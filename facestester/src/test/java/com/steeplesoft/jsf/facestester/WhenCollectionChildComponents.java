package com.steeplesoft.jsf.facestester;

import static com.steeplesoft.jsf.facestester.FacesFormBuilder.newFacesForm;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import javax.faces.component.ActionSource;
import javax.faces.component.EditableValueHolder;
import static java.util.Collections.singletonList;

public class WhenCollectionChildComponents {
    private FacesForm facesForm;

    @Before
    public void setUp() {
        facesForm = newFacesForm().withInputText("input").withButton("button").build();
    }

    @Test
    public void shouldCollectInputTextComponents() {
        assertThat(facesForm.collectChildrenOfType(EditableValueHolder.class), is(singletonList("input")));
    }

    @Test
    public void shouldCollectCommandButtonComponents() {
        assertThat(facesForm.collectChildrenOfType(ActionSource.class), is(singletonList("button")));
    }
}
