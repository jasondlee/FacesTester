package com.steeplesoft.jsf.facestester.servlet;

import static com.steeplesoft.jsf.facestester.servlet.ServletContextFactory.createServletContext;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class WhenCreatingServletContext {
    @Test
    public void shouldLocateWebAppDirectoryFromSystemProperty() throws IOException {
        TestWebAppDirectoryCreator creator = new TestWebAppDirectoryCreator();
        File webAppDirectory = creator.createTestWebAppWithDescriptor(getClass().getResourceAsStream("/webapp/WEB-INF/web.xml"));

        Properties properties = System.getProperties();
        try {
            System.setProperty("facestester.webAppPath", webAppDirectory.getAbsolutePath());
            assertThat(createServletContext().getInitParameter("javax.faces.DEFAULT_SUFFIX"), is(".xhtml"));
        }
        finally {
            System.setProperties(properties);
        }
    }
}
