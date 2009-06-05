package com.steeplesoft.jsf.facestester;

import javax.faces.context.FacesContext;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

public class WhenBuildingFacesTester {
    @Test
    public void shouldReturnSameFacesContextInstance() {
        FacesTester tester = new FacesTester();
        FacesContext context1 = tester.getFacesContext();
        FacesContext context2 = tester.getFacesContext();
        assertThat(context1, is(context2));

    }

    @Test
    public void shouldReleaseFacesContext() {
        FacesTester tester = new FacesTester();
        FacesContext context1 = tester.getFacesContext();
        tester = new FacesTester();
        FacesContext context2 = tester.getFacesContext();
        Assert.assertNotSame(context1, context2);
    }

    @Test
    public void shouldReturnValidFacesContext() {
        FacesTester tester = new FacesTester();
        assertThat(tester.getFacesContext(), is (not(nullValue())));
    }

    @Test
    public void shouldExposeServletContext() {
        FacesTester tester = new FacesTester();
        assertThat(tester.getServletContext(), is(not(nullValue())));
    }
}