/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.jsf.facestester.metadata;

import com.steeplesoft.jsf.facestester.Util;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

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


/**
 *
 * @author jasonlee
 */
public class FacesConfig {
    protected List<ComponentMetaData> components = new ArrayList<ComponentMetaData>();
    protected List<ManagedBeanMetaData> managedBeans = new ArrayList<ManagedBeanMetaData>();
    protected List<PhaseListenerMetaData> phaseListeners = new ArrayList<PhaseListenerMetaData>();
    protected List<RendererMetaData> renderers = new ArrayList<RendererMetaData>();

    public FacesConfig(File configFile)
        throws ParserConfigurationException, SAXException, IOException {
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

    public void validateComponents() throws IOException {
        for (ComponentMetaData cmd : components) {
            try {
                Class clazz = Class.forName(cmd.getComponentClass());
                Object obj = clazz.newInstance();

                if (!(obj instanceof UIComponent)) {
                    throwAssertionError("The component '" +
                        cmd.getComponentClass() +
                        "' was found but does not extend UIComponent.");
                }

                Logger.getLogger("FacesConfig")
                      .fine("The component '" + cmd.getComponentClass() +
                    "' loaded correctly.");
            } catch (Exception ex) {
                throwAssertionError("The component '" + cmd.getDisplayName() +
                    "' could not be loaded:  " + cmd.getComponentClass() +
                    " not found");
            }
        }
    }

    public void validateManagedBeans() throws IOException {
        for (ManagedBeanMetaData mbmd : managedBeans) {
            try {
                Class clazz = Class.forName(mbmd.getBeanClass());
                clazz.newInstance();
                Logger.getLogger("FacesConfig")
                      .fine("Managed bean " + mbmd.getBeanName() + " (" +
                    mbmd.getBeanClass() + ") loaded correctly.");
            } catch (Exception ex) {
                throwAssertionError("The managed bean '" + mbmd.getBeanName() +
                        "' could not be loadedClass: " + mbmd.getBeanClass() +
                        ". Caused by: " + ex.getClass().getSimpleName() + " - " +
                        ex.getMessage());
            }
        }
    }

    public void validatePhaseListeners() {
        for (PhaseListenerMetaData plmd : phaseListeners) {
            try {
                Class clazz = Class.forName(plmd.getClassName());
                Object obj = clazz.newInstance();

                if (!(obj instanceof PhaseListener)) {
                    throwAssertionError("The PhaseListener '" +
                        plmd.getClassName() +
                        "' was found but does not implement PhaseListener.");
                }

                Logger.getLogger("FacesConfig")
                      .fine("The PhaseListener '" + plmd.getClassName() +
                    "'  loaded correctly.");
            } catch (Exception ex) {
                throwAssertionError("The PhaseListener '" +
                    plmd.getClassName() + "' could not be loaded:  " +
                    plmd.getClassName() + " not found");
            }
        }
    }

    public void validateRenderers() {
        for (RendererMetaData rmd : renderers) {
            try {
                Class clazz = Class.forName(rmd.getRendererClass());
                Object obj = clazz.newInstance();

                if (!(obj instanceof Renderer)) {
                    throwAssertionError("The Renderer '" +
                        rmd.getRendererType() +
                        "' was found but does not implement Renderer.");
                }

                Logger.getLogger("FacesConfig")
                      .fine("The renderer type '" + rmd.getRendererType() +
                    "' (using class " + rmd.getRendererClass() +
                    ") loaded correctly.");
            } catch (Exception ex) {
                throwAssertionError("The renderer type '" +
                    rmd.getRendererType() + "' could not be loaded:  " +
                    rmd.getRendererClass() + " not found");
            }
        }
    }

    private void loadComponentInfo(Document doc) {
        NodeList nodes = doc.getElementsByTagName("component");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                ComponentMetaData cmd = new ComponentMetaData();
                cmd.setComponentClass(Util.getNodeValue(node, "component-class"));
                cmd.setComponentType(Util.getNodeValue(node, "component-type"));
                cmd.setDisplayName(Util.getNodeValue(node, "display-name"));

                components.add(cmd);
            }
        }
    }

    private void loadManagedBeanInfo(Document doc) {
        NodeList beanNodes = doc.getElementsByTagName("managed-bean");

        for (int i = 0; i < beanNodes.getLength(); i++) {
            Node beanNode = beanNodes.item(i);

            if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                ManagedBeanMetaData mbmd = new ManagedBeanMetaData();
                String beanName = Util.getNodeValue(beanNode, "managed-bean-name");
                String beanScope = Util.getNodeValue(beanNode, "managed-bean-scope");
                String beanClass = Util.getNodeValue(beanNode, "managed-bean-class");
                mbmd.setBeanClass(beanClass);
                mbmd.setBeanName(beanName);
                mbmd.setBeanScope(beanScope);
                managedBeans.add(mbmd);
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

    private void loadRendererInfo(Document doc) {
        NodeList nodes = doc.getElementsByTagName("renderer");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                RendererMetaData rmd = new RendererMetaData();
                rmd.setRendererClass(Util.getNodeValue(node, "renderer-class"));
                rmd.setRendererType(Util.getNodeValue(node, "renderer-type"));
                rmd.setComponentFamily(Util.getNodeValue(node, "component-family"));

                renderers.add(rmd);
            }
        }
    }

    private void throwAssertionError(String message) {
        Logger.getLogger("FacesConfig").severe(message);
        throw new AssertionError(message);
    }
}
