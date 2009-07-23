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
import com.steeplesoft.jsf.facestester.util.MultiValueMap;
import com.sun.faces.util.ReflectionUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
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

    private static final Cookie[] EMPTY_COOKIE_ARRAY = new Cookie[0];

    private ServletContext context;
    private String contextPath;
    private String method;
    private String requestURI;
    private List<Locale> locales = new ArrayList<Locale>();
    private String servletPath = "";
    private String pathInfo;
    private String queryString;
    private HttpSession session;

    private Map<String, Object> attributes = new HashMap<String, Object>();
    private MultiValueMap<String> headers = new MultiValueMap<String>();
    private MultiValueMap<String> parameters = new MultiValueMap<String>();
    private Cookie[] cookies = EMPTY_COOKIE_ARRAY;

    private String contentType;
    private String encoding = "ISO-8859-1";
    private String serverName = "localhost";
    private int serverPort = 80;
    private int localPort = 80;

    public FacesTesterHttpServletRequest(ServletContext context, String method, String url) {
        this.context = context;
        this.contextPath = context.getContextPath();
        this.method = method;
        this.requestURI = url;
        this.locales.add(Locale.US);
    }

    public FacesTesterHttpServletRequest(FacesTesterServletContext context) {
        this(context, null, null);
    }

    public String getAuthType() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public Cookie[] getCookies() {
        return this.cookies;
    }

    public long getDateHeader(String name) {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public String getHeader(String name) {
        return this.headers.getSingle(name);
    }

    public Enumeration getHeaders(String name) {
        return Util.enumeration(this.headers.getAll(name));
    }

    public Enumeration getHeaderNames() {
        return Util.enumeration(this.headers.keySet());
    }

    public int getIntHeader(String name) {
        String stringValue = this.getHeader(name);
        return stringValue == null ? -1 : Integer.parseInt(stringValue);
    }

    public String getMethod() {
        return this.method;
    }

    public String getPathInfo() {
        return this.pathInfo;
    }

    public String getPathTranslated() {
        throw newUnsupportedOperationException("Not supported yet.");
//      return this.pathInfo == null ? null : this.getRealPath(this.pathInfo);
    }

    public String getContextPath() {
        return this.contextPath;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public String getRemoteUser() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public boolean isUserInRole(String role) {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public Principal getUserPrincipal() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public String getRequestedSessionId() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public String getRequestURI() {
        return this.requestURI;
    }

    public StringBuffer getRequestURL() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public String getServletPath() {
        return this.servletPath;
    }

    public HttpSession getSession(boolean create) {
        if(this.session==null && create) {
            throw new UnsupportedOperationException("Not supported yet. Please use setSession");
        }
        return this.session;
    }

    public HttpSession getSession() {
        return this.getSession(true);
    }

    public boolean isRequestedSessionIdValid() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public boolean isRequestedSessionIdFromCookie() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public boolean isRequestedSessionIdFromURL() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public boolean isRequestedSessionIdFromUrl() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public Enumeration getAttributeNames() {
        return Util.enumeration(this.attributes.keySet());
    }

    public String getCharacterEncoding() {
        return this.encoding;
    }

    public void setCharacterEncoding(String enc) throws UnsupportedEncodingException {
        this.encoding = enc;
    }

    public int getContentLength() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public String getContentType() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public ServletInputStream getInputStream() throws IOException {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public String getParameter(String name) {

        return this.parameters.getSingle(name);
    }

    public Enumeration getParameterNames() {
        return Util.enumeration(this.parameters.keySet());
    }

    public String[] getParameterValues(String name) {
        return this.parameters.getAll(name);
    }

    public Map getParameterMap() {
        return this.parameters.getData();
    }

    public String getProtocol() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public String getScheme() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public String getServerName() {
        throw newUnsupportedOperationException("Not supported yet.");
//      return this.serverName;
    }

    public int getServerPort() {
        throw newUnsupportedOperationException("Not supported yet.");
//      return this.serverPort;
    }

    public BufferedReader getReader() throws IOException {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public String getRemoteAddr() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public String getRemoteHost() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public void setAttribute(String name, Object o) {
        this.attributes.put(name, o);
    }

    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    public Locale getLocale() {
        return this.locales.get(0);
    }

    public Enumeration getLocales() {
        return Util.enumeration(this.locales);
    }

    public boolean isSecure() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public String getRealPath(String path) {
        if(this.context!=null) {
            return this.context.getRealPath(path);
        }
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public int getRemotePort() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public String getLocalName() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public String getLocalAddr() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public int getLocalPort() {
        throw newUnsupportedOperationException("Not supported yet.");
//      return this.localPort;
    }

    // ---------- Mock accessors ---------- //

    public void setCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }

    public void setHeadersMap(Map<String, String[]> headers) {
        this.headers.setData(headers);
    }

    public Map<String, String[]> getHeaders() {
        return this.headers.getData();
    }

    public void setHeader(String key, String value) {
        this.headers.set(key, value);
    }

    public void setHeader(String key, int value) {
        this.setHeader(key, String.valueOf(value));
    }

    public void addHeader(String key, String value) {
        this.headers.add(key, value);
    }

    public void addHeader(String key, int value) {
        this.addHeader(key, String.valueOf(value));
    }

    public void setHeaders(String key, String[] values) {
        this.headers.set(key, values);
    }

    public void removeHeader(String key) {
        this.headers.remove(key);
    }

    public void setParameterMap(Map<String, String[]> parameters) {
        this.parameters.setData(parameters);
    }

    public void addParameter(String key, String value) {
        this.parameters.add(key, value);
    }

    public void setParameter(String key, String value) {
        this.parameters.set(key, value);
    }

    public void removeParameter(String key) {
        this.parameters.remove(key);
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public void setLocales(List<Locale> locales) {
        this.locales = locales;
    }

    public void addLocale(Locale locale) {
        this.locales.add(locale);
    }

    public void setMethod(String method) {
        this.method = method;
    }

 

    private static RuntimeException newUnsupportedOperationException(String msg) {
        String caller = Thread.currentThread().getStackTrace()[2].getMethodName();
        RuntimeException ex = new RuntimeException(msg + ": " + caller);
        ex.fillInStackTrace();
        ex.printStackTrace(System.out);
        return ex;
    }
    /*
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

    public void setSession(HttpSession session) {
    this.session = session;
    }
     */
}
