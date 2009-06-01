package com.steeplesoft.jsf.facestester.servlet;

import com.steeplesoft.jsf.facestester.Util;

import com.sun.faces.config.WebConfiguration.WebContextInitParameter;

import java.io.File;

import java.util.Map;

import javax.servlet.ServletContext;


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

    private static ServletContext createServletContext() {
        ServletContextFactory factory = new ServletContextFactory(Util.lookupWebAppPath());

        return factory.createContextForWebAppAt();
    }

    public static ServletContext createServletContext(WebDeploymentDescriptor webDesciptor) {
        ServletContextFactory factory = new ServletContextFactory(webDesciptor); //Util.lookupWebAppPath());

        return factory.createContextForWebAppAt();
    }

    public ServletContext createContextForWebAppAt() {
        FacesTesterServletContext servletContext = new FacesTesterServletContext(new WebAppResourceLoader(webAppDirectory));

//        WebDeploymentDescriptor descriptor = WebDeploymentDescriptor.createFromFile(webAppDirectory);

        for (Map.Entry<String, String> each : webDescriptor.getContextParameters().entrySet()) {
            servletContext.addInitParameter(each.getKey(), each.getValue());
        }

        servletContext.addInitParameter(WebContextInitParameter.ExpressionFactory.getQualifiedName(), WebContextInitParameter.ExpressionFactory.getDefaultValue());

        for (FilterWrapper wrapper : webDescriptor.getFilters().values()) {
            wrapper.init(servletContext);
        }

//        servletContext.addInitParameter("com.sun.faces.injectionProvider", "com.steeplesoft.jsf.facestester.injection.FacesTesterInjectionProvider");

        return servletContext;
    }
}