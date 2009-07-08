/*
 * Copyright (c) 2009, Jason Lee <jason@steeplesoft.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the <ORGANIZATION> nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.steeplesoft.jsf.facestester;

import com.steeplesoft.jsf.facestester.context.mojarra.MojarraFacesContextBuilder;
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
