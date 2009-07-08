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
