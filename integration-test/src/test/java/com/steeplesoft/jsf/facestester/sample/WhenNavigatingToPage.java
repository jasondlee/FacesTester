package com.steeplesoft.jsf.facestester.sample;

import com.steeplesoft.jsf.facestester.FacesComponent;
import com.steeplesoft.jsf.facestester.FacesTester;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class WhenNavigatingToPage {
    private FacesTester tester;

    @Before
    public void setUp() throws Exception {
        tester = new FacesTester();
    }

    @Test
    public void shouldBeAbleToAssertValueOfComponents() throws Exception {
        assertThat(tester.requestPage("/address.xhtml").getComponentWithId("form:stateLabel").getValueAsString(),
                is("State"));
    }

    @Test(expected = AssertionError.class)
    public void shouldBeAbleToAssertValueOfNoExistentComponents() {
        tester.requestPage("/address.xhtml").getComponentWithId("unknown");
    }

    @Test
    public void shouldBeAbleToEvaluateEl() throws Exception {
        assertThat(tester.requestPage("/address.xhtml").getComponentWithId("form:elTest").getValueAsString(),
                is ("9"));
    }

    @Test
    public void shouldBeAbleToTestRendered() throws Exception {
        FacesComponent component = tester.requestPage("/address.xhtml").getComponentWithId("form:renderedTest");
        assertThat(component.getValueAsString(),is ("RenderedTest"));
        assertEquals(component.isRendered(), false);
    }

}