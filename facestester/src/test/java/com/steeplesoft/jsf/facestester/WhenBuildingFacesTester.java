package com.steeplesoft.jsf.facestester;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

public class WhenBuildingFacesTester {

    @Test
    public void shouldExposeServletContext() {
        FacesTester tester = new FacesTester();
        assertThat(tester.getServletContext(), is(not(nullValue())));
    }
}
