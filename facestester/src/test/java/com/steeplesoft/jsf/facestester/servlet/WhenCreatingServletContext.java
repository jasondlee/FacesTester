package com.steeplesoft.jsf.facestester.servlet;

import com.steeplesoft.jsf.facestester.FacesTester;
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
            assertThat(ServletContextFactory.createServletContext(WebDeploymentDescriptor.createFromFile(webAppDirectory)).getInitParameter("javax.faces.DEFAULT_SUFFIX"), is(".xhtml"));
        }
        finally {
            System.setProperties(properties);
        }
    }

    @Test
    public void shoudBeAbleToGetContextPath() {
        FacesTester ft = new FacesTester();
        assertThat(ft.getServletContext().getContextPath(), is("/"));
    }
}
