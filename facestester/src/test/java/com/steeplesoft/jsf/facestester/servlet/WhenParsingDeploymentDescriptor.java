package com.steeplesoft.jsf.facestester.servlet;

import com.steeplesoft.jsf.facestester.FacesTesterException;
import com.steeplesoft.jsf.facestester.test.TestFilter;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        assertThat(descriptor.getListeners().size(), is(1));
   }

    @Test
    public void shouldLoadFilterInformation() {
        String webXml = new StringBuilder()
                .append("<web-app>")
                .append("   <filter>")
                .append("       <filter-name>Test Filter</filter-name>")
                .append("       <filter-class>com.steeplesoft.jsf.facestester.test.TestFilter</filter-class>")
                .append("   </filter>")
                .append("   <filter-mapping>")
                .append("       <filter-name>Test Filter</filter-name>")
                .append("       <url-pattern>*.jsf</url-pattern>")
                .append("   </filter-mapping>")
                .append("</web-app>").toString();

        createTempFile(webXml);
        WebDeploymentDescriptor descriptor = parser.parse(new File("."));
        Assert.assertTrue(descriptor.getFilters().get("Test Filter") instanceof TestFilter);
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
