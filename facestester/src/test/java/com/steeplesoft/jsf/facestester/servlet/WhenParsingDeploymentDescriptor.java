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

import com.steeplesoft.jsf.facestester.FacesTesterException;
import com.steeplesoft.jsf.facestester.test.TestFilter;
import com.steeplesoft.jsf.facestester.test.TestServletContextListener;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.EventListener;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

public class WhenParsingDeploymentDescriptor {
    private WebDeploymentDescriptorParser parser = new WebDeploymentDescriptorParser();
    private static File fakeWebAppDir;

    @BeforeClass
    public static void beforeClass() {
        fakeWebAppDir = new File("WEB-INF");
        fakeWebAppDir.mkdirs();
    }

    @AfterClass
    public static void afterClass() {
        (new File(fakeWebAppDir, "web.xml")).delete();
        fakeWebAppDir.delete();
    }

    @Test
    public void shouldSetDefaultSuffixContextParameterToXhtml() throws SAXException, IOException {
        String webXml = new StringBuilder()
                .append("<web-app>")
                .append("   <context-param>")
                .append("       <param-name>javax.faces.DEFAULT_SUFFIX</param-name>")
                .append("       <param-value>.xhtml</param-value>")
                .append("   </context-param>")
                .append("</web-app>").toString();

        createTempFile(webXml);
        WebDeploymentDescriptor descriptor = parser.parse(new File("."));

        assertThat(descriptor.getContextParameters().get("javax.faces.DEFAULT_SUFFIX"), is(".xhtml"));
    }

    @Test
    public void shouldSetDefaultSuffixContextParameterToJsp() throws SAXException, IOException {
        String webXml = new StringBuilder()
                .append("<web-app>")
                .append("   <context-param>")
                .append("       <param-name>javax.faces.DEFAULT_SUFFIX</param-name>")
                .append("       <param-value>.jsp</param-value>")
                .append("   </context-param>")
                .append("</web-app>").toString();

        createTempFile(webXml);
        WebDeploymentDescriptor descriptor = parser.parse(new File("."));

        assertThat(descriptor.getContextParameters().get("javax.faces.DEFAULT_SUFFIX"), is(".jsp"));
    }

    @Test
    public void shouldNotFailWhenThereSpacesAndNewLinesInContextParamNames() {
        String webXml = new StringBuilder()
                .append("<web-app>")
                .append("   <context-param>")
                .append("       <param-name>javax.faces.DEFAULT_SUFFIX\n")
                .append("       </param-name>")
                .append("       <param-value>.spaces")
                .append("       </param-value>")
                .append("   </context-param>")
                .append("</web-app>").toString();

        createTempFile(webXml);
        WebDeploymentDescriptor descriptor = parser.parse(new File("."));
        assertThat(descriptor.getContextParameters().get("javax.faces.DEFAULT_SUFFIX"), is(".spaces"));
    }

    @Test
    public void shouldNotFailOnBlankParameterValues() {
        String webXml = new StringBuilder()
                .append("<web-app>")
                .append("   <context-param>")
                .append("       <param-name>javax.faces.DEFAULT_SUFFIX</param-name>")
                .append("       <param-value></param-value>")
                .append("   </context-param>")
                .append("</web-app>").toString();

        createTempFile(webXml);
        WebDeploymentDescriptor descriptor = parser.parse(new File("."));
        assertThat(descriptor.getContextParameters().get("javax.faces.DEFAULT_SUFFIX"), is((String)null));
    }

    @Test
    public void shouldLoadListenerInformation() {
        String webXml = new StringBuilder()
                .append("<web-app>")
                .append("   <listener>")
                .append("       <listener-class>com.steeplesoft.jsf.facestester.test.TestServletContextListener</listener-class>")
                .append("   </listener>")
                .append("</web-app>").toString();

        createTempFile(webXml);
        WebDeploymentDescriptor descriptor = parser.parse(new File("."));
        // In a Mojarra environment, this will always have at least one listener, so we
        // need to test the TestServletContextListener was found, created, and added to
        // the list.
        boolean found = false;
        for (EventListener listener : descriptor.getListeners()) {
            if (listener instanceof TestServletContextListener) {
                found = true;
            }
        }

        Assert.assertTrue(found);
   }

    @Test
    public void shouldLoadFilterInformation() {
        String webXml = new StringBuilder()
                .append("<web-app>")
                .append("   <filter>")
                .append("       <filter-name>Test Filter</filter-name>")
                .append("       <filter-class>com.steeplesoft.jsf.facestester.test.TestFilter</filter-class>")
                .append("       <init-param>")
                .append("           <param-name>a.test.param</param-name>")
                .append("           <param-value>a.test.value</param-value>")
                .append("       </init-param>")
                .append("   </filter>")
                .append("   <filter-mapping>")
                .append("       <filter-name>Test Filter</filter-name>")
                .append("       <url-pattern>*.jsf</url-pattern>")
                .append("   </filter-mapping>")
                .append("   <filter-mapping>")
                .append("       <filter-name>Test Filter</filter-name>")
                // Test with no url-pattern
                .append("   </filter-mapping>")
                .append("</web-app>").toString();

        createTempFile(webXml);
        WebDeploymentDescriptor descriptor = parser.parse(new File("."));
        Assert.assertTrue(descriptor.getFilters().get("Test Filter").getFilter() instanceof TestFilter);
        Assert.assertEquals(descriptor.getFilterMappings().get("*.jsf"), "Test Filter");
   }

   protected void createTempFile(String contents) {
        try {
            File file = new File (fakeWebAppDir, "web.xml");
            file.createNewFile();
            FileOutputStream os = new FileOutputStream(file);
            os.write(contents.getBytes());
            os.close();
            file.deleteOnExit(); // just in case :)
        } catch (IOException ex) {
            throw new FacesTesterException("Unable to create temporary file", ex);
        }
   }
}
