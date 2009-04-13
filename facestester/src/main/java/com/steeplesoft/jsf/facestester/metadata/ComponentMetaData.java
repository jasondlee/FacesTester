/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.jsf.facestester.metadata;


/**
 *
 * @author jasonlee
 */
public class ComponentMetaData {
    private String componentClass;
    private String componentType;
    private String displayName;

    public String getComponentClass() {
        return componentClass;
    }

    public String getComponentType() {
        return componentType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setComponentClass(String componentClass) {
        this.componentClass = componentClass;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
