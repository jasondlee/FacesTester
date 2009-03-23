package com.steeplesoft.jsf.facestester.servlet;

import com.steeplesoft.jsf.facestester.FacesTesterException;
import org.springframework.mock.web.MockServletContext;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

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

        for (Map.Entry<String, String> each : descriptor.getContextParameters().entrySet()) {
            System.err.println("key = '" + each.getKey() + "' value ='" + each.getValue() + "'");
            servletContext.addInitParameter(each.getKey(), each.getValue());
        }

        return servletContext;
    }

    private static File lookupWebAppPath() {
        String webAppPath = System.getProperty("facestester.webAppPath");

        // The system property has not been set, so let's look in a couple of
        // sensible locations to see if we can figure what it should be.
        if (webAppPath == null) {
            try {
                File testDirCheck = new File("src/test/webapp");
                if (testDirCheck.exists()) {
                    webAppPath = testDirCheck.getCanonicalPath();
                } else {
                    File mainDirCheck = new File("src/main/webapp");
                    if (mainDirCheck.exists()) {
                        webAppPath = mainDirCheck.getCanonicalPath();
                    }
                }
            } catch (IOException ioe) {
                // swallow this and throw the excpetion below
            }
        }

        // The web app path was not set, nor could it be found, so let's throw
        // an exception and abort
        if (webAppPath == null) {
                throw new FacesTesterException("The facestester.webAppPath system property has not been set and could not calculated.");
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

