package com.steeplesoft.jsf.facestester.servlet;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileWriter;

public class TestWebAppDirectoryCreator {
    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    public File createTestWebAppWithDescriptor(InputStream descriptor) throws IOException {
        File webAppDirectory = new File(System.getProperty("java.io.tmpdir"), "facestester-webapp");
        if (webAppDirectory.exists()) {
            webAppDirectory.delete();
        }
        webAppDirectory.mkdir();

        createTestWebXml(webAppDirectory, descriptor);

        return webAppDirectory;
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    private File createTestWebXml(File webAppDirectory, InputStream descriptor) throws IOException {
        File webInfDirectory = new File(webAppDirectory, "WEB-INF");
        webInfDirectory.mkdir();

        File webXml = new File(webInfDirectory, "web.xml");
        webXml.createNewFile();

        FileWriter writer = new FileWriter(webXml);
        IOUtils.copy(descriptor, writer);
        writer.close();

        return webXml;
    }
}
