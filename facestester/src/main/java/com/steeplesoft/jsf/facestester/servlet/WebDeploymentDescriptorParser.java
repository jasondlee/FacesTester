package com.steeplesoft.jsf.facestester.servlet;

import com.steeplesoft.jsf.facestester.FacesTesterException;

import com.steeplesoft.jsf.facestester.Util;
import java.io.File;

import java.io.InputStream;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

            return descriptor;
        } catch (Exception ex) {
            throw new FacesTesterException("Unable to parse web deployment descriptor", ex);
        }

    }

    private void loadContextParams(Document doc, WebDeploymentDescriptor descriptor) {
        NodeList nodes = doc.getElementsByTagName("context-param");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String paramName = Util.getNodeValue(node, "param-name").trim();
                String paramValue = Util.getNodeValue(node, "param-value");
                if (paramValue != null) {
                    paramValue = paramValue.trim();
                }
                descriptor.getContextParameters().put(paramName, paramValue);
            }
        }
    }

    private void loadListeners(Document doc, WebDeploymentDescriptor descriptor) {
        NodeList nodes = doc.getElementsByTagName("listener");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String listenerClass = Util.getNodeValue(node, "listener-class").trim();
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
                String filterName = Util.getNodeValue(node, "filter-name").trim();
                String filterClass = Util.getNodeValue(node, "filter-class").trim();
                FilterWrapper wrapper = new FilterWrapper(filterName);
                wrapper.setFilter(Util.createInstance(Filter.class, filterClass));
                NodeList children = ((Element) node).getElementsByTagName("init-param");
                Map<String, String> initParameters = new HashMap<String, String>();

                for (int j = 0; j < children.getLength(); j++) {
                    Node child = children.item(j);
                    if (child.getNodeType() == Node.ELEMENT_NODE) {
                        String paramName = Util.getNodeValue(child, "param-name");
                        String paramValue = Util.getNodeValue(child, "param-value");
                        if (paramName == null) {
                            throw new FacesTesterException("An init-param name for the filter '" + filterName + "' is null.");
                        }
                        initParameters.put(paramName.trim(), (paramValue != null) ? paramValue.trim() : paramValue);
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
                String filterName = Util.getNodeValue(node, "filter-name").trim();
                String urlPattern = Util.getNodeValue(node, "url-pattern").trim();

                if (descriptor.getFilters().get(filterName) == null) {
                    throw new FacesTesterException ("The filter-mapping '" +
                            urlPattern + "' for filter '" + filterName +
                            "' is invalid because the filter could not be found.");
                }

                descriptor.getFilterMappings().put(urlPattern, filterName);
            }
        }
    }
}