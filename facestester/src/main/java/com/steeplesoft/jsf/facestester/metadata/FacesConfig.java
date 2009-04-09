/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.jsf.facestester.metadata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.event.PhaseListener;
import javax.faces.render.Renderer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author jasonlee
 */
public class FacesConfig {
    protected List<ManagedBeanMetaData> managedBeans = new ArrayList<ManagedBeanMetaData>();
    protected List<ComponentMetaData> components = new ArrayList<ComponentMetaData>();
    protected List<RendererMetaData> renderers = new ArrayList<RendererMetaData>();
    protected List<PhaseListenerMetaData> phaseListeners = new ArrayList<PhaseListenerMetaData>();

    public FacesConfig(File configFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(configFile);
        doc.getDocumentElement().normalize();
        loadManagedBeanInfo(doc);
        loadComponentInfo(doc);
        loadRendererInfo(doc);
        loadPhaseListenerInfo(doc);
    }

    public List<ManagedBeanMetaData> getManagedBeans() {
        return Collections.unmodifiableList(managedBeans);
    }

    public void validateManagedBeans() throws IOException {
        for (ManagedBeanMetaData mbmd : managedBeans) {
            try {
                Class clazz = Class.forName(mbmd.getBeanClass());
                clazz.newInstance();
                Logger.getLogger("FacesConfig").fine("Managed bean " + mbmd.getBeanName() +
                        " ("+ mbmd.getBeanClass() +") loaded correctly.");
            } catch (Exception ex) {
                throwAssertionError("The managed bean '" + mbmd.getBeanName() +
                        "' could not be loaded:  " + mbmd.getBeanClass() + " not found");
            }
        }
    }

    public void validateComponents() throws IOException {
        for (ComponentMetaData cmd : components) {
            try {
                Class clazz = Class.forName(cmd.getComponentClass());
                Object obj = clazz.newInstance();
                if (!(obj instanceof UIComponent)) {
                    throwAssertionError("The component '" + cmd.getComponentClass() +
                            "' was found but does not extend UIComponent.");
                }
                Logger.getLogger("FacesConfig").fine("The component '" + cmd.getComponentClass() +
                        "' loaded correctly.");
            } catch (Exception ex) {
                throwAssertionError("The component '" + cmd.getDisplayName() +
                        "' could not be loaded:  " + cmd.getComponentClass() + " not found");
            }
        }
    }

    public void validatePhaseListeners() {
        for (PhaseListenerMetaData plmd : phaseListeners) {
            try {
                Class clazz = Class.forName(plmd.getClassName());
                Object obj = clazz.newInstance();
                if (!(obj instanceof PhaseListener)) {
                    throwAssertionError("The PhaseListener '" + plmd.getClassName() +
                            "' was found but does not implement PhaseListener.");
                }
                Logger.getLogger("FacesConfig").fine("The PhaseListener '" + plmd.getClassName() +
                        "'  loaded correctly.");
            } catch (Exception ex) {
                throwAssertionError("The PhaseListener '" + plmd.getClassName() +
                        "' could not be loaded:  " + plmd.getClassName() + " not found");
            }
        }
    }

    public void validateRenderers() {
        for (RendererMetaData rmd : renderers) {
            try {
                Class clazz = Class.forName(rmd.getRendererClass());
                Object obj = clazz.newInstance();
                if (!(obj instanceof Renderer)) {
                    throwAssertionError("The Renderer '" + rmd.getRendererType() +
                            "' was found but does not implement Renderer.");
                }
                Logger.getLogger("FacesConfig").fine("The renderer type '" + rmd.getRendererType() +
                        "' (using class "+ rmd.getRendererClass() +") loaded correctly.");
            } catch (Exception ex) {
                throwAssertionError("The renderer type '" + rmd.getRendererType() +
                        "' could not be loaded:  " + rmd.getRendererClass() + " not found");
            }
        }
    }

    private String getValue(Node node, String name) {
        String retValue = null;
        NodeList element = ((Element) node).getElementsByTagName(name);
        NodeList valueNodeList = ((Element) element.item(0)).getChildNodes();

        if (valueNodeList.getLength() > 0) {
            retValue = valueNodeList.item(0).getNodeValue();
        }
        return retValue;
    }

    private void loadManagedBeanInfo(Document doc) {
        NodeList beanNodes = doc.getElementsByTagName("managed-bean");
        for (int i = 0; i < beanNodes.getLength(); i++) {
            Node beanNode = beanNodes.item(i);
            if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                ManagedBeanMetaData mbmd = new ManagedBeanMetaData();
                String beanName = getValue(beanNode, "managed-bean-name");
                String beanScope = getValue(beanNode, "managed-bean-scope");
                String beanClass = getValue(beanNode, "managed-bean-class");
                mbmd.setBeanClass(beanClass);
                mbmd.setBeanName(beanName);
                mbmd.setBeanScope(beanScope);
                managedBeans.add(mbmd);
            }
        }
    }

    private void loadComponentInfo(Document doc) {
        NodeList nodes = doc.getElementsByTagName("component");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                ComponentMetaData cmd = new ComponentMetaData();
                cmd.setComponentClass(getValue(node, "component-class"));
                cmd.setComponentType(getValue(node, "component-type"));
                cmd.setDisplayName(getValue(node, "display-name"));

                components.add(cmd);
            }
        }
    }

    private void loadRendererInfo(Document doc) {
        NodeList nodes = doc.getElementsByTagName("renderer");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                RendererMetaData rmd = new RendererMetaData();
                rmd.setRendererClass(getValue(node, "renderer-class"));
                rmd.setRendererType(getValue(node, "renderer-type"));
                rmd.setComponentFamily(getValue(node, "component-family"));

                renderers.add(rmd);
            }
        }
    }

    private void loadPhaseListenerInfo(Document doc) {
        NodeList nodes = doc.getElementsByTagName("phase-listener");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                PhaseListenerMetaData plmd = new PhaseListenerMetaData();
                NodeList valueNodeList = ((Element) node).getChildNodes();
                String value = valueNodeList.item(0).getNodeValue();
                plmd.setClassName(valueNodeList.item(0).getNodeValue());

                phaseListeners.add(plmd);
            }
        }
    }

    private void throwAssertionError(String message) {
        Logger.getLogger("FacesConfig").severe(message);
        throw new AssertionError(message);
    }
}
