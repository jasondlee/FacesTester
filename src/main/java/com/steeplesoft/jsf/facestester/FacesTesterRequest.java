/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;

/**
 *
 * @author jasonlee
 */
class FacesTesterRequest implements ServletRequest {
    protected Map<String, Object> attributes = new HashMap<String, Object>();
    protected Map<String, String> parameters = new HashMap<String, String>();

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public Enumeration getAttributeNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getCharacterEncoding() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getContentLength() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getContentType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ServletInputStream getInputStream() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public Enumeration getParameterNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getParameterValues(String arg0) {
        return (String[])parameters.values().toArray();
    }

    public Map getParameterMap() {
        return Collections.unmodifiableMap(parameters);
    }

    public String getProtocol() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getScheme() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getServerName() {
        throw new UnsupportedOperationException("Not supported yet.");
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

    public void setAttribute(String arg0, Object arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeAttribute(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Locale getLocale() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Enumeration getLocales() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isSecure() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RequestDispatcher getRequestDispatcher(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
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

}
