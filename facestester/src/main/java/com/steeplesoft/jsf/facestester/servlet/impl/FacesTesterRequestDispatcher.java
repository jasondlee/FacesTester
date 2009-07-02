/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.servlet.impl;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author jasonlee
 */
public class FacesTesterRequestDispatcher implements RequestDispatcher {
    private String url;
    public FacesTesterRequestDispatcher(String url) {
        this.url = url;
    }

    public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {
//        request.
    }

    public void include(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
