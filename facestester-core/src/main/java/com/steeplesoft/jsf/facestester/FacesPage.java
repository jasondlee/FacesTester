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

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;

import com.steeplesoft.jsf.facestester.context.FacesContextBuilder;
import com.steeplesoft.jsf.facestester.servlet.impl.FacesTesterHttpServletResponse;
import com.steeplesoft.jsf.facestester.servlet.impl.FacesTesterPrintWriter;

public class FacesPage extends FacesComponent {

    private FacesContext facesContext;
    private FacesContextBuilder facesContextBuilder;
    private FacesLifecycle lifecycle;
    private String uri;

    public FacesPage(FacesContext facesContext,
                     FacesContextBuilder facesContextBuilder, FacesLifecycle lifecycle,
                     String uri) {
        super(facesContext.getViewRoot());

        this.facesContextBuilder = facesContextBuilder;
        this.facesContext = facesContext;
        this.lifecycle = lifecycle;
        this.uri = uri;
    }

    /**
     * Returns the form identified by the specified id if one can be found as a child of the component
     * tree for the page
     *
     * @param id form to return
     * @return specified form
     * @throws AssertionError if form cannot be found with the specified id
     */
	public FacesForm getFormById(String id) {
		FacesComponent form = getComponentWithId(id);
		String clientId = form.getWrappedComponent().getClientId(
				FacesContext.getCurrentInstance());
		return new FacesForm((HtmlForm) form.getWrappedComponent(),
				facesContextBuilder, lifecycle, uri, getViewState(clientId), getFormSubmitHiddenParameter(clientId));

	}
    
    private String getViewState(String clientId) {
    	FacesTesterPrintWriter facesTesterPrintWriter = getHttpServletResponse().getFacesTesterPrintWriter();
    	return facesTesterPrintWriter.getViewState(clientId);
    }
    
    private Map.Entry<String, String> getFormSubmitHiddenParameter(String clientId) {
    	FacesTesterPrintWriter facesTesterPrintWriter = getHttpServletResponse().getFacesTesterPrintWriter();
    	return facesTesterPrintWriter.getFormSubmitHiddenParameter(clientId);
    }

    public String getParameterValue(String key) {
        return facesContext.getExternalContext().getRequestParameterMap().get(key);
    }

    public String getMessageFor(String id) {
        Iterator<FacesMessage> iterator = facesContext.getMessages(id);
        return iterator.next().getSummary();
    }

    public String getRenderedPage() {
        String renderedPage = "";

        try {
            renderedPage = getHttpServletResponse().getContentAsString();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FacesPage.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        return renderedPage;
    }

	private FacesTesterHttpServletResponse getHttpServletResponse() {
		return ((FacesTesterHttpServletResponse) facesContext.getExternalContext()
		        .getResponse());
	}

    @Override
    public String getValueAsString() {
        return null;
    }
}