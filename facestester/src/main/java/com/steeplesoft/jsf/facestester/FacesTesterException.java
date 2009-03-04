package com.steeplesoft.jsf.facestester;

import org.xml.sax.SAXException;

public class FacesTesterException extends RuntimeException{
    public FacesTesterException(String message, Exception cause) {
        super(message, cause);
    }
}
