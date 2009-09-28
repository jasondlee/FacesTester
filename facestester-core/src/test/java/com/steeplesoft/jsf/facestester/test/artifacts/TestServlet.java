/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.test.artifacts;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author jasonlee
 */
public class TestServlet implements Servlet {
    protected ServletConfig servletConfig;
    protected String dummyParam;
    public static final String TEST_PARAM_KEY = "dummyParam";
    public static final String TEST_PARAM_VALUE = "dummyValue";

    public void init(ServletConfig sc) throws ServletException {
        this.servletConfig = sc;
        dummyParam = sc.getInitParameter(TEST_PARAM_KEY);
    }

    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Just a test servlet");
    }

    public String getServletInfo() {
        return "TestServlet";
    }

    public void destroy() {
        //
    }

    public String getDummyParam() {
        return dummyParam;
    }


}
