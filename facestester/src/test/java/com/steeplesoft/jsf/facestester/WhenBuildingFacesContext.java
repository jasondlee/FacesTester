package com.steeplesoft.jsf.facestester;

import com.steeplesoft.jsf.facestester.servlet.WebDeploymentDescriptor;
import static com.steeplesoft.jsf.facestester.servlet.ServletContextFactory.createServletContext;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.Before;

import javax.faces.context.FacesContext;
import org.springframework.mock.web.MockHttpSession;

public class WhenBuildingFacesContext {
    private FacesContextBuilder facesContextBuilder;

    @Before
    public void setUp() {
        final WebDeploymentDescriptor descriptor = WebDeploymentDescriptor.createFromFile(Util.lookupWebAppPath());
        facesContextBuilder = new FacesContextBuilderImpl(createServletContext(descriptor),new MockHttpSession(),descriptor);
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
