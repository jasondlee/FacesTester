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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jasonlee
 */
public class FacesTesterHttpServletRequest implements HttpServletRequest {

    protected String method;
    protected String url;
    protected String servletPath;
    private FacesTesterHttpSession session;
    protected Map<String, Object> attributes = new HashMap<String, Object>();
    private String encoding = "ISO-8859-1";
    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> parameters = new HashMap<String, String>();
    private String queryString;
    private String pathInfo = "";
    private Cookie[] cookies;
    private String contentType = "";
    private ServletContext context;

    public FacesTesterHttpServletRequest(ServletContext context, String method, String url) {
        this.context = context;
        this.method = method;
        this.url = url;
    }

    public String getAuthType() {
        return "";
    }

    public Cookie[] getCookies() {
        return cookies;
    }

    public void setCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }

    public long getDateHeader(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public Enumeration getHeaders(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Enumeration getHeaderNames() {
        return new EnumerationImpl(headers.keySet());
    }

    public int getIntHeader(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public String getPathTranslated() {
        return "";
    }

    public String getContextPath() {
        return "/";
    }

    public String getQueryString() {
        return queryString;
    }

    public String getRemoteUser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isUserInRole(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Principal getUserPrincipal() {
        return null;
    }

    public String getRequestedSessionId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getRequestURI() {
        int index = url.indexOf("?");
        if (index == -1) {
            return url;
        } else {
            return url.substring(0, index);
        }
    }

    public StringBuffer getRequestURL() {
        return new StringBuffer(url);
    }

    public String getServletPath() {
        return this.servletPath;
    }

    public HttpSession getSession(boolean create) {
        if ((session == null) && create) {
            session = new FacesTesterHttpSession(context);
        }

        return session;
    }

    public HttpSession getSession() {
        return session;
    }

    public boolean isRequestedSessionIdValid() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isRequestedSessionIdFromCookie() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isRequestedSessionIdFromURL() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isRequestedSessionIdFromUrl() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public Enumeration getAttributeNames() {
        return new EnumerationImpl(attributes.keySet());
    }

    public String getCharacterEncoding() {
        return this.encoding;
    }

    public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException {
        this.encoding = encoding;
    }

    public int getContentLength() {
        return -1;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public ServletInputStream getInputStream() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public Enumeration getParameterNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getParameterValues(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Map getParameterMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getProtocol() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getScheme() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getServerName() {
        return "localhost";
    }

    public int getServerPort() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public BufferedReader getReader() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getRemoteAddr() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getRemoteHost() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    public void removeAttribute(String key) {
        this.attributes.remove(key);
    }

    public Locale getLocale() {
        return Locale.getDefault();
    }

    public Enumeration getLocales() {
        return new EnumerationImpl(Locale.getDefault());
    }

    public boolean isSecure() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        return new FacesTesterRequestDispatcher(path);
    }

    public String getRealPath(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getRemotePort() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getLocalName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getLocalAddr() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getLocalPort() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addParameter(String key, String value) {
        parameters.put(key, value);
    }

    public void setPathInfo(String info) {
        this.pathInfo = info;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setServletPath(String uri) {
        this.servletPath = uri;
    }

    public void setSession(FacesTesterHttpSession session) {
        this.session = session;
    }
}