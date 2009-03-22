package com.steeplesoft.jsf.facestester;

import static com.steeplesoft.jsf.facestester.MapOfStringsMatcher.containsKey;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.Before;
import org.springframework.mock.web.MockServletContext;
import static org.hamcrest.CoreMatchers.is;

import javax.faces.render.ResponseStateManager;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import java.util.Map;

public class WhenBuildingFacesContextFromForm {
    private FacesForm form;
    private FacesContextBuilder builder;

    @Before
    public void setUp() {
        HtmlForm htmlForm = new HtmlForm();
        htmlForm.setId("test-form");

        form = new FacesForm(htmlForm, new FakeFacesContextBuilder(), new FakeFacesLifecycle());
        builder = new FacesContextBuilderImpl(new MockServletContext());
    }

    @Test
    public void shouldContainViewStateProperty() {
        assertThat(form.getParameterMap(), containsKey(ResponseStateManager.VIEW_STATE_PARAM));
    }

    @Test
    public void shouldContainFormIdInRequestParameters() {
        assertThat(buildContext().getExternalContext().getRequestParameterMap(), containsKey("test-form"));
    }

    @Test
    public void shouldContainIdOfSubmittedComponent() {
        form.submit("submit");
        assertThat(buildContext().getExternalContext().getRequestParameterMap(), containsKey("test-form:submit"));
    }

    @Test
    public void shouldContainFormDataAsRequestParameters() {
        form.setValue("input", "foo");
        Map<String,String> requestMap = buildContext().getExternalContext().getRequestParameterMap();
        assertThat(requestMap, containsKey("test-form:input"));
        assertThat(requestMap.get("test-form:input"), is("foo"));
    }

    private FacesContext buildContext() {
        return builder.createFacesContext(form, new FakeFacesLifecycle());
    }
}
