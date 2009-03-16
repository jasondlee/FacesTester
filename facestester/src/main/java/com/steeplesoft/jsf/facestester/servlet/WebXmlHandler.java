package com.steeplesoft.jsf.facestester.servlet;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.Map;
import java.util.HashMap;

public class WebXmlHandler extends DefaultHandler {
    private String qname;
    private String paramName;
    private String paramValue;
    private Map<String, String> parameters = new HashMap<String, String>();

    @Override
    public void startElement(String uri, String name, String qname, Attributes attributes) throws SAXException {
        this.qname = qname;
    }

    @Override
    public void endElement(String uri, String name, String qname) throws SAXException {
        if ("context-param".equalsIgnoreCase(qname)) {
            parameters.put(paramName, paramValue);
        }
        this.qname = null;
    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
        if ("param-name".equals(qname)) {
            paramName = new String(chars, start, length);
        }
        if ("param-value".equals(qname)) {
            paramValue = new String(chars, start, length);
        }
    }

    public Map<String, String> listContextParameters() {
        return parameters;
    }
}
