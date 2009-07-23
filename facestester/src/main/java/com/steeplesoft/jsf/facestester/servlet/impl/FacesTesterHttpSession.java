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

import com.steeplesoft.jsf.facestester.Util;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * TODO invalid checks
 * @author jasonlee
 */
public class FacesTesterHttpSession implements HttpSession {
    protected Date creationDate = new Date();
    private long creationTime = System.currentTimeMillis();
    private long lastAccessedTime = creationTime;
    protected Map<String, Object> attributes = new HashMap<String, Object>();
    private String id = Long.toString((new Date()).getTime()) + Math.random();;
    private ServletContext context;
    private Map<String, Object> values = new HashMap<String, Object>();
    private int maxInactiveInterval = 1800;
    private boolean isNew = true;
    private boolean invalid = false;

    public FacesTesterHttpSession(ServletContext context) {
        this.context = context;
    }

    public long getCreationTime() {
        return this.creationTime;
    }

    public String getId() {
        return this.id;
    }

    public long getLastAccessedTime() {
        return this.lastAccessedTime;
    }

    public ServletContext getServletContext() {
        return this.context;
    }

    public void setMaxInactiveInterval(int interval) {
        this.maxInactiveInterval = interval;
    }

    public int getMaxInactiveInterval() {
        return this.maxInactiveInterval;
    }

    public HttpSessionContext getSessionContext() {
        throw new UnsupportedOperationException("Not supported yet and deprecated!");
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public Object getValue(String name) {
        return this.values.get(name);
    }

    public Enumeration getAttributeNames() {
        return Util.enumeration(this.attributes.keySet());
    }

    public String[] getValueNames() {
        return this.values.keySet().toArray(new String[this.values.size()]);
    }

    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    public void putValue(String name, Object value) {
        this.values.put(name, value);
    }

    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    public void removeValue(String name) {
        this.values.remove(name);
    }

    public void invalidate() {
        this.invalid = true;
    }

    public boolean isNew() {
        return this.isNew;
    }

    /* ---------- Mock accessors --------- */
    public void access() {
        long now = System.currentTimeMillis();
        if(now>this.lastAccessedTime + this.maxInactiveInterval*1000) {
            this.invalidate();
        } else {
            this.lastAccessedTime = now;
        }
        this.isNew = false;
    }

}
