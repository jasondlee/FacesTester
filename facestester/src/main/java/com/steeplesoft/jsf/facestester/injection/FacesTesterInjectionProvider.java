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
package com.steeplesoft.jsf.facestester.injection;

import com.steeplesoft.jsf.facestester.FacesTesterException;
import com.steeplesoft.jsf.facestester.Util;
import com.sun.faces.spi.DiscoverableInjectionProvider;
import com.sun.faces.spi.InjectionProviderException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.annotation.PreDestroy;
import javax.annotation.PostConstruct;

/**
 *
 * @author jasonlee
 */
public class FacesTesterInjectionProvider extends DiscoverableInjectionProvider {

    public void inject(Object managedBean) throws InjectionProviderException {
        processMethods(managedBean);
        processFields(managedBean);
    }

    public void invokePreDestroy(Object managedBean) throws InjectionProviderException {
        Method method = getAnnotatedMethod(managedBean, PreDestroy.class);
        if (method != null) {
            boolean accessible = method.isAccessible();
            method.setAccessible(true);
            try {
                method.invoke(managedBean);
            } catch (Exception ex) {
                throw new FacesTesterException("An error occured while executing the @PreConstruct method " +
                        method.getName() + ": " + ex.getLocalizedMessage(), ex);
            } finally {
                method.setAccessible(accessible);
            }
        }
    }

    public void invokePostConstruct(Object managedBean) throws InjectionProviderException {
        Method method = getAnnotatedMethod(managedBean, PostConstruct.class);
        if (method != null) {
            boolean accessible = method.isAccessible();
            method.setAccessible(true);
            try {
                method.invoke(managedBean);
            } catch (Exception ex) {
                throw new FacesTesterException("An error occured while executing the @PostConstruct method " +
                        method.getName() + ": " + ex.getLocalizedMessage(), ex);
            } finally {
                method.setAccessible(accessible);
            }
        }
    }

    private Method getAnnotatedMethod(Object obj, Class<? extends Annotation> annotation) {
        Class clazz = obj.getClass();
        while (!clazz.equals(Object.class)) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    if (method.getParameterTypes().length > 0) {
                        throw new FacesTesterException("@" + annotation.getName() + " methods can not take parameters:  " + clazz.getName() + "." +method.getName());
                    }
                    if (!method.getReturnType().equals(Void.TYPE)) {
                        throw new FacesTesterException("@" + annotation.getName() + " methods must return void:  " + clazz.getName() + "." +method.getName());
                    }
                    if (Modifier.isStatic(method.getModifiers())) {
                        throw new FacesTesterException("@" + annotation.getName() + " methods must not be static:  " + clazz.getName() + "." +method.getName());
                    }

                    return method;
                }

            }
            clazz = clazz.getSuperclass();
        }


        return null;
    }

    private void processMethods(Object managedBean) throws InjectionProviderException {
        Class<?> clazz = managedBean.getClass();
        while (!Object.class.equals(clazz)) {

            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(PersistenceUnit.class)) {
                    inject(method, getEntityManagerFactory(method.getAnnotation(PersistenceUnit.class)), managedBean);
                }
                if (method.isAnnotationPresent(Resource.class)) {
                    inject(method, null, managedBean);
                }
                if (method.isAnnotationPresent(PersistenceContext.class)) {
                    inject(method, getEntityManager(method.getAnnotation(PersistenceContext.class)), managedBean);
                }
            }
            clazz = clazz.getSuperclass();
        }

    }

    private void processFields(Object managedBean) throws InjectionProviderException {
        Class<?> clazz = managedBean.getClass();
        while (!Object.class.equals(clazz)) {

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(PersistenceUnit.class)) {
                    inject(field, getEntityManagerFactory(field.getAnnotation(PersistenceUnit.class)), managedBean);
                }
                if (field.isAnnotationPresent(Resource.class)) {
                    inject(field, null, managedBean);
                }
                if (field.isAnnotationPresent(PersistenceContext.class)) {
                    inject(field, getEntityManager(field.getAnnotation(PersistenceContext.class)), managedBean);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    private void inject(final Object target, final Object value, final Object managedBean) throws InjectionProviderException {
        if (target instanceof Method) {
            invokeAnnotatedMethod((Method) target, managedBean, value);
        } else {
            try {
                final Field f = (Field) target;
                if (System.getSecurityManager() != null) {
                    java.security.AccessController.doPrivileged(
                            new java.security.PrivilegedExceptionAction() {
                                public java.lang.Object run() throws Exception {
                                    f.set(managedBean, value);
                                    return null;
                                }
                            });
                } else {
                    boolean accessible = f.isAccessible();
                    f.setAccessible(true);
                    try {
                        f.set(managedBean, value);
                    } catch (Exception e) {
                        throw new InjectionProviderException(e.getMessage(), e);
                    } finally {
                        f.setAccessible(accessible);
                    }
                }
            } catch (Throwable t) {
                String msg = "Exception attempting to inject " + value + " into " + target.getClass();
                throw new InjectionProviderException(msg, (t instanceof InvocationTargetException) ? ((InvocationTargetException) t).getCause() : t);
            }

        }
    }

    private static void invokeAnnotatedMethod(Method method, Object managedBean, Object value)
            throws InjectionProviderException {

        if (method != null) {
            boolean accessible = method.isAccessible();
            method.setAccessible(true);
            try {
                method.invoke(managedBean, value);
            } catch (Exception e) {
                throw new InjectionProviderException(e.getMessage(), e);
            } finally {
                method.setAccessible(accessible);
            }
        }

    }

    private EntityManager getEntityManager(PersistenceContext annotation) {
        String unitName = annotation.unitName();
        return (EntityManager) InjectionManager.getRegisteredObject(unitName);
    }

    private EntityManagerFactory getEntityManagerFactory(PersistenceUnit annotation) {
        String unitName = annotation.unitName();
        return (EntityManagerFactory) InjectionManager.getRegisteredObject(unitName);
    }

}
