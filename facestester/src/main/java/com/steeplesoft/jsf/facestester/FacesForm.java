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

import com.steeplesoft.jsf.facestester.context.FacesContextBuilder;
import static java.lang.String.format;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.ActionSource;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import static javax.faces.render.ResponseStateManager.VIEW_STATE_PARAM;


public class FacesForm extends FacesComponent {

    private static final String UNDEFINED = "undefined";
    private static final String DEFAULT_VIEW_STATE = "j_id1:j_id2";
    private FacesContextBuilder facesContextBuilder;
    private FacesLifecycle lifecycle;
    private Map<String, String> parameterMap = new HashMap<String, String>();
    private String formId;
    private String uri;

    public FacesForm(HtmlForm htmlForm,
        FacesContextBuilder facesContextBuilder, FacesLifecycle lifecycle,
        String uri) {
        this(htmlForm, facesContextBuilder, lifecycle);
        this.uri = uri;
    }

    FacesForm(HtmlForm htmlForm, FacesContextBuilder facesContextBuilder,
        FacesLifecycle lifecycle) {
        super(htmlForm);
        this.facesContextBuilder = facesContextBuilder;
        this.lifecycle = lifecycle;

        parameterMap.put(VIEW_STATE_PARAM, DEFAULT_VIEW_STATE);
        formId = htmlForm.getId();
        parameterMap.put(formId, UNDEFINED);
    }

    public Map<String, String> getParameterMap() {
        return parameterMap;
    }

    public String getUri() {
        return uri;
    }

    public void setValue(String key, String value) {
        if (!collectChildrenOfType(EditableValueHolder.class).contains(key)) {
            throw new FacesTesterException(format("There is no input component with id '%s' on form '%s'",
                    key, formId));
        }

        parameterMap.put(qualifiedIdFor(key), value);
    }

    public FacesPage submit(String submittedId) {
        if (!collectChildrenOfType(ActionSource.class).contains(submittedId)) {
            throw new FacesTesterException(format("There is no action component with id '%s' on form '%s'",
                    submittedId, formId));
        }

        parameterMap.put(qualifiedIdFor(submittedId), UNDEFINED);

        FacesContext context = facesContextBuilder.createFacesContext(this,
                lifecycle);

        lifecycle.execute(context);
        lifecycle.render(context);

        return new FacesPage(context, facesContextBuilder, lifecycle, "unknown");
    }

    private String qualifiedIdFor(String key) {
        return formId + ":" + key;
    }
}
