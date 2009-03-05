package com.steeplesoft.jsf.facestester.servlet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class WhenCreatingServletContext {
    private ServletContextFactory contextFactory;

    @Before
    public void setUp() {
        contextFactory = new ServletContextFactory();
    }

    @Test
    public void shouldLocateWebAppDirectoryFromSystemProperty() throws IOException {
        TestWebAppDirectoryCreator creator = new TestWebAppDirectoryCreator();
        File webAppDirectory = creator.createTestWebAppWithDescriptor(getClass().getResourceAsStream("/test-web.xml"));

        Properties properties = System.getProperties();
        try {
            System.setProperty("facestester.webAppPath", webAppDirectory.getAbsolutePath());
            assertThat(contextFactory.createContext().getInitParameter("javax.faces.DEFAULT_SUFFIX"), is(".xhtml"));
        }
        finally {
            System.setProperties(properties);
        }
    }
}
