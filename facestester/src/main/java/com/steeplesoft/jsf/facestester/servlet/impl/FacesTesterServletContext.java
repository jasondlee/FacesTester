/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.jsf.facestester.servlet.impl;

import com.steeplesoft.jsf.facestester.Util;
import com.steeplesoft.jsf.facestester.servlet.WebAppResourceLoader;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.mock.web.MockServletContext;


/**
 *
 * @author jasonlee
 */
public class FacesTesterServletContext extends MockServletContext {
    private Map<String, Object> attributes = new HashMap<String, Object>();
    private Map<String, String> initParameters = new HashMap<String, String>();

    public FacesTesterServletContext() {
    }

    public FacesTesterServletContext(WebAppResourceLoader webAppResourceLoader) {
        super(webAppResourceLoader);
    }

    public String getContextPath() {
        return "/";
    }


    /*
    public void addInitParameter(String key, String value) {
        initParameters.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public Enumeration getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    public ServletContext getContext(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getInitParameter(String key) {
        return initParameters.get(key);
    }

    public Enumeration getInitParameterNames() {
        return Collections.enumeration(initParameters.keySet());
    }

    public int getMajorVersion() {
        return 2;
    }

    public String getMimeType(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getMinorVersion() {
        return 5;
    }

    public RequestDispatcher getNamedDispatcher(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getRealPath(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public URL getResource(String name) throws MalformedURLException {
        return this.getClass().getClassLoader().getResource(name);
    }

    public InputStream getResourceAsStream(String path) {
        if ("/WEB-INF/web.xml".equals(path)) {
            return Util.streamWebXmlFrom(Util.lookupWebAppPath()); // Ugly hack?
        }
        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        if (is == null) {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        }
        if (is == null) {
            is = ClassLoader.getSystemResourceAsStream(path);
        }
        return is;
    }

    public Set getResourcePaths(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getServerInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Servlet getServlet(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getServletContextName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Enumeration getServletNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Enumeration getServlets() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void log(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void log(Exception arg0, String arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void log(String arg0, Throwable arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeAttribute(String key) {
        attributes.put(key, null);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }
    */
}
