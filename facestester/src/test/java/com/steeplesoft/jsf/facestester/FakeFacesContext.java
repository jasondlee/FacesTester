package com.steeplesoft.jsf.facestester;

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
