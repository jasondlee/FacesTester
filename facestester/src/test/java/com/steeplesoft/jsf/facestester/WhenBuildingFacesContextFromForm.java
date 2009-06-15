package com.steeplesoft.jsf.facestester;

import com.steeplesoft.jsf.facestester.context.MojarraFacesContextBuilder;
import com.steeplesoft.jsf.facestester.context.FacesContextBuilder;
import com.steeplesoft.jsf.facestester.servlet.impl.FacesTesterServletContext;
import com.steeplesoft.jsf.facestester.servlet.WebDeploymentDescriptor;
import static com.steeplesoft.jsf.facestester.MapOfStringsMatcher.containsKey;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.Before;
import static org.hamcrest.CoreMatchers.is;

import javax.faces.render.ResponseStateManager;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import java.util.Map;
import org.springframework.mock.web.MockHttpSession;

public class WhenBuildingFacesContextFromForm {
    private FacesForm form;
    private FacesContextBuilder builder;

    @Before
    public void setUp() {
        HtmlForm htmlForm = new HtmlForm();
        htmlForm.setId("test-form");

        form = new FacesForm(htmlForm, new FakeFacesContextBuilder(), new FakeFacesLifecycle());
        builder = new MojarraFacesContextBuilder(new FacesTesterServletContext(), new MockHttpSession(),
                WebDeploymentDescriptor.createFromFile(Util.lookupWebAppPath()));
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
        HtmlCommandButton button = new HtmlCommandButton();
        button.setId("submit");
        form.component.getChildren().add(button);

        form.submit("submit");
        assertThat(buildContext().getExternalContext().getRequestParameterMap(), containsKey("test-form:submit"));
    }

    @Test
    public void shouldContainFormDataAsRequestParameters() {
        HtmlInputText textField = new HtmlInputText();
        textField.setId("input");
        form.component.getChildren().add(textField);

        form.setValue("input", "foo");
        Map<String, String> requestMap = buildContext().getExternalContext().getRequestParameterMap();
        assertThat(requestMap, containsKey("test-form:input"));
        assertThat(requestMap.get("test-form:input"), is("foo"));
    }

    private FacesContext buildContext() {
        return builder.createFacesContext(form, new FakeFacesLifecycle());
    }
}
