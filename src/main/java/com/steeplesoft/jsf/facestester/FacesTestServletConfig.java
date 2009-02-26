/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester;

import java.util.Enumeration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 *
 * @author jasonlee
 */
class FacesTestServletConfig implements ServletConfig {
    private FacesTestServletContext servletContext;

    public FacesTestServletConfig() {
        servletContext = new FacesTestServletContext("");
    }

    public String getServletName() {
        return "FacesServlet"; // TODO:  pull this from web.xml?
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public String getInitParameter(String name) {
        return servletContext.getInitParameter(name);
    }

    public Enumeration getInitParameterNames() {
        return servletContext.getInitParameterNames();
    }

}
