package com.steeplesoft.jsf.facestester.servlet;

import com.steeplesoft.jsf.facestester.FacesTesterException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import static org.xml.sax.helpers.XMLReaderFactory.createXMLReader;

import java.io.IOException;
import java.io.InputStream;


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
            throw new FacesTesterException("Unable to parse web deployment descriptor",
                e);
        } catch (IOException e) {
            throw new FacesTesterException("Unable to parse web deployment descriptor",
                e);
        }
    }
}
