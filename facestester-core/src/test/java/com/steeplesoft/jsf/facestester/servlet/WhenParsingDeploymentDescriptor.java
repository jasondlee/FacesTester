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
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.List;

import javax.servlet.Filter;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.steeplesoft.jsf.facestester.FacesTester;
import com.steeplesoft.jsf.facestester.FacesTesterException;
import com.steeplesoft.jsf.facestester.Util;
import com.steeplesoft.jsf.facestester.servlet.impl.FilterChainImpl;
import com.steeplesoft.jsf.facestester.test.artifacts.TestFilter;
import com.steeplesoft.jsf.facestester.test.artifacts.TestServlet;
import com.steeplesoft.jsf.facestester.test.artifacts.TestServletContextListener;
import javax.servlet.Servlet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class WhenParsingDeploymentDescriptor {
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
        WebDeploymentDescriptor descriptor = new WebDeploymentDescriptor(new File("."));

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
        WebDeploymentDescriptor descriptor = new WebDeploymentDescriptor(new File("."));

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
        WebDeploymentDescriptor descriptor = new WebDeploymentDescriptor(new File("."));
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
        WebDeploymentDescriptor descriptor = new WebDeploymentDescriptor(new File("."));
        assertThat(descriptor.getContextParameters().get("javax.faces.DEFAULT_SUFFIX"), is((String)null));
    }

    @Test
    public void shouldLoadListenerInformation() {
        String webXml = new StringBuilder()
                .append("<web-app>")
                .append("   <listener>")
                .append("       <listener-class>")
                .append(TestServletContextListener.class.getName())
                .append("		</listener-class>")
                .append("   </listener>")
                .append("</web-app>").toString();

        createTempFile(webXml);
        WebDeploymentDescriptor descriptor = new WebDeploymentDescriptor(new File("."));
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
                .append("       <filter-class>")
                .append(TestFilter.class.getName())
                .append("       </filter-class>")
                .append("       <init-param>")
                .append("           <param-name>")
                .append("               ").append(TestFilter.TEST_PARAM_KEY)
                .append("           </param-name>")
                .append("           <param-value>")
                .append("               ").append(TestFilter.TEST_PARAM_VALUE)
                .append("           </param-value>")
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
        WebDeploymentDescriptor descriptor = new WebDeploymentDescriptor(new File("."));
        
        FilterWrapper filterWrapper = descriptor.getFilters().get("Test Filter");
        Assert.assertNotNull("Should create FilterWrapper", filterWrapper);
        Assert.assertEquals("FilterWrapper should contain initParam: " + TestFilter.TEST_PARAM_KEY,
                TestFilter.TEST_PARAM_VALUE, filterWrapper.getInitParam(TestFilter.TEST_PARAM_KEY));
        
        Filter filter = filterWrapper.getFilter();
        Assert.assertNotNull("Test Filter should be available", filter);
        Assert.assertEquals("Check type of Filter", filter.getClass(), TestFilter.class);

        filterWrapper.init(null);
        TestFilter testFilter = (TestFilter) filter;
        Assert.assertNull("Reading unavailable initParam", testFilter.getInitParam("notset"));
        Assert.assertEquals("Reading available initParam '"+TestFilter.TEST_PARAM_KEY+"'",
                TestFilter.TEST_PARAM_VALUE, testFilter.getInitParam(TestFilter.TEST_PARAM_KEY));
    }

    @Test
    public void shouldLoadServletInformation() {
        String webXml = new StringBuilder()
                .append("<web-app>")
                .append("   <servlet>")
                .append("       <servlet-name>Test Servlet</servlet-name>")
                .append("       <servlet-class>").append(TestServlet.class.getName())
                .append("       </servlet-class>")
                .append("       <init-param>")
                .append("           <param-name>").append(TestServlet.TEST_PARAM_KEY).append("</param-name>")
                .append("           <param-value>").append(TestServlet.TEST_PARAM_VALUE).append("</param-value>")
                .append("       </init-param>")
                .append("   </servlet>")
                .append("   <servlet-mapping>")
                .append("       <servlet-name>Test Servlet</servlet-name>")
                .append("       <url-pattern>*.jsf</url-pattern>")
                .append("   </servlet-mapping>")
                .append("   <servlet-mapping>")
                .append("       <servlet-name>Test Servlet</servlet-name>")
                // Test with no url-pattern
                .append("   </servlet-mapping>")
                .append("</web-app>").toString();

        createTempFile(webXml);
        WebDeploymentDescriptor descriptor = new WebDeploymentDescriptor(new File("."));

        ServletWrapper wrapper = descriptor.getServlets().get("Test Servlet");
        Assert.assertNotNull("Should create ServletWrapper", wrapper);
        Assert.assertEquals("ServletWrapper should contain initParam: " + TestServlet.TEST_PARAM_KEY, TestServlet.TEST_PARAM_VALUE, wrapper.getInitParam(TestServlet.TEST_PARAM_KEY));

        Servlet servlet = wrapper.getServlet();
        Assert.assertNotNull("Test Servlet should be available", servlet);
        Assert.assertEquals("Check type of Servlet", servlet.getClass(), TestServlet.class);

        wrapper.init(null);
        TestServlet testServlet = (TestServlet) servlet;
        Assert.assertEquals("Reading initParam '" + TestServlet.TEST_PARAM_KEY + "'", TestServlet.TEST_PARAM_VALUE, testServlet.getDummyParam());
    }

    @Test
    public void shouldLoadMultipleFilters() {
        String webXML = "testingTwoFiltersWithSameMapping-web.xml";
        // Test Filter3: /faces/*
        // Test Filter1: *.jsf
        // Test Filter2: *.xxx, Faces Servlet
        // Test Filter3: /faces/*, /feets/*
        Assert.assertEquals("Checking order for '/test.jsf'",
                "Test Filter1, Test Filter2",
                getOrderedNames(this.createFilterChain(webXML, "/test.jsf")));
        Assert.assertEquals("Checking order for '/faces/test.jsf'",
                "Test Filter3, Test Filter1, Test Filter2",
                getOrderedNames(this.createFilterChain(webXML, "/faces/test.jsf")));
        Assert.assertEquals("Checking order for '/feets/some.xxx'",
                "Test Filter2, Test Filter3",
                getOrderedNames(this.createFilterChain(webXML, "/feets/some.xxx")));
        Assert.assertEquals("Checking order for '/any'",
                "Test Filter2",
                getOrderedNames(this.createFilterChain(webXML, "/any")));
        Assert.assertEquals("Checking order for '/faces/any'",
                "Test Filter3, Test Filter2",
                getOrderedNames(this.createFilterChain(webXML, "/faces/any")));

    }

    private String getOrderedNames(List<Filter> filters) {
        StringBuilder sb = new StringBuilder();
        for(Filter filter : filters) {
            if(sb.length()>0) {
                sb.append(", ");
            }
            if(filter instanceof TestFilter) {
                sb.append(((TestFilter)filter).getName());
            } else {
                sb.append("<UNKNOWN FILTER>");
            }
        }
        return sb.toString();
    }

    private List<Filter> createFilterChain(String resourcePath, String uri) {
        createTempFileFromCP(resourcePath);
        final WebDeploymentDescriptor wdDescriptor = new WebDeploymentDescriptor(new File("."));
        ServletContextFactory.createServletContext(wdDescriptor); // initialize the ServletContext
        FacesTesterForFilterTests tester = new FacesTesterForFilterTests(wdDescriptor);
        FilterChainImpl fChain = tester.createAppropriateFilterChain(uri);
        tester.cleanup();
        List<Filter> rval = new ArrayList<Filter>(3);
        do {
            Filter f = fChain.getFilter();
            if(f != null) {
                rval.add(f);
            }
        } while(null != (fChain = (FilterChainImpl) fChain.getNextFilterChain()));
        return rval;
    }

    @Test
    public void shouldLoadMimeTypeInformation() {
        String webXml = new StringBuilder()
             .append("<web-app>")
             .append("    <mime-mapping>")
             .append("        <extension>foo</extension>")
             .append("        <mime-type>application/x-foo</mime-type>")
             .append("    </mime-mapping>")
             .append("</web-app>").toString();
       createTempFile(webXml);
       WebDeploymentDescriptor descriptor = new WebDeploymentDescriptor(new File("."));
       Assert.assertTrue(descriptor.getMimeTypeMappings().containsKey("foo"));
       Assert.assertTrue("application/x-foo".equals(descriptor.getMimeTypeMappings().get("foo")));

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

    private final String resourcePackagePath = "/" +
            this.getClass().getPackage().getName().replace(".", "/") + "/resources/";

    /**
     * creates the temp web.xml copying the specified resource from the
     * subpackage 'resources'
     * @param resourceName the resource to copy to a temporary web.xml
     */
    protected void createTempFileFromCP(String resourceName) {
        InputStream resourceStream = null;
        try {
            String path = this.resourcePackagePath + resourceName;
            resourceStream = this.getClass().getResourceAsStream(path);
            File file = new File(fakeWebAppDir, "web.xml");
            file.createNewFile();
            Util.copy(resourceStream, file);
            file.deleteOnExit();
        } catch (IOException ex) {
            throw new FacesTesterException("Unable to create temporary file", ex);
        } finally {
            Util.close(resourceStream);
        }
    }

    private static class FacesTesterForFilterTests extends FacesTester {

        public FacesTesterForFilterTests(WebDeploymentDescriptor wdDescriptor) {
            super(wdDescriptor);
        }

        public FilterChainImpl createAppropriateFilterChain(String uri) {
            return (FilterChainImpl) super.createAppropriateFilterChain(uri, null);
        }

        public void cleanup() {
//            setDescriptor(null);
        }
    }


}
