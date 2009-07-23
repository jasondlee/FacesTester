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
package com.steeplesoft.jsf.facestester;

import com.steeplesoft.jsf.facestester.servlet.ServletContextFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author jasonlee
 */
public class Util {

    private static final String[] locationsToCheck = new String[]{"src/test/webapp", "src/test/resources", "src/test/resources/webapp", "src/main/webapp"};
    private static Boolean isMojarra = null;
    private static Boolean isMyFaces = null;

    public static Logger getLogger() {
        return Logger.getLogger(FacesTester.class.getName());
    }

    public static boolean isMojarra() {
        if (isMojarra == null) {
            try {
                Class.forName("com.sun.faces.config.ConfigureListener");
                isMojarra = true;
            } catch (ClassNotFoundException ex) {
                //
            }
        }
        return isMojarra;
    }

    public static String getNodeValue(Node node, String name) {
        return getNodeValue(node, name, true);
    }

    public static String getNodeValue(Node node, String name, boolean trim) {
        String retValue = null;
        NodeList element = ((Element) node).getElementsByTagName(name);
        if (element.getLength() > 0) {
            NodeList valueNodeList = ((Element) element.item(0)).getChildNodes();

            if (valueNodeList.getLength() > 0) {
                retValue = valueNodeList.item(0).getNodeValue();
            }
        }

        return (trim && (retValue != null)) ? retValue.trim() : retValue;
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

                for (String location : locationsToCheck) {
                    File dir = new File(location);
                    if (dir.isDirectory()) {
                        File webXml = new File(dir, "WEB-INF/web.xml");

                        if (webXml.exists()) {
                            webAppPath = dir.getCanonicalPath();

                            break;
                        }
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
                    "The facestester.webAppPath system property has not been set and could not be calculated.");
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

    public static <T> Enumeration<T> singletonEnumeration(T single) {
        return Collections.enumeration(Collections.singleton(single));
    }

    public static <T> Enumeration<T> enumeration(Collection<T> input) {
        if(input == null) {
            return emptyEnumeration();
        } else {
            return Collections.enumeration(input);
        }
    }

    public static <T> Enumeration<T> emptyEnumeration() {
        return Collections.enumeration((List<T>)Collections.emptyList());
    }

    public static <T> T getFirst(List<T> list) {
        return list == null || list.size()==0 ? null : list.get(0);
    }

    public static <T> Enumeration<T> enumeration(final T[] values) {
        return new Enumeration<T>() {
            private int i = 0;
            public boolean hasMoreElements() {
                return values!=null && i<values.length;
            }

            public T nextElement() {
                if(!this.hasMoreElements()) {
                    throw new NoSuchElementException();
                }
                return values[i++];
            }
        };
    }
}
