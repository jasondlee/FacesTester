/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.jsf.facestester.metadata;


/**
 *
 * @author jasonlee
 */
public class ManagedBeanMetaData {
    protected String beanClass;
    protected String beanName;
    protected String beanScope;

    public String getBeanClass() {
        return beanClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public String getBeanScope() {
        return beanScope;
    }

    public void setBeanClass(String beanClassName) {
        this.beanClass = beanClassName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setBeanScope(String beanScope) {
        this.beanScope = beanScope;
    }
}
