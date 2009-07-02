/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.servlet.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jasonlee
 */
public class FacesTesterHttpServletResponse implements HttpServletResponse {
    protected Map<String, Object> headers = new HashMap<String, Object>();
    private ByteArrayOutputStream baos;
    private PrintWriter writer;
    private int status;
    private String message;

    public String getContentAsString() throws UnsupportedEncodingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addCookie(Cookie arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean containsHeader(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String encodeURL(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String encodeRedirectURL(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String encodeUrl(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String encodeRedirectUrl(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendError(int status, String message) throws IOException {
        this.status = status;
        this.message = message;
    }

    public void sendError(int status) throws IOException {
        this.status = status;
    }

    public void sendRedirect(String arg0) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDateHeader(String arg0, long arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addDateHeader(String arg0, long arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setHeader(String arg0, String arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addHeader(String key, String value) {
        Object oldValue = headers.get(key);
        if (oldValue != null) {
            if (oldValue instanceof List) {
                ((List)oldValue).add(value);
            } else {
                List<Object> list = new ArrayList<Object>();
                list.add(oldValue);
                list.add(value);
                headers.put(key, list);
            }
        } else {
            headers.put(key, value);
        }
    }

    public void setIntHeader(String arg0, int arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addIntHeader(String arg0, int arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getCharacterEncoding() {
        return "UTF-8";
    }

    public String getContentType() {
        return "text/html";
    }

    public ServletOutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PrintWriter getWriter() throws IOException {
        if (writer == null) {
            baos = new ByteArrayOutputStream();
            writer = new PrintWriter(baos);
        }
        return writer;
    }

    public void setCharacterEncoding(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setContentLength(int arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setContentType(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setBufferSize(int arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getBufferSize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void flushBuffer() throws IOException {
        //
    }

    public void resetBuffer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isCommitted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setLocale(Locale arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Locale getLocale() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return message;
    }

}
