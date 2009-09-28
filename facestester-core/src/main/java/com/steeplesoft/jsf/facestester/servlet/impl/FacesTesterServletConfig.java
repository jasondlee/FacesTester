/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.servlet.impl;

import com.steeplesoft.jsf.facestester.Util;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 *
 * @author jasonlee
 */
public class FacesTesterServletConfig implements ServletConfig {
    protected String servletName;
    protected ServletContext servletContext;
    protected Map<String, String> initParams = new HashMap<String, String>(); // This should be changed to something that retains order

    public FacesTesterServletConfig(String servletName, ServletContext servletContext) {
        this.servletName = servletName;
        this.servletContext = servletContext;
    }

    public String getServletName() {
        return servletName;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public String getInitParameter(String name) {
        return initParams.get(name);
    }

    public Enumeration getInitParameterNames() {
        return Util.enumeration(initParams.keySet());
    }

    public void setInitParam(String name, String value) {
        initParams.put(name, value);
    }

}
