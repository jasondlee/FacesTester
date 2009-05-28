package com.steeplesoft.jsf.facestester.servlet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class WhenParsingDeploymentDescriptor {
    private WebDeploymentDescriptorParser parser = new WebDeploymentDescriptorParser();

    @Test
    public void shouldSetDefaultSuffixContextParameterToXhtml() throws SAXException, IOException {
        String webXml = new StringBuilder()
                .append("<web-app>")
                .append("   <context-param>")
                .append("       <param-name>javax.faces.DEFAULT_SUFFIX</param-name>")
                .append("       <param-value>.xhtml</param-value>")
                .append("   </context-param>")
                .append("</web-app>").toString();

        WebDeploymentDescriptor descriptor = parser.parse(new ByteArrayInputStream(webXml.getBytes()));

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

        WebDeploymentDescriptor descriptor = parser.parse(new ByteArrayInputStream(webXml.getBytes()));

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

        WebDeploymentDescriptor descriptor = parser.parse(new ByteArrayInputStream(webXml.getBytes()));
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

        WebDeploymentDescriptor descriptor = parser.parse(new ByteArrayInputStream(webXml.getBytes()));
        assertThat(descriptor.getContextParameters().get("javax.faces.DEFAULT_SUFFIX"), is((String)null));
    }

    //@Test
    public void shouldLoadListenerInformation() {
        String webXml = new StringBuilder()
                .append("<web-app>")
                .append("   <listener>")
                .append("       <listener-class>com.steeplesoft.jsf.facestester.test.TestListener</listener-class>")
                .append("   </listener>")
                .append("</web-app>").toString();
        WebDeploymentDescriptor descriptor = parser.parse(new ByteArrayInputStream(webXml.getBytes()));
        assertThat(descriptor.getListeners().size(), is(1));
   }
}
