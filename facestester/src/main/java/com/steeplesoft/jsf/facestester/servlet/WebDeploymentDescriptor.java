package com.steeplesoft.jsf.facestester.servlet;

import java.io.File;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;


public class WebDeploymentDescriptor {
    protected File webAppPath;
    private Map<String, String> contextParameters = new HashMap<String, String>();
    private List<EventListener> listeners = new ArrayList<EventListener>();
    private Map<String, FilterWrapper> filters = new HashMap<String, FilterWrapper>();
    private Map<String, String> filterMappings = new HashMap<String, String>();

    public WebDeploymentDescriptor() {
    }

    public WebDeploymentDescriptor(File webAppPath) {
        this();
        this.webAppPath = webAppPath;
    }

    public static WebDeploymentDescriptor createFromFile(File webXml) {
        return new WebDeploymentDescriptorParser().parse(webXml);
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

    public Map<String, FilterWrapper> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, FilterWrapper> filters) {
        this.filters = filters;
    }

    public Map<String, String> getFilterMappings() {
        return filterMappings;
    }

    public void setFilterMappings(Map<String, String> filterMappings) {
        this.filterMappings = filterMappings;
    }
}