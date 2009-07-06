/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.jsf.facestester.context.mojarra;

import com.steeplesoft.jsf.facestester.Util;
import com.sun.faces.spi.AnnotationProvider;
import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.faces.component.behavior.FacesBehavior;
import javax.faces.convert.FacesConverter;
import javax.faces.bean.ManagedBean;
import javax.faces.component.FacesComponent;
import javax.faces.event.NamedEvent;
import javax.faces.render.FacesBehaviorRenderer;
import javax.faces.render.FacesRenderer;
import javax.faces.validator.FacesValidator;
import javax.servlet.ServletContext;

/**
 *
 * @author jasonlee
 */
public class FacesTesterAnnotationScanner extends AnnotationProvider {

    protected static Set<Class<? extends Annotation>> annotations = new HashSet<Class<? extends Annotation>>();
    protected AnnotationProvider parentProvider;

    public FacesTesterAnnotationScanner(ServletContext sc, AnnotationProvider parent) {
        super(sc);
        this.parentProvider = parent;
        Util.getLogger().info("FacesTesterAnnotationScanner installed.");

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

    @Override
    public Map<Class<? extends Annotation>, Set<Class<?>>> getAnnotatedClasses() {
        Map<Class<? extends Annotation>, Set<Class<?>>> annotatedClasses = new HashMap<Class<? extends Annotation>, Set<Class<?>>>();

        // TODO: This needs to be configurable.  Once I get this working...
        processBuildDirectories(new File("target/classes"), annotatedClasses);


        Map<Class<? extends Annotation>, Set<Class<?>>> parentsClasses = parentProvider.getAnnotatedClasses();
        if (parentsClasses != null) {
            annotatedClasses.putAll(parentsClasses);
        }

        return annotatedClasses;
    }

    protected void processBuildDirectories(File startingDir, Map<Class<? extends Annotation>, Set<Class<?>>> classList) {
        assert (startingDir.exists());
        assert (startingDir.isDirectory());

        for (File file : startingDir.listFiles()) {
            if (file.isDirectory()) {
                processBuildDirectories(file, classList);
            } else {
                if (file.getName().endsWith(".class")) {
                    String classFile = file.getPath().substring("test/classes/".length() + 2);
                    classFile = classFile.substring(0, classFile.length() - 6).replace("/", ".");

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
}
