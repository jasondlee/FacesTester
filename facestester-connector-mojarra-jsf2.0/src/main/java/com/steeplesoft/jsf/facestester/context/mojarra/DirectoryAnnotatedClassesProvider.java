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

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.component.FacesComponent;
import javax.faces.component.behavior.FacesBehavior;
import javax.faces.convert.FacesConverter;
import javax.faces.event.NamedEvent;
import javax.faces.render.FacesBehaviorRenderer;
import javax.faces.render.FacesRenderer;
import javax.faces.validator.FacesValidator;

public abstract class DirectoryAnnotatedClassesProvider implements AnnotatedClassesProvider {
	
    private static Set<Class<? extends Annotation>> annotations = new HashSet<Class<? extends Annotation>>();

    public DirectoryAnnotatedClassesProvider() {
        Collections.addAll(annotations,
                FacesComponent.class,
                FacesConverter.class,
                FacesValidator.class,
                FacesRenderer.class,
                ManagedBean.class,
                NamedEvent.class,
                FacesBehavior.class,
                FacesBehaviorRenderer.class);
	}
	
    protected void processBuildDirectories(File sourceDir, File startingDir, Map<Class<? extends Annotation>, Set<Class<?>>> classList) {
        assert (startingDir.exists());
        assert (startingDir.isDirectory());

        File[] files = startingDir.listFiles();
        if(files == null) {
        	return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                processBuildDirectories(sourceDir, file, classList);
            } else {
                if (file.getName().endsWith(".class")) {
                    int sourceDirPathLength = sourceDir.getPath().length();
                    if(!sourceDir.getPath().endsWith("/")) {
                    	sourceDirPathLength++;
                    }
					String classFile = file.getPath().substring(sourceDirPathLength);
                    classFile = classFile.substring(0, classFile.length() - 6).replace(File.separator, ".");

                    try {
                        Class clazz = Class.forName(classFile);
                        for (Class annotation : annotations) {
                            if (clazz.isAnnotationPresent(annotation)) {
                                Set<Class<?>> classes = classList.get(annotation);
                                if (classes == null) {
                                    classes = new HashSet<Class<?>>();
                                    classList.put(annotation, classes);
                                }
                                classes.add(clazz);
                            }
                        }
                    } catch (ClassNotFoundException ex) {
                        //
                    }
                }
            }
        }
    }

	public Map<Class<? extends Annotation>, Set<Class<?>>> getAnnotatedClasses() {
    	Map<Class<? extends Annotation>, Set<Class<?>>> annotatedClasses = new HashMap<Class<? extends Annotation>, Set<Class<?>>>();

    	for (File sourceDir : getSources()) {
    		processBuildDirectories(sourceDir, sourceDir, annotatedClasses);
			
		}

        return annotatedClasses;
	}

	protected abstract Collection<File> getSources();

}
