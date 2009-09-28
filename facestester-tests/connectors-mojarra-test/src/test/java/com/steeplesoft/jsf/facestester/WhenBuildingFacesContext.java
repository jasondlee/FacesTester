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
package com.steeplesoft.jsf.facestester;

import static com.steeplesoft.jsf.facestester.servlet.ServletContextFactory.createServletContext;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;

import com.steeplesoft.jsf.facestester.context.FacesContextBuilder;
import com.steeplesoft.jsf.facestester.context.mojarra.MojarraFacesContextBuilder;
import com.steeplesoft.jsf.facestester.servlet.WebDeploymentDescriptor;
import com.steeplesoft.jsf.facestester.servlet.impl.FacesTesterCookieManager;
import com.steeplesoft.jsf.facestester.servlet.impl.FacesTesterHttpSession;
import com.steeplesoft.jsf.facestester.servlet.impl.FacesTesterServletContext;
import com.steeplesoft.jsf.facestester.test.stubs.FakeFacesLifecycle;

public class WhenBuildingFacesContext {
    private FacesContextBuilder facesContextBuilder;

    @Before
    public void setUp() {
    	FactoryFinder.releaseFactories();
        final WebDeploymentDescriptor descriptor = new WebDeploymentDescriptor();
        FacesTesterServletContext context = createServletContext(descriptor);
        facesContextBuilder = new MojarraFacesContextBuilder(context,new FacesTesterHttpSession(context),descriptor, new FacesTesterCookieManager());
    }

    @Test
    public void shouldHaveSessionAvailableInExternalContext() {
        FacesContext facesContext = buildContext();
        assertThat(facesContext.getExternalContext().getSession(false), is(notNullValue()));
    }

    @Test
    public void shouldBuildSubsequentContextWithSameSession() {
        assertThat(buildContext().getExternalContext().getSession(false),
                is(buildContext().getExternalContext().getSession(false)));
    }

    private FacesContext buildContext() {
        return facesContextBuilder.createFacesContext("/test.xhtml", "GET", new FakeFacesLifecycle());
    }
    
}
