/*
 * Copyright (c) 2009, Jason Lee <jason@steeplesoft.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the <ORGANIZATION> nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.steeplesoft.jsf.facestester.servlet;


import com.steeplesoft.jsf.facestester.servlet.impl.FacesTesterServletContext;
import java.io.File;
import java.util.Map;


public class ServletContextFactory {
    private File webAppDirectory;
    private WebDeploymentDescriptor webDescriptor;

    protected ServletContextFactory(File webAppDirectory) {
        this.webAppDirectory = webAppDirectory;
    }

    protected ServletContextFactory(WebDeploymentDescriptor webDescriptor) {
        this.webDescriptor = webDescriptor;
        this.webAppDirectory = webDescriptor.getWebAppPath();
    }

//    private static ServletContext createServletContext() {
//        ServletContextFactory factory = new ServletContextFactory(Util.lookupWebAppPath());
//
//        return factory.createContextForWebAppAt();
//    }

    public static FacesTesterServletContext createServletContext(WebDeploymentDescriptor webDesciptor) {
        ServletContextFactory factory = new ServletContextFactory(webDesciptor); //Util.lookupWebAppPath());

        return factory.createContextForWebAppAt();
    }

    public FacesTesterServletContext createContextForWebAppAt() {
        FacesTesterServletContext servletContext = new FacesTesterServletContext(webAppDirectory);

        //FacesTesterServletContext servletContext = new FacesTesterServletContext(new WebAppResourceLoader(webAppDirectory));

        for (Map.Entry<String, String> each : webDescriptor.getContextParameters().entrySet()) {
            servletContext.addInitParameter(each.getKey(), each.getValue());
        }

        for (FilterWrapper wrapper : webDescriptor.getFilters().values()) {
            wrapper.init(servletContext);
        }

        for (Map.Entry<String,String> mimeMappings : webDescriptor.getMimeTypeMappings().entrySet()) {
            servletContext.addMimeType(mimeMappings.getKey(), mimeMappings.getValue());
        }

//        servletContext.addInitParameter("com.sun.faces.injectionProvider", "com.steeplesoft.jsf.facestester.injection.FacesTesterInjectionProvider");

        return servletContext;
    }
}