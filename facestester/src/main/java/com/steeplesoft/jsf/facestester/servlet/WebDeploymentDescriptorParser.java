package com.steeplesoft.jsf.facestester.servlet;

import com.steeplesoft.jsf.facestester.FacesTesterException;

import com.steeplesoft.jsf.facestester.Util;
import java.io.File;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import static org.xml.sax.helpers.XMLReaderFactory.createXMLReader;

import java.io.InputStream;
import java.util.EventListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WebDeploymentDescriptorParser {

    public WebDeploymentDescriptor parse1(InputStream webXmlStream) {
        try {
            WebXmlHandler handler = new WebXmlHandler();

            XMLReader reader = createXMLReader();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(webXmlStream));

            WebDeploymentDescriptor descriptor = new WebDeploymentDescriptor();
            descriptor.setContextParameters(handler.listContextParameters());

            return descriptor;
        } catch (Exception e) {
            throw new FacesTesterException("Unable to parse web deployment descriptor", e);
        }
    }

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
}