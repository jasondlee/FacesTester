package com.steeplesoft.jsf.facestester.servlet;


import java.io.File;


public class WebAppResourceLoader { //implements ResourceLoader {
    private File searchPath;

    public WebAppResourceLoader(File searchPath) {
        this.searchPath = searchPath;
    }

    public ClassLoader getClassLoader() {
        return getClass().getClassLoader();
    }

//    public Resource getResource(String location) {
//        return new FileSystemResource(new File(searchPath, location));
//    }
}
