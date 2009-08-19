/*
 * Copyright (c) 2009, Jason Lee <jason@steeplesoft.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the <ORGANIZATION> nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.steeplesoft.jsf.facestester.servlet;

import java.io.File;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WebDeploymentDescriptor {
    protected File webAppPath;
    private Map<String, String> contextParameters = new HashMap<String, String>();
    private List<EventListener> listeners = new ArrayList<EventListener>();
    private Map<String, FilterWrapper> filters = new HashMap<String, FilterWrapper>();
    private List<Mapping> filterMappings = new ArrayList<Mapping>();
    private Map<String,String> mimeTypeMappings;
    private List<Mapping> servletMappings;

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

    public List<Mapping> getFilterMappings() {
        return filterMappings;
    }

    public void setFilterMappings(List<Mapping> filterMappings) {
        this.filterMappings = filterMappings;
    }

    public Map<String,String> getMimeTypeMappings() {
        if (mimeTypeMappings == null) {
            mimeTypeMappings = new HashMap<String,String>();
        }
        return mimeTypeMappings;
    }

    public void setMimeTypeMappings(Map<String,String> mimeTypeMappings) {
        this.mimeTypeMappings = mimeTypeMappings;
    }

    public void addFilterMapping(String urlPattern, String filterName) {
        this.filterMappings.add(new Mapping(urlPattern, filterName));
    }

    public List<Mapping> getServletMappings() {
        return this.servletMappings;
    }
}