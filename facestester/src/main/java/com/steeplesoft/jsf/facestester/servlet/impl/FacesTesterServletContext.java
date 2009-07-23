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

import com.steeplesoft.jsf.facestester.FacesTesterException;
import com.steeplesoft.jsf.facestester.Resource;
import com.steeplesoft.jsf.facestester.ResourceLoader;
import com.steeplesoft.jsf.facestester.Util;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;


/**
 *
 * @author jasonlee
 */
public class FacesTesterServletContext implements ServletContext {

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
    private int majorVarsion = 2;
    private int minorVersion = 5;
    private String contextPath = "";

    /**
     * Creates a ServletContext suitable for a default maven setup
     */
    public FacesTesterServletContext() {
        this("src/main/webapp");
    }

    public FacesTesterServletContext(String webappDir) {
        this(new File(webappDir));
    }

    public FacesTesterServletContext(File webAppDirectory) {
        this(new DefaultResourceLoader(webAppDirectory));
    }

    public FacesTesterServletContext(ResourceLoader resourceLoader) {
        this.setResourceLoader(resourceLoader);
        mimeTypes = new HashMap<String,String>(staticMimeTypes);
    }

    public String getContextPath() {
        return this.contextPath;
    }

    public ServletContext getContext(String uripath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getMajorVersion() {
        return this.majorVarsion;
    }

    public int getMinorVersion() {
        return this.minorVersion;
    }

    public String getMimeType(String file) {
        if (file == null || file.length() == 0) {
            return null;
        }
        int idx = file.lastIndexOf('.');
        if (idx == -1) {
            return null;
        }
        String extension = file.substring(idx + 1);
        if (extension.length() == 0) {
            return null;
        }
        return mimeTypes.get(extension);
    }

    public Set getResourcePaths(String path) {
        StringBuilder sb = new StringBuilder(path.length()+2);
        if(!path.startsWith("/")) {
            sb.append("/");
        }
        sb.append(path);
        if(!path.endsWith("/")) {
            sb.append("/");
        }
        String normalizedPath = sb.toString();
        Resource resource = this.getResourceLoader().getResource(normalizedPath);

        if(resource == null || !resource.exists()) {
            return null;
        }
        File[] children = resource.getFile().listFiles();
        if(children.length==0) {
            return null;
        }
        Set<String> rval = new LinkedHashSet<String>(children.length);
        for(File child : children) {
            String childPath = normalizedPath + child.getName();
            if(child.isDirectory()) {
                childPath += "/";
            }
            rval.add(childPath);
        }
        return rval;
    }

    public URL getResource(String path) throws MalformedURLException {
        try {
            Resource res = this.getResourceLoader().getResource(path);
            URL rval = (res == null ? null : res.getURL());
            return rval;
        } catch (MalformedURLException ex){
            throw ex;
        } catch (IOException ex) {
            throw new FacesTesterException(ex);
        }
    }

    public InputStream getResourceAsStream(String path) {
        if(!path.startsWith("/")) {
            path = "/" + path;
        }
        Resource res = this.getResourceLoader().getResource(path);
        if(res==null || !res.exists()) {
            return null;
        }
        try {
            return res.getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(FacesTesterServletContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public RequestDispatcher getNamedDispatcher(String name) {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public Servlet getServlet(String name) /* throws ServletException */ {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public Enumeration getServlets() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public Enumeration getServletNames() {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public void log(String msg) {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public void log(Exception exception, String msg) {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public void log(String message, Throwable throwable) {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public String getRealPath(String path) {
        throw newUnsupportedOperationException("Not supported yet.");
    }

    public String getServerInfo() {
        return "FacesTester";
    }

    public String getInitParameter(String name) {
        return this.initParameters.get(name);
    }

    public Enumeration getInitParameterNames() {
        return Util.enumeration(this.initParameters.keySet());
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public Enumeration getAttributeNames() {
        return Util.enumeration(this.attributes.keySet());
    }

    public void setAttribute(String name, Object object) {
        this.attributes.put(name, object);
    }

    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    public String getServletContextName() {
        return "FacesTesterContext";
    }

    // ---------- Mock accessors ---------- //
    public void setInitParameter(String name, String value) {
        this.initParameters.put(name, value);
    }

    @Deprecated
    public void addInitParameter(String name, String value) {
        this.setInitParameter(name, value);
    }

    private ResourceLoader resourceLoader;

    public ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void addMimeType(String extension, String mimetype) {
        mimeTypes.put(extension, mimetype);
    }
/*
        private static final String VOID = "<VOID>";
    public static void login(Object input) {
        _log(input, VOID);
    }

    public static <T> T log(Object input, T output) {
        _log(input, output);
        return output;
    }

    private static void _log(Object input, Object output) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
        String cn = ste.getClassName();
        int ldot = cn.lastIndexOf('.');
        cn = cn.substring(ldot+1);
        String msg = cn+"."+ste.getMethodName();
        System.out.println(" +++++++++ " + msg + "(" + input + ") > " + output);

    }

    public static <T> T logout(T output) {
        _log(VOID, output);
        return output;
    }
*/
    public static RuntimeException newUnsupportedOperationException(String msg) {
        String caller = Thread.currentThread().getStackTrace()[2].getMethodName();
        RuntimeException ex = new RuntimeException(msg + ": " + caller);
        ex.fillInStackTrace();
        ex.printStackTrace(System.out);
        return ex;
    }
}
