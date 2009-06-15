/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.jsf.facestester.servlet;

import com.steeplesoft.jsf.facestester.servlet.impl.FilterConfigImpl;
import com.steeplesoft.jsf.facestester.FacesTesterException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *
 * @author jasonlee
 */
public class FilterWrapper {

    protected Filter filter;
    protected Map<String, String> initParams = new HashMap<String, String>();
    private String name;
    
    public FilterWrapper(String name) {
        this.name = name;
    }

    public void init(ServletContext servletContext) {
        FilterConfig filterConfig = new FilterConfigImpl(servletContext, name, initParams);
        try {
            filter.init(filterConfig);
        } catch (ServletException ex) {
            throw new FacesTesterException("An error occured while trying to init the filter '" + name + "'", ex);
        }
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Map<String, String> getInitParams() {
        return initParams;
    }

    public void setInitParams(Map<String, String> initParams) {
        this.initParams = initParams;
    }
}
