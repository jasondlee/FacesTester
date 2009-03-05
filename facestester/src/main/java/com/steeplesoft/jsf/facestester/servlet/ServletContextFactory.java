package com.steeplesoft.jsf.facestester.servlet;

import com.steeplesoft.jsf.facestester.FacesTesterException;
import org.springframework.mock.web.MockServletContext;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class ServletContextFactory {
    public ServletContext createContextForWebAppAt(File webAppDirectory) {
        MockServletContext servletContext = new MockServletContext(new WebAppResourceLoader(webAppDirectory));

        WebDeploymentDescriptor descriptor = WebDeploymentDescriptor.createFromStream(streamWebXmlFrom(webAppDirectory));

        for (Map.Entry<String, String> each : descriptor.getContextParameters().entrySet()) {
            servletContext.addInitParameter(each.getKey(), each.getValue());
        }

        return servletContext;
    }

    public ServletContext createContext() {
        return createContextForWebAppAt(lookupWebAppPath());
    }

    private File lookupWebAppPath() {
        String webAppPath = System.getProperty("facestester.webAppPath");

        if (webAppPath == null) {
            throw new FacesTesterException("The facestester.webAppPath system property has not been set.");
        }

        return new File(webAppPath);
    }

    private InputStream streamWebXmlFrom(File webAppDirectory) {
        File webXml = new File(webAppDirectory, "WEB-INF/web.xml");

        if (!webXml.exists()) {
            throw new FacesTesterException(webXml.getAbsolutePath() + " does not exist");
        }

        try {
            return new FileInputStream(webXml);
        } catch (FileNotFoundException e) {
            throw new FacesTesterException("Unable to read web.xml at " + webXml.getAbsolutePath(), e);
        }
    }
}

