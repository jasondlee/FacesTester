/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.jsf.facestester.metadata;


/**
 *
 * @author jasonlee
 */
public class RendererMetaData {
    protected String componentFamily;
    protected String rendererClass;
    protected String rendererType;

    public String getComponentFamily() {
        return componentFamily;
    }

    public String getRendererClass() {
        return rendererClass;
    }

    public String getRendererType() {
        return rendererType;
    }

    public void setComponentFamily(String componentFamily) {
        this.componentFamily = componentFamily;
    }

    public void setRendererClass(String rendererClass) {
        this.rendererClass = rendererClass;
    }

    public void setRendererType(String rendererType) {
        this.rendererType = rendererType;
    }
}
