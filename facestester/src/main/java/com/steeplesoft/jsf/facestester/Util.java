/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.jsf.facestester;

import com.steeplesoft.jsf.facestester.servlet.ServletContextFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author jasonlee
 */
public class Util {

    public static String getNodeValue(Node node, String name) {
        String retValue = null;
        NodeList element = ((Element) node).getElementsByTagName(name);
        NodeList valueNodeList = ((Element) element.item(0)).getChildNodes();

        if (valueNodeList.getLength() > 0) {
            retValue = valueNodeList.item(0).getNodeValue();
        }

        return retValue;
    }

    public static <T> T createInstance(Class<T> type, String className) {
        try {
            Class clazz = Class.forName(className);
            return (T) clazz.newInstance();
        } catch (Exception ex) {
            throw new FacesTesterException("Unable to load  class " + className +
                    ".  Root case:  " + ex.getMessage(), ex);
        }
    }

    public static File lookupWebAppPath() {
        String webAppPath = System.getProperty("facestester.webAppPath");

        // The system property has not been set, so let's look in a couple of
        // sensible locations to see if we can figure what it should be.
        if (webAppPath == null) {
            try {
                String[] locationsToCheck = new String[]{
                    "src/test/webapp", "src/test/resources",
                    "src/test/resources/webapp", "src/main/webapp"
                };

                for (String location : locationsToCheck) {
                    File dir = new File(location);
                    File webXml = new File(dir, "WEB-INF/web.xml");

                    if (webXml.exists()) {
                        webAppPath = dir.getCanonicalPath();

                        break;
                    }
                }
            } catch (IOException ioe) {
                // swallow this and throw the excpetion below
            }
        }

        // The web app path was not set, nor could it be found, so let's throw
        // an exception and abort
        if (webAppPath == null) {
            throw new FacesTesterException(
                    "The facestester.webAppPath system property has not been set and could not calculated.");
        }

        Logger.getLogger(ServletContextFactory.class.getName()).fine("The facestester.webAppPath system property was set to " +
                webAppPath);

        return new File(webAppPath);
    }

    public static InputStream streamWebXmlFrom(File webAppDirectory) {
        File webXml = new File(webAppDirectory, "WEB-INF/web.xml");

        if (!webXml.exists()) {
            throw new FacesTesterException(webXml.getAbsolutePath() +
                    " does not exist");
        }

        try {
            return new FileInputStream(webXml);
        } catch (FileNotFoundException e) {
            throw new FacesTesterException("Unable to read web.xml at " +
                    webXml.getAbsolutePath(), e);
        }
    }
}