package com.steeplesoft.jsf.facestester.servlet;

import com.steeplesoft.jsf.facestester.FacesTesterException;

import com.sun.faces.config.WebConfiguration.WebContextInitParameter;
import org.springframework.mock.web.MockServletContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletContext;


public class ServletContextFactory {
    private File webAppDirectory;

    public ServletContextFactory(File webAppDirectory) {
        this.webAppDirectory = webAppDirectory;
    }

    public static ServletContext createServletContext() {
        ServletContextFactory factory = new ServletContextFactory(lookupWebAppPath());

        return factory.createContextForWebAppAt();
    }

    public ServletContext createContextForWebAppAt() {
        MockServletContext servletContext = new MockServletContext(new WebAppResourceLoader(webAppDirectory));

        WebDeploymentDescriptor descriptor = WebDeploymentDescriptor.createFromStream(streamWebXmlFrom(webAppDirectory));

        for (Map.Entry<String, String> each : descriptor.getContextParameters()
                                                        .entrySet()) {
            servletContext.addInitParameter(each.getKey(), each.getValue());
        }

        servletContext.addInitParameter(WebContextInitParameter.ExpressionFactory.getQualifiedName(), WebContextInitParameter.ExpressionFactory.getDefaultValue());

//        servletContext.addInitParameter("com.sun.faces.injectionProvider", "com.steeplesoft.jsf.facestester.injection.FacesTesterInjectionProvider");

        return servletContext;
    }

    private static File lookupWebAppPath() {
        String webAppPath = System.getProperty("facestester.webAppPath");

        // The system property has not been set, so let's look in a couple of
        // sensible locations to see if we can figure what it should be.
        if (webAppPath == null) {
            try {
                String[] locationsToCheck = new String[] {
                        "src/test/webapp", "src/test/resources",
                        "src/test/resources/webapp", "src/main/webapp"
                    };

                for (String location : locationsToCheck) {
                    File dir = new File(location);
                    File webXml = new File(dir, "WEB-INF/web.xml");

                    if (webXml.exists()) {
                        webAppPath = dir.getCanonicalPath();

                        break;
                    }
                }
            } catch (IOException ioe) {
                // swallow this and throw the excpetion below
            }
        }

        // The web app path was not set, nor could it be found, so let's throw
        // an exception and abort
        if (webAppPath == null) {
            throw new FacesTesterException(
                "The facestester.webAppPath system property has not been set and could not calculated.");
        }

        Logger.getLogger(ServletContextFactory.class.getName())
              .fine("The facestester.webAppPath system property was set to " +
            webAppPath);

        return new File(webAppPath);
    }

    private InputStream streamWebXmlFrom(File webAppDirectory) {
        File webXml = new File(webAppDirectory, "WEB-INF/web.xml");

        if (!webXml.exists()) {
            throw new FacesTesterException(webXml.getAbsolutePath() +
                " does not exist");
        }

        try {
            return new FileInputStream(webXml);
        } catch (FileNotFoundException e) {
            throw new FacesTesterException("Unable to read web.xml at " +
                webXml.getAbsolutePath(), e);
        }
    }
}
