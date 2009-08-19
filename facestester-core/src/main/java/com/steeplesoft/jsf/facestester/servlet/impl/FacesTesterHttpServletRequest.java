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

import com.steeplesoft.jsf.facestester.Util;
import com.steeplesoft.jsf.facestester.servlet.CookieManager;
import com.steeplesoft.jsf.facestester.util.MultiValueMap;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

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

    private String remoteUser = null;
    private String scheme = "http";
    private String protocol = "HTTP/1.1";
    private ServletInputStream servletInputStream;
    private InputStream inputStream;
    private String authType;
    private Principal userPrincipal;
    private String[] userRoles;
    private String requestedSessionId;
    private boolean requestedSessionIdIsValid;
    private boolean requestedSessionIdIsFromCookie;
    private boolean requestedSessionIdIsFromURL;
    private String remoteAddr = "127.0.0.1";
    private String remoteHost = "localhost";
    private boolean secure;
    private int remotePort = 0;
    private String localName = "localhost";
    private String localAddr = "127.0.0.1";

    private final CookieManager cookieManager;

    public FacesTesterHttpServletRequest(ServletContext context, String method, String url, CookieManager cookieManager) {
        this.context = context;
        this.cookieManager = cookieManager;
        this.contextPath = context.getContextPath();
        this.method = method;
        this.requestURI = url;
        this.locales.add(Locale.US);
    }

    public FacesTesterHttpServletRequest(FacesTesterServletContext context, CookieManager cookieManager) {
        this(context, null, null, cookieManager);
    }

    public String getAuthType() {
        return this.authType;
    }

    public Cookie[] getCookies() {
        return this.cookieManager.getCookies();
    }

    public long getDateHeader(String name) {
        String value = this.getHeader(name);
        // TODO not doing date translation yet
        // So for now it has to be set with a long value.
        return Long.parseLong(value);
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
       return this.pathInfo == null ? null : this.getRealPath(this.pathInfo);
    }

    public String getContextPath() {
        return this.contextPath;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public String getRemoteUser() {
        return this.remoteUser;
    }

    public boolean isUserInRole(String role) {
        if(this.userRoles != null && role != null) {
            for(String userRole : this.userRoles) {
                if(role.equals(userRole)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Principal getUserPrincipal() {
        return this.userPrincipal;
    }

    public String getRequestedSessionId() {
        return this.requestedSessionId;
    }

    public String getRequestURI() {
        return this.requestURI;
    }

    public StringBuffer getRequestURL() {
        StringBuffer rval = new StringBuffer();
        rval.append(this.getProtocol()).append("://");
        rval.append(this.getServerName()).append(":").append(this.getServerPort());
        rval.append("/").append(this.getRequestURI());
        return rval;
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
        return this.requestedSessionIdIsValid;
    }

    public boolean isRequestedSessionIdFromCookie() {
        return this.requestedSessionIdIsFromCookie;
    }

    public boolean isRequestedSessionIdFromURL() {
        return this.requestedSessionIdIsFromURL;
    }

    public boolean isRequestedSessionIdFromUrl() {
        return this.isRequestedSessionIdFromURL();
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
        return this.contentType;
    }

    public ServletInputStream getInputStream() throws IOException {
        if(this.servletInputStream == null) {
            final InputStream in = this.inputStream;
            this.servletInputStream = new ServletInputStream() {

                @Override
                public int read() throws IOException {
                    return in.read();
                }

            };
        }
        return this.servletInputStream;
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
        return this.protocol;
    }

    public String getScheme() {
        return this.scheme;
    }

    public String getServerName() {
        return this.serverName;
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public BufferedReader getReader() throws IOException {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public String getRemoteAddr() {
        return this.remoteAddr;
    }

    public String getRemoteHost() {
        return this.remoteHost;
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
        return this.secure;
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        return new RequestDispatcher() {
            public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    public String getRealPath(String path) {
        if(this.context!=null) {
            return this.context.getRealPath(path);
        }
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public int getRemotePort() {
        return this.remotePort;
    }

    public String getLocalName() {
        return this.localName;
    }

    public String getLocalAddr() {
        return this.localAddr;
    }

    public int getLocalPort() {
        return this.localPort;
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

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public void setLocalAddr(String localAddr) {
        this.localAddr = localAddr;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    public void setRequestedSessionId(String requestedSessionId) {
        this.requestedSessionId = requestedSessionId;
    }

    public void setRequestedSessionIdFromCookie(boolean requestedSessionIdIsFromCookie) {
        this.requestedSessionIdIsFromCookie = requestedSessionIdIsFromCookie;
    }

    public void setRequestedSessionIdFromURL(boolean requestedSessionIdIsFromURL) {
        this.requestedSessionIdIsFromURL = requestedSessionIdIsFromURL;
    }

    public void setRequestedSessionIdValid(boolean requestedSessionIdIsValid) {
        this.requestedSessionIdIsValid = requestedSessionIdIsValid;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public void setUserPrincipal(Principal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    public void setUserRoles(String[] userRoles) {
        this.userRoles = userRoles;
    }

    private static RuntimeException newUnsupportedOperationException(String msg) {
        String caller = Thread.currentThread().getStackTrace()[2].getMethodName();
        RuntimeException ex = new RuntimeException(msg + ": " + caller);
        ex.fillInStackTrace();
        ex.printStackTrace(System.out);
        return ex;
    }
}
