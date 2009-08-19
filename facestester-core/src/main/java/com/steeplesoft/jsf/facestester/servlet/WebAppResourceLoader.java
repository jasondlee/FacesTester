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

import com.steeplesoft.jsf.facestester.servlet.impl.FileResource;
import com.steeplesoft.jsf.facestester.Resource;
import com.steeplesoft.jsf.facestester.ResourceLoader;

import java.io.File;
import java.net.URL;


public class WebAppResourceLoader implements ResourceLoader {
    private File searchPath;
    
    public WebAppResourceLoader(File webappDirectory) {
        this.searchPath = webappDirectory;
    }

    public ClassLoader getClassLoader() {
        return getClass().getClassLoader();
    }

    public Resource getResource(String location) {
    	if(this.searchPath == null) {
    		return null;
    	}
        if(location.startsWith("/")) {
            location = location.substring(1);
        }
        File file = new File(searchPath, location);
        if(!file.exists()) {
        	String path = searchPath.getPath();
        	if(!path.endsWith(File.separator)) {
        		path = path + "/";
        	}
	        URL url = getClassLoader().getResource(path + location);
	        if(url != null) {
	        	file = new File(url.getFile());
	        }
        }
        return new FileResource(file);
    }
}
