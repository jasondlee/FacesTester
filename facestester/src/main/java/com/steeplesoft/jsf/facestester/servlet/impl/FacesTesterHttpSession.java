/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.servlet.impl;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 *
 * @author jasonlee
 */
public class FacesTesterHttpSession implements HttpSession {
    protected Date creationDate = new Date();
    protected Map<String, Object> attributes = new HashMap<String, Object>();
    private String id = Long.toString((new Date()).getTime());;
    private ServletContext context;
    private Map<String, Object> values = new HashMap<String, Object>();
    private boolean valid = true;

    public FacesTesterHttpSession(ServletContext context) {
        this.context = context;
    }

    public long getCreationTime() {
        return creationDate.getTime();
    }

    public String getId() {
        return this.id;
    }

    public long getLastAccessedTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ServletContext getServletContext() {
        return this.context;
    }

    public void setMaxInactiveInterval(int arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getMaxInactiveInterval() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public HttpSessionContext getSessionContext() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public Object getValue(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Enumeration getAttributeNames() {
        return new EnumerationImpl(attributes.keySet());
    }

    public String[] getValueNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public void putValue(String key, Object value) {
        values.put(key, value);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public void removeValue(String key) {
        values.remove(key);
    }

    public void invalidate() {
        valid = false;
        attributes.clear();
        values.clear();
    }

    public boolean isNew() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
