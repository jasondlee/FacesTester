package com.steeplesoft.jsf.facestester.servlet;

import org.springframework.mock.web.MockServletContext;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.util.Map;

public class ServletContextFactory {
    public ServletContext createContextFromDescriptor(InputStream webXmlStream) {
        MockServletContext servletContext = new MockServletContext();

        WebDeploymentDescriptor descriptor = WebDeploymentDescriptor.createFromStream(webXmlStream);
        for (Map.Entry<String, String> each : descriptor.getContextParameters().entrySet()) {
            servletContext.addInitParameter(each.getKey(), each.getValue());
        }

        return servletContext;
    }
}

