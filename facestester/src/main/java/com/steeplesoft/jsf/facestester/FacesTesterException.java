package com.steeplesoft.jsf.facestester;

public class FacesTesterException extends RuntimeException {
    public FacesTesterException(String message, Exception cause) {
        super(message, cause);
    }

    public FacesTesterException(String message) {
        super(message);
    }

    public FacesTesterException(Exception cause) {
        super(cause);
    }
}
