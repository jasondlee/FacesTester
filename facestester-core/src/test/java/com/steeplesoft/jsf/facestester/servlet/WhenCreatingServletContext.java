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
package com.steeplesoft.jsf.facestester.servlet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import com.steeplesoft.jsf.facestester.servlet.impl.FacesTesterServletContext;


public class WhenCreatingServletContext {
    @Test
    public void shouldLocateWebAppDirectoryFromSystemProperty() throws IOException {
        TestWebAppDirectoryCreator creator = new TestWebAppDirectoryCreator();
        File webAppDirectory = creator.createTestWebAppWithDescriptor(getClass().getResourceAsStream("/webapp/WEB-INF/web.xml"));

        Properties properties = System.getProperties();
        try {
            System.setProperty("facestester.webAppPath", webAppDirectory.getAbsolutePath());
            assertThat(ServletContextFactory.createServletContext(new WebDeploymentDescriptor(webAppDirectory)).getInitParameter("javax.faces.DEFAULT_SUFFIX"), is(".xhtml"));
        }
        finally {
            System.setProperties(properties);
        }
    }

    @Test
    public void shouldLoadMimeTypesFromProperties() throws IOException {

        TestWebAppDirectoryCreator creator = new TestWebAppDirectoryCreator();
        File webAppDirectory = creator.createTestWebAppWithDescriptor(getClass().getResourceAsStream("/webapp/WEB-INF/web.xml"));

        Properties properties = System.getProperties();
        try {
            System.setProperty("facestester.webAppPath", webAppDirectory.getAbsolutePath());
            FacesTesterServletContext context = ServletContextFactory.createServletContext(new WebDeploymentDescriptor(webAppDirectory));
            assertThat(context.getMimeType("/images/image.png"), is("image/png"));
            assertThat(context.getMimeType("/resources/misc/some.foo"), is("application/x-foo"));
            assertNull(context.getMimeType(null));
            assertNull(context.getMimeType(""));
            assertNull(context.getMimeType("some."), null);
        }
        finally {
            System.setProperties(properties);
        }

    }

    @Test
    public void shoudBeAbleToGetContextPath() throws IOException {
        TestWebAppDirectoryCreator creator = new TestWebAppDirectoryCreator();
        File webAppDirectory = creator.createTestWebAppWithDescriptor(getClass().getResourceAsStream("/webapp/WEB-INF/web.xml"));

        Properties properties = System.getProperties();
        try {
            System.setProperty("facestester.webAppPath", webAppDirectory.getAbsolutePath());
            FacesTesterServletContext context = ServletContextFactory.createServletContext(new WebDeploymentDescriptor(webAppDirectory));
            assertThat(context.getContextPath(), is(""));
        }
        finally {
            System.setProperties(properties);
        }
    }
}
