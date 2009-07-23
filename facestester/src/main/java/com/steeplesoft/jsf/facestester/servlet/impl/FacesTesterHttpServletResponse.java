/*
 * Copyright (c) 2009, Jason Lee <jason@steeplesoft.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the <ORGANIZATION> nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.steeplesoft.jsf.facestester.servlet.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
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
    private int status = HttpServletResponse.SC_OK;
    private String message;
    private String contentType = "text/html";
    private Locale locale = Locale.ENGLISH;
    private String characterEncoding = "iso-8859-1";
    private int contentLength = -1;


    public String getContentAsString() throws UnsupportedEncodingException {
        return this.baos.toString();
    }

    public void addCookie(Cookie arg0) {
        // TODO where to "persist" cookies
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean containsHeader(String name) {
        return this.headers.containsKey(name);
    }

    public String encodeURL(String url) {
        return url;
    }

    public String encodeRedirectURL(String url) {
        return encodeURL(url);
    }

    public String encodeUrl(String url) {
        return encodeURL(url);
    }

    public String encodeRedirectUrl(String url) {
        return encodeRedirectURL(url);
    }

    public void sendError(int status, String message) throws IOException {
        this.setStatus(status, message);
    }

    public void sendError(int status) throws IOException {
        this.setStatus(status);
    }

    public void sendRedirect(String arg0) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDateHeader(String name, long value) {
        this.setHeader(name, new Date(value).toGMTString());
    }

    public void addDateHeader(String name, long value) {
        this.addHeader(name, new Date(value).toGMTString());
    }

    public void setHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public void addHeader(String key, String value) {
        Object oldValue = headers.get(key);
        if (oldValue != null) {
            if (oldValue instanceof List) {
                ((List)oldValue).add(value);
            } else {
                List<String> list = new ArrayList<String>();
                list.add((String) oldValue);
                list.add(value);
                headers.put(key, list);
            }
        } else {
            headers.put(key, value);
        }
    }

    public void setIntHeader(String name, int value) {
        this.setHeader(name, String.valueOf(value));
    }

    public void addIntHeader(String name, int value) {
        this.addHeader(name, String.valueOf(value));
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    public String getContentType() {
        return contentType;
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

    public void setCharacterEncoding(String enc) {
        this.characterEncoding = enc;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setBufferSize(int arg0) {
        //
    }

    public int getBufferSize() {
        return 1;
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

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public int getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return message;
    }


    // ---------- Mock accessors ---------- //
    public Map<String, Object> getHeaderMap() {
        return headers;
    }

    public int getContentLength() {
        return contentLength!=-1 ? contentLength : this.baos.size();
    }



}