/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester;

import java.io.IOException;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletException;

/**
 *
 * @author jasonlee
 */
public class FacesTester {
    
    private FacesServlet facesServlet;
    private FacesTestServletConfig servletConfig;

    public FacesTester() throws ServletException {
        facesServlet = new FacesServlet();
        servletConfig = new FacesTestServletConfig();
        facesServlet.init(servletConfig);
    }

    public void requestPage(String uri) throws IOException, ServletException {
        FacesTesterRequest request = null;
        FacesTestResponse response = null;
        facesServlet.service(request, response);
    }

}