package com.steeplesoft.jsf.facestester.servlet;


import com.steeplesoft.jsf.facestester.servlet.impl.FacesTesterServletContext;
import com.sun.faces.config.WebConfiguration.WebContextInitParameter;

import java.io.File;

import java.util.Map;

import javax.servlet.ServletContext;


public class ServletContextFactory {
    protected File webAppDirectory;
    protected WebDeploymentDescriptor descriptor;

    protected ServletContextFactory(File webAppDirectory) {
        this.webAppDirectory = webAppDirectory;
    }

    public ServletContextFactory(WebDeploymentDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public static ServletContext createServletContext(WebDeploymentDescriptor descriptor) {
        ServletContextFactory factory = new ServletContextFactory(descriptor);

        return factory.createContextForWebAppAt();
    }

    public ServletContext createContextForWebAppAt() {
        FacesTesterServletContext servletContext = new FacesTesterServletContext(); //new WebAppResourceLoader(webAppDirectory));

        for (Map.Entry<String, String> each : descriptor.getContextParameters()
                                                        .entrySet()) {
            servletContext.addInitParameter(each.getKey(), each.getValue());
        }

        servletContext.addInitParameter(WebContextInitParameter.ExpressionFactory.getQualifiedName(), WebContextInitParameter.ExpressionFactory.getDefaultValue());

//        servletContext.addInitParameter("com.sun.faces.injectionProvider", "com.steeplesoft.jsf.facestester.injection.FacesTesterInjectionProvider");

        return servletContext;
    }

}