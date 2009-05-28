package com.steeplesoft.jsf.facestester;

import static com.steeplesoft.jsf.facestester.servlet.ServletContextFactory.createServletContext;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.Before;

import javax.faces.context.FacesContext;

public class WhenBuildingFacesContext {
    private FacesContextBuilder facesContextBuilder;

    @Before
    public void setUp() {
        facesContextBuilder = new FacesContextBuilderImpl(createServletContext());
    }

    @Test
    public void shouldHaveSessionAvailableInExternalContext() {
        FacesContext facesContext = buildContext();
        assertThat(facesContext.getExternalContext().getSession(false), is(notNullValue()));
    }

    @Test
    public void shouldBuildSubsequentContextWithSameSession() {
        assertThat(buildContext().getExternalContext().getSession(false),
                is(buildContext().getExternalContext().getSession(false)));
    }

    private FacesContext buildContext() {
        return facesContextBuilder.createFacesContext("/test.xhtml", "GET", new FakeFacesLifecycle());
    }
}
