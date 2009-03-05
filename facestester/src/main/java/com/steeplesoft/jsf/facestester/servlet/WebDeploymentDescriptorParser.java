package com.steeplesoft.jsf.facestester.servlet;

import org.xml.sax.XMLReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import static org.xml.sax.helpers.XMLReaderFactory.createXMLReader;

import java.io.InputStream;
import java.io.IOException;

import com.steeplesoft.jsf.facestester.FacesTesterException;

public class WebDeploymentDescriptorParser {
    public WebDeploymentDescriptor parse(InputStream webXmlStream) {
        try {
            WebXmlHandler handler = new WebXmlHandler();

            XMLReader reader = createXMLReader();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(webXmlStream));

            WebDeploymentDescriptor descriptor = new WebDeploymentDescriptor();
            descriptor.setContextParameters(handler.listContextParameters());

            return descriptor;
        } catch (SAXException e) {
            throw new FacesTesterException("Unable to parse web deployment descriptor", e);
        } catch (IOException e) {
            throw new FacesTesterException("Unable to parse web deployment descriptor", e);
        }
    }
}
