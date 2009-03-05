package com.steeplesoft.jsf.facestester.servlet;

import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

public class WebAppResourceLoader implements ResourceLoader {
    private File searchPath;

    public WebAppResourceLoader(File searchPath) {
        this.searchPath = searchPath;
    }

    public Resource getResource(String location) {
        return new FileSystemResource(new File(searchPath, location));
    }

    public ClassLoader getClassLoader() {
        return getClass().getClassLoader();
    }
}
