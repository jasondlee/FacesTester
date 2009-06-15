/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.jsf.facestester.servlet;

import org.springframework.mock.web.MockServletContext;

/**
 *
 * @author jasonlee
 */
public class FacesTesterServletContext extends MockServletContext {
    public FacesTesterServletContext() {
        super();
    }
    
    public FacesTesterServletContext(WebAppResourceLoader webAppResourceLoader) {
        super(webAppResourceLoader);
    }

    public String getContextPath() {
        return "/";
    }
}