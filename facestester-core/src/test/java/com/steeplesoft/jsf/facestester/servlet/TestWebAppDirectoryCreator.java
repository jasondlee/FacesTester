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
package com.steeplesoft.jsf.facestester.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.steeplesoft.jsf.facestester.Util;

public class TestWebAppDirectoryCreator {

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    public File createTestWebAppWithDescriptor(InputStream descriptor) throws IOException {
        File webAppDirectory = new File(System.getProperty("java.io.tmpdir"), "facestester-webapp");
        if (webAppDirectory.exists()) {
            webAppDirectory.delete();
        }
        webAppDirectory.mkdir();

        createTestWebXml(webAppDirectory, descriptor);

        return webAppDirectory;
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    private File createTestWebXml(File webAppDirectory, InputStream descriptor) throws IOException {
        File webInfDirectory = new File(webAppDirectory, "WEB-INF");
        webInfDirectory.mkdir();
        webInfDirectory.deleteOnExit();

        File webXml = new File(webInfDirectory, "web.xml");
        webXml.createNewFile();
        webXml.deleteOnExit();
        Util.copy(descriptor, webXml);

        return webXml;
    }
}
