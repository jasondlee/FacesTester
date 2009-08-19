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
package com.steeplesoft.jsf.facestester;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;

public class FacesComponent {

    protected UIComponent component;

    public FacesComponent(UIComponent component) {
        this.component = component;
    }

    /**
     * This method dumps a component tree to standard out, a useful tool for
     * making sure the component tree looks like you think it should.
     */
    public void dumpComponentTree() {
        System.out.println("/ " + buildComponentString(component));

        for (UIComponent child : component.getChildren()) {
            dumpChildren(child, "    ");
        }
    }

    /**
     * Returns the component identified by the specified id if one can be found as a child of the component
     * tree for the page
     *
     * @param id component to return
     * @return specified component
     * @throws AssertionError if component cannot be found with the specified id
     */
    public FacesComponent getComponentWithId(String id) {
        UIComponent desiredComponent = this.component.findComponent(id);

        if (desiredComponent == null) {
            throw new AssertionError("UIComponent '" + id +
                    "' does not exist on page.");
        }

        return new FacesComponent(desiredComponent);
    }

    public String getValueAsString() {
        if (component instanceof ValueHolder) {
            Object value = ((ValueHolder) component).getValue();

            return (value == null) ? null : value.toString();
        }

        throw new AssertionError("UIComponent '" + component.getId() +
                "' does not hold values.");
    }

    public UIComponent getWrappedComponent() {
        return component;
    }

    public boolean isRendered() {
        return component.isRendered();
    }

    protected String buildComponentString(UIComponent component) {
        return component.getId() +
                ((component instanceof ValueHolder)
                ? (":  " + ((ValueHolder) component).getValue()) : "");
    }

    protected List<String> collectChildrenOfType(Class<?> type) {
        List<String> elements = new ArrayList<String>();

        for (UIComponent each : component.getChildren()) {
            if (type.isAssignableFrom(each.getClass())) {
                elements.add(each.getId());
            }
        }

        return elements;
    }

    protected void dumpChildren(UIComponent component, String prefix) {
        System.out.println(prefix + "|----" + buildComponentString(component));

        for (UIComponent child : component.getChildren()) {
            dumpChildren(child, prefix + "     ");
        }
    }
}
