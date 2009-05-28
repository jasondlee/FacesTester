package com.steeplesoft.jsf.facestester.servlet;

import com.steeplesoft.jsf.facestester.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WebDeploymentDescriptor {
    protected File webAppPath;
    private Map<String, String> contextParameters = new HashMap<String, String>();
    private List<EventListener> listeners = new ArrayList<EventListener>();

    public WebDeploymentDescriptor() {
    }

    public WebDeploymentDescriptor(File webAppPath) {
        this.webAppPath = webAppPath;
    }

    public static WebDeploymentDescriptor createFromStream(InputStream webXml) {
        return new WebDeploymentDescriptorParser().parse(webXml);
    }

    public static WebDeploymentDescriptor createFromFile(File webAppPath) throws FileNotFoundException {
        return new WebDeploymentDescriptorParser().parse(new FileInputStream(webAppPath));
    }

    public File getWebAppPath() {
        return webAppPath;
    }

    public void setWebAppPath(File webAppPath) {
        this.webAppPath = webAppPath;
    }

    public Map<String, String> getContextParameters() {
        return contextParameters;
    }

    public void setContextParameters(Map<String, String> contextParameters) {
        this.contextParameters = contextParameters;
    }

    public List<EventListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<EventListener> listeners) {
        this.listeners = listeners;
    }
}
