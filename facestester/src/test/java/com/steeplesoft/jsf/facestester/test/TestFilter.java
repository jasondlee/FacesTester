/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.test;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author jasonlee
 */
public class TestFilter implements Filter {
    public static Boolean FILTER_RUN = Boolean.FALSE;
    private Map<String, String> params = new HashMap<String, String>();

    public void init(FilterConfig filterConfig) throws ServletException {
        Enumeration e = filterConfig.getInitParameterNames();
        while (e.hasMoreElements()) {
            String name = (String)e.nextElement();
            params.put(name, filterConfig.getInitParameter(name));
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FILTER_RUN = true;
        chain.doFilter(request, response);
    }

    public void destroy() {
        //
    }
}
