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
package com.steeplesoft.jsf.facestester.test.stubs;

import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.render.RenderKit;
import javax.faces.component.UIViewRoot;
import java.util.Iterator;

public class FakeFacesContext extends FacesContext {
    public Application getApplication() {
        return null;
    }

    public Iterator<String> getClientIdsWithMessages() {
        return null;
    }

    public ExternalContext getExternalContext() {
        return null;
    }

    public FacesMessage.Severity getMaximumSeverity() {
        return null;
    }

    public Iterator<FacesMessage> getMessages() {
        return null;
    }

    public Iterator<FacesMessage> getMessages(String clientId) {
        return null;
    }

    public RenderKit getRenderKit() {
        return null;
    }

    public boolean getRenderResponse() {
        return false;
    }

    public boolean getResponseComplete() {
        return false;
    }

    public ResponseStream getResponseStream() {
        return null;
    }

    public void setResponseStream(ResponseStream responseStream) {
    }

    public ResponseWriter getResponseWriter() {
        return null;
    }

    public void setResponseWriter(ResponseWriter responseWriter) {
    }

    public UIViewRoot getViewRoot() {
        return null;
    }

    public void setViewRoot(UIViewRoot root) {
    }

    public void addMessage(String clientId, FacesMessage message) {
    }

    public void release() {
    }

    public void renderResponse() {
    }

    public void responseComplete() {
    }
}
