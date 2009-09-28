/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.servlet;

import com.steeplesoft.jsf.facestester.FacesTesterException;
import com.steeplesoft.jsf.facestester.Util;
import com.steeplesoft.jsf.facestester.servlet.impl.FacesTesterServletConfig;
import java.util.HashMap;
import java.util.Map;
import javax.media.j3d.Node;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *
 * @author jasonlee
 */
public class ServletWrapper {
    protected Servlet instance;
    protected boolean initialized = false;
    protected Map<String, String> initParams = new HashMap<String, String>();
    protected String name;

    public ServletWrapper(String name, Servlet instance) {
        this.name = name;
        this.instance = instance;
    }

    public void init(ServletContext servletContext) {
        FacesTesterServletConfig servletConfig = new FacesTesterServletConfig(name, servletContext);
        for (Map.Entry<String, String> entry : initParams.entrySet()) {
            servletConfig.setInitParam(entry.getKey(), entry.getValue());
        }
        
        try {
            instance.init(servletConfig);
            initialized = true;
        } catch (ServletException ex) {
            throw new FacesTesterException("Unable to init the servlet '" + name + "':  " + ex.getLocalizedMessage());
        }
    }

    public Servlet getServlet() {
        return instance;
    }

    public String getInitParam(String key) {
        return this.initParams.get(key);
    }

    public void setInitParam(String key, String value) {
        this.initParams.put(key, value);
    }

    public Map<String, String> getInitParams() {
        return initParams;
    }
}
