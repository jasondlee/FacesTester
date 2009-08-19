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
package com.steeplesoft.jsf.facestester.context.myfaces;

import static javax.faces.FactoryFinder.FACES_CONTEXT_FACTORY;

import java.util.EventListener;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.myfaces.config.annotation.LifecycleProviderFactory;
import org.apache.myfaces.webapp.MyFacesHttpSessionAttributeListener;
import org.apache.myfaces.webapp.MyFacesHttpSessionListener;
import org.apache.myfaces.webapp.MyFacesServletContextListener;
import org.apache.myfaces.webapp.MyFacesServletRequestListener;
import org.apache.myfaces.webapp.MyfacesServletRequestAttributeListener;
import org.apache.myfaces.webapp.StartupServletContextListener;

import com.steeplesoft.jsf.facestester.FacesForm;
import com.steeplesoft.jsf.facestester.FacesLifecycle;
import com.steeplesoft.jsf.facestester.FacesTesterException;
import com.steeplesoft.jsf.facestester.context.FacesContextBuilder;
import com.steeplesoft.jsf.facestester.servlet.CookieManager;
import com.steeplesoft.jsf.facestester.servlet.WebDeploymentDescriptor;
import com.steeplesoft.jsf.facestester.servlet.impl.FacesTesterHttpServletRequest;
import com.steeplesoft.jsf.facestester.servlet.impl.FacesTesterHttpServletResponse;
import com.steeplesoft.jsf.facestester.servlet.impl.FacesTesterServletContext;

public class MyFacesFacesContextBuilder implements FacesContextBuilder {
    private FacesContextFactory facesContextFactory;
    private HttpSession session;
    private FacesTesterServletContext servletContext;
    private WebDeploymentDescriptor webDescriptor;
	private CookieManager cookieManager;
	private FacesContext current;

    public MyFacesFacesContextBuilder(FacesTesterServletContext servletContext, HttpSession session, WebDeploymentDescriptor webDescriptor, CookieManager cookieManager) {
        this.servletContext = servletContext;
        this.session = session;
        this.webDescriptor = webDescriptor;
		this.cookieManager = cookieManager;

        try {
                webDescriptor.getListeners().add(0, new StartupServletContextListener());
                webDescriptor.getListeners().add(1, new MyFacesHttpSessionAttributeListener());
                webDescriptor.getListeners().add(2, new MyFacesHttpSessionListener());
                webDescriptor.getListeners().add(3, new MyFacesServletContextListener());
                webDescriptor.getListeners().add(4, new MyfacesServletRequestAttributeListener());
                webDescriptor.getListeners().add(5, new MyFacesServletRequestListener());
        } catch (Exception ex) {
            throw new FacesTesterException("MyFaces' StartupServletContextListener was found, but could not be instantiated: " + ex.getLocalizedMessage(), ex);
        }


//        initializeFaces(servletContext);
        
    }

    public FacesContext createFacesContext(String method, FacesLifecycle lifecycle) {

        return createFacesContext(null, method, lifecycle);
    }

    public FacesContext createFacesContext(String uri, String method, FacesLifecycle lifecycle) {
        return buildFacesContext(mockServletRequest(uri, method), lifecycle);
    }

    public FacesContext createFacesContext(FacesForm form, FacesLifecycle lifecycle) {
        FacesTesterHttpServletRequest request = mockServletRequest(form.getUri(), "POST");

        for (Map.Entry<String, String> each : form.getParameterMap().entrySet()) {
            request.addParameter(each.getKey(), each.getValue());
        }

        return buildFacesContext(request, lifecycle);
    }

    protected FacesContext buildFacesContext(FacesTesterHttpServletRequest request, FacesLifecycle lifecycle) throws FacesException {
    	if(this.current != null) {
    		this.current.release();
    	}
        FacesContext context = getFacesContextFactory().getFacesContext(servletContext, request,
                new FacesTesterHttpServletResponse(this.cookieManager), lifecycle.getUnderlyingLifecycle());
        this.current = context;
        return context;
    }

	private FacesContextFactory getFacesContextFactory() {
		if(facesContextFactory == null) {
			facesContextFactory = (FacesContextFactory) FactoryFinder.getFactory(FACES_CONTEXT_FACTORY);
		}
		return facesContextFactory;
	}

    /*
     * This is a pretty simple solution that will likely need to be replaced,
     * but should get us going for now.
     */
    private void addQueryParameters(FacesTesterHttpServletRequest servletRequest, String uri) {
        int qmark = uri.indexOf("?");

        if (qmark > -1) {
            String queryString = uri.substring(qmark + 1);
            String[] params = queryString.split("&");

            for (String param : params) {
                String[] parts = param.split("=");

                if (parts.length == 1) {
                    servletRequest.addParameter(parts[0], "");
                } else {
                    servletRequest.addParameter(parts[0], parts[1]);
                }
            }

            servletRequest.setQueryString(queryString);
        }
    }

    private void initializeFaces(ServletContext servletContext) {
        ServletContextEvent sce = new ServletContextEvent(servletContext);
        HttpSessionEvent hse = new HttpSessionEvent(session);
        List<EventListener> listeners = webDescriptor.getListeners();

        for (EventListener listener : listeners) {
            if (listener instanceof ServletContextListener) {
                ((ServletContextListener) listener).contextInitialized(sce);
            }
        }
        // Do all SCLs need to be called first?  Probably can't hurt...
        for (EventListener listener : listeners) {
            if (listener instanceof HttpSessionListener) {
                ((HttpSessionListener) listener).sessionCreated(hse);
            }
        }
    }

    private FacesTesterHttpServletRequest mockServletRequest(String uri, String method) {
        FacesTesterHttpServletRequest servletRequest;
        if (uri != null) {
            servletRequest = new FacesTesterHttpServletRequest(servletContext, method,uri, cookieManager);
            servletRequest.setServletPath(uri);
        } else {
            servletRequest = new FacesTesterHttpServletRequest(servletContext, cookieManager);
        }
        servletRequest.setSession(session);

        if (uri != null) {
            addQueryParameters(servletRequest, uri);
        }

        return servletRequest;
    }
    
    
	public void release() {
		if(this.current != null) {
			this.current.release();
		}
	    facesContextFactory = null;
	    session = null;
	    servletContext = null;
	    webDescriptor = null;
		cookieManager = null;
		current = null;
		
	}
	
	public FacesContext getCurrentFacesContext() {
		return this.current;
	}
}
