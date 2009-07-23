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
package com.steeplesoft.jsf.facestester.servlet.impl;

import com.steeplesoft.jsf.facestester.servlet.WebAppResourceLoader;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Collections;
import java.io.InputStream;
import java.io.IOException;

import org.springframework.mock.web.MockServletContext;


/**
 *
 * @author jasonlee
 */
public class FacesTesterServletContext extends MockServletContext {

    private static final String MIME_PROPERTIES = "META-INF/facestester-mimetypes.properties";

    private static final Map<String,String> staticMimeTypes;
    static {

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream in = cl.getResourceAsStream(MIME_PROPERTIES);
        Properties props = new Properties();
        try {
            props.load(in);
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioe) {
                    // ignore
                }
            }
        }
        //noinspection unchecked
        staticMimeTypes = Collections.unmodifiableMap(
                              new HashMap<String,String>((Map)props));
        
    }

    private Map<String, Object> attributes = new HashMap<String, Object>();
    private Map<String, String> initParameters = new HashMap<String, String>();
    private Map<String,String> mimeTypes;

    public FacesTesterServletContext() {
        mimeTypes = new HashMap<String,String>(staticMimeTypes);

    }

    public FacesTesterServletContext(WebAppResourceLoader webAppResourceLoader) {
        super(webAppResourceLoader);
        mimeTypes = new HashMap<String,String>(staticMimeTypes);
    }

    public String getContextPath() {
        return "/";
    }

    public void addMimeType(String extension, String mimetype) {
        mimeTypes.put(extension, mimetype);
    }

    public String getMimeType(String extension) {
        return mimeTypes.get(extension);
    }


    /*
    public void addInitParameter(String key, String value) {
        initParameters.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public Enumeration getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    public ServletContext getContext(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getInitParameter(String key) {
        return initParameters.get(key);
    }

    public Enumeration getInitParameterNames() {
        return Collections.enumeration(initParameters.keySet());
    }

    public int getMajorVersion() {
        return 2;
    }



    public int getMinorVersion() {
        return 5;
    }

    public RequestDispatcher getNamedDispatcher(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getRealPath(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public URL getResource(String name) throws MalformedURLException {
        return this.getClass().getClassLoader().getResource(name);
    }

    public InputStream getResourceAsStream(String path) {
        if ("/WEB-INF/web.xml".equals(path)) {
            return Util.streamWebXmlFrom(Util.lookupWebAppPath()); // Ugly hack?
        }
        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        if (is == null) {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        }
        if (is == null) {
            is = ClassLoader.getSystemResourceAsStream(path);
        }
        return is;
    }

    public Set getResourcePaths(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getServerInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Servlet getServlet(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getServletContextName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Enumeration getServletNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Enumeration getServlets() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void log(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void log(Exception arg0, String arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void log(String arg0, Throwable arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeAttribute(String key) {
        attributes.put(key, null);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }
    */
}
