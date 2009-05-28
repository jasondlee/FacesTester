package com.steeplesoft.jsf.facestester.servlet;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

public class WhenLoadingResourceFromServletContext {
    @Test
    public void shouldFindFileFromWebAppDirectory() throws IOException {
        File webAppDirectory = new TestWebAppDirectoryCreator().createTestWebAppWithDescriptor(
                getClass().getResourceAsStream("/webapp/WEB-INF/web.xml"));

        ResourceLoader loader = new WebAppResourceLoader(webAppDirectory);

        assertTrue(loader.getResource("/WEB-INF/web.xml").exists());
    }
}
