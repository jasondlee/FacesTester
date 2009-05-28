package com.steeplesoft.jsf.facestester.servlet;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import org.junit.Assert;

public class WhenLoadingResourceFromServletContext {
    @Test
    public void shouldFindFileFromWebAppDirectory() throws IOException {
        File webAppDirectory = new TestWebAppDirectoryCreator().createTestWebAppWithDescriptor(
                getClass().getResourceAsStream("/webapp/WEB-INF/web.xml"));

//        ResourceLoader loader = new WebAppResourceLoader(webAppDirectory);
//
//        assertTrue(loader.getResource("/WEB-INF/web.xml").exists());
        Assert.fail("Reimplement this funcationality");
    }
}
