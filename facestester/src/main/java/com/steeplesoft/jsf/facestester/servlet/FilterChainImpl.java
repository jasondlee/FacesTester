/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author jasonlee
 */
public class FilterChainImpl implements FilterChain {
    private List<Filter> filters;
    private int index = 0;

    public FilterChainImpl(List<Filter> filters) {
        this.filters = filters;
    }

    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        if (index < filters.size()) {
            Filter filter = filters.get(index);
            index++;
            filter.doFilter(request, response, this);
        }
    }

}
