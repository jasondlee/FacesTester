/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

/**
 *
 * @author jasonlee
 */
class FacesTestResponse implements ServletResponse {

    public String getCharacterEncoding() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getContentType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ServletOutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PrintWriter getWriter() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
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

}
