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
import java.io.InputStream;
import java.util.Collection;
import java.util.EventListener;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.steeplesoft.jsf.facestester.FacesTesterException;
import com.steeplesoft.jsf.facestester.Util;
import com.steeplesoft.jsf.facestester.servlet.impl.FacesTesterServletConfig;
import javax.servlet.Servlet;

public class WebDeploymentDescriptorParser {

    public WebDeploymentDescriptor parse(File webXmlPath) { //InputStream stream) {
        try {
            InputStream stream = Util.streamWebXmlFrom(webXmlPath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(stream);
            doc.getDocumentElement().normalize();

            WebDeploymentDescriptor descriptor = new WebDeploymentDescriptor(webXmlPath);
            loadContextParams(doc, descriptor);
            loadListeners(doc, descriptor);
            loadFilters(doc, descriptor);
            loadFilterMappings(doc, descriptor);
            loadServlets(doc, descriptor);
            loadMimeTypes(doc, descriptor);

            return descriptor;
        } catch (Exception ex) {
            throw new FacesTesterException("Unable to parse web deployment descriptor", ex);
        }

    }

    private void loadMimeTypes(Document doc, WebDeploymentDescriptor descriptor) {
        NodeList nodes = doc.getElementsByTagName("mime-mapping");

        for (int i = 0, len = nodes.getLength(); i < len; i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String extension = Util.getNodeValue(node, "extension");
                String mimeType = Util.getNodeValue(node, "mime-type");
                descriptor.getMimeTypeMappings().put(extension, mimeType);
            }
        }
    }

    private void loadContextParams(Document doc, WebDeploymentDescriptor descriptor) {
        NodeList nodes = doc.getElementsByTagName("context-param");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String paramName = Util.getNodeValue(node, "param-name");
                String paramValue = Util.getNodeValue(node, "param-value");
                descriptor.getContextParameters().put(paramName, paramValue);
            }
        }
    }

    private void loadListeners(Document doc, WebDeploymentDescriptor descriptor) {
        NodeList nodes = doc.getElementsByTagName("listener");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String listenerClass = Util.getNodeValue(node, "listener-class");
                EventListener listener = Util.createInstance(EventListener.class, listenerClass);
                descriptor.getListeners().add(listener);
            }
        }
    }

    private void loadFilters(Document doc, WebDeploymentDescriptor descriptor) {
        NodeList nodes = doc.getElementsByTagName("filter");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String filterName = Util.getNodeValue(node, "filter-name");
                String filterClass = Util.getNodeValue(node, "filter-class");
                FilterWrapper wrapper = new FilterWrapper(filterName);
                wrapper.setFilter(Util.createInstance(Filter.class, filterClass));
                NodeList children = ((Element) node).getElementsByTagName("init-param");
                for (int j = 0; j < children.getLength(); j++) {
                    Node child = children.item(j);
                    if (child.getNodeType() == Node.ELEMENT_NODE) {
                        String paramName = Util.getNodeValue(child, "param-name");
                        String paramValue = Util.getNodeValue(child, "param-value");
                        if (paramName == null) {
                            throw new FacesTesterException("An init-param name for the filter '" + filterName + "' is null.");
                        }
                        wrapper.setInitParam(paramName, paramValue);
                    }
                }
                descriptor.getFilters().put(filterName, wrapper);
            }
        }
    }

    private void loadFilterMappings(Document doc, WebDeploymentDescriptor descriptor) {
        NodeList nodes = doc.getElementsByTagName("filter-mapping");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String filterName = Util.getNodeValue(node, "filter-name");
                FilterWrapper wrapper = descriptor.getFilters().get(filterName);
                if (wrapper == null) {
                    throw new FacesTesterException ("The filter-mapping for filter '" + filterName + "' is invalid because the filter could not be found.");
                }
                Collection<String> urlPatterns = Util.getNodesValues(node, "url-pattern");
                for(String urlPattern : urlPatterns) {
                    descriptor.addFilterMapping(urlPattern, filterName);
                }
                Collection<String> servletNames = Util.getNodesValues(node, "servlet-name");
                if(servletNames.size()>0) {
                    descriptor.addFilterMapping("/*", filterName);
                }
            }
        }
    }

    private void loadServlets(Document doc, WebDeploymentDescriptor descriptor) {
        NodeList nodes = doc.getElementsByTagName("servlet");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String servletName = Util.getNodeValue(node, "servlet-name");
                String servletClass = Util.getNodeValue(node, "servlet-class");
                Servlet instance = Util.createInstance(Servlet.class, servletClass);

                ServletWrapper wrapper = new ServletWrapper(servletName, instance);
                NodeList children = ((Element) node).getElementsByTagName("init-param");

                for (int j = 0; j < children.getLength(); j++) {
                    Node child = children.item(j);
                    if (child.getNodeType() == Node.ELEMENT_NODE) {
                        String paramName = Util.getNodeValue(child, "param-name");
                        String paramValue = Util.getNodeValue(child, "param-value");
                        if (paramName == null) {
                            throw new FacesTesterException("An init-param name for the servlet '" + servletName + "' is null.");
                        }
                        wrapper.setInitParam(paramName, paramValue);
                    }
                }
                descriptor.getServlets().put(servletName, wrapper);
            }
        }
    }
}