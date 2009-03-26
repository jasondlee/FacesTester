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

    public FacesConfig(File configFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(configFile);
        doc.getDocumentElement().normalize();
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

    public List<ManagedBeanMetaData> getManagedBeans() {
        return Collections.unmodifiableList(managedBeans);
    }

    public void performStaticAnalysis() throws IOException {
        for (ManagedBeanMetaData mbmd : managedBeans) {
            try {
                Class clazz = Class.forName(mbmd.getBeanClass());
                clazz.newInstance();
                Logger.getLogger("FacesConfig").info("Managed bean " + mbmd.getBeanName() +
                        " ("+ mbmd.getBeanClass() +") loaded correctly.");
            } catch (Exception ex) {
                throw new AssertionError("The managed bean '" + mbmd.getBeanName() +
                        "' could not be loaded:  " + mbmd.getBeanClass() + " not found");
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
}
