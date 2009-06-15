/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.servlet.impl;

import java.util.Enumeration;
import java.util.Map;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import com.steeplesoft.jsf.facestester.util.EnumerationImpl;

/**
 *
 * @author jasonlee
 */
public class FilterConfigImpl implements FilterConfig {
    private ServletContext context;
    private String filterName;
    private Map<String,String> initParameters;

    public FilterConfigImpl (ServletContext context, String filterName, Map<String, String> initParameters) {
        this.context = context;
        this.filterName = filterName;
        this.initParameters = initParameters;
    }

    public String getFilterName() {
        return filterName;
    }

    public ServletContext getServletContext() {
        return context;
    }

    public String getInitParameter(String name) {
        return initParameters.get(name);
    }

    public Enumeration getInitParameterNames() {
        return new EnumerationImpl(initParameters.keySet());
    }

}
