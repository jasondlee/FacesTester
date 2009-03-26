/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester;

import javax.faces.component.UIOutput;

/**
 *
 * @author jasonlee
 */
public class TestComponent extends UIOutput {
    public static final String COMPONENT_FAMILY = "com.steeplesoft.jsf.facestester.TestComponent";
    public static final String COMPONENT_TYPE = COMPONENT_FAMILY;
    public static final String RENDERER_TYPE = COMPONENT_FAMILY;

    public TestComponent() {
        super();
    }

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

}
