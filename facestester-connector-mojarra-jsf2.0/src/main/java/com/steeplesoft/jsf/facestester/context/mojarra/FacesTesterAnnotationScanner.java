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
package com.steeplesoft.jsf.facestester.context.mojarra;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import com.steeplesoft.jsf.facestester.util.ServiceProvider;
import com.sun.faces.spi.AnnotationProvider;

/**
 *
 * @author jasonlee
 */
public class FacesTesterAnnotationScanner extends AnnotationProvider {
    protected AnnotationProvider parentProvider;

    public FacesTesterAnnotationScanner(ServletContext sc, AnnotationProvider parent) {
        super(sc);
        this.parentProvider = parent;
    }

    @Override
    public Map<Class<? extends Annotation>, Set<Class<?>>> getAnnotatedClasses(Set<URL> urls) {
    	
    	Map<Class<? extends Annotation>, Set<Class<?>>> annotatedClasses = new HashMap<Class<? extends Annotation>, Set<Class<?>>>();
    	
    	Collection<AnnotatedClassesProvider> allProviders = ServiceProvider.getAllProviders(AnnotatedClassesProvider.class);
    	for(AnnotatedClassesProvider annotatedClassesProvider : allProviders) {
    		annotatedClasses.putAll(annotatedClassesProvider.getAnnotatedClasses());
    	}
    	
        Map<Class<? extends Annotation>, Set<Class<?>>> parentsClasses = this.parentProvider.getAnnotatedClasses(urls);
        if (parentsClasses != null) {
            annotatedClasses.putAll(parentsClasses);
        }

        return annotatedClasses;
    }
}
