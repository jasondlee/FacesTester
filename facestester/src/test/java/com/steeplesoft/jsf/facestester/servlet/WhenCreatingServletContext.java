package com.steeplesoft.jsf.facestester.servlet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class WhenCreatingServletContext {
    private ServletContextFactory contextFactory;

    @Before
    public void setUp() {
        contextFactory = new ServletContextFactory();
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

        ServletContext context = contextFactory.createContextFromDescriptor(
                new ByteArrayInputStream(webXml.getBytes()));

        assertThat(context.getInitParameter("javax.faces.DEFAULT_SUFFIX"), is(".xhtml"));
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

        ServletContext context = contextFactory.createContextFromDescriptor(
                new ByteArrayInputStream(webXml.getBytes()));

        assertThat(context.getInitParameter("javax.faces.DEFAULT_SUFFIX"), is(".jsp"));
    }
}
