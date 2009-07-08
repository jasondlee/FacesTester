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
package com.steeplesoft.jsf.facestester.test;

import java.util.Date;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

/**
 *
 * @author jasonlee
 */
public class InvalidStateSavingComponent extends UIOutput {
    public static final String COMPONENT_FAMILY = "com.steeplesoft.jsf.facestester.InvalidStateSavingComponent";
    public static final String COMPONENT_TYPE = COMPONENT_FAMILY;
    public static final String RENDERER_TYPE = COMPONENT_FAMILY;

    private Object[] _state;

    protected String property1;
    protected Date property2;
    protected Boolean property3;

    public InvalidStateSavingComponent() {
        super();
    }

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public Date getProperty2() {
        return property2;
    }

    public void setProperty2(Date property2) {
        this.property2 = property2;
    }

    public Boolean getProperty3() {
        return property3;
    }

    public void setProperty3(Boolean property3) {
        this.property3 = property3;
    }

    @Override
    public void restoreState(FacesContext _context, Object _state) {
        this._state = (Object[]) _state;
        super.restoreState(_context, this._state[0]);

        property1 = (String) this._state[1];
        property2 = (Date) this._state[2];
    }

    @Override
    public Object saveState(FacesContext _context) {
        if (_state == null) {
            _state = new Object[3];
        }
        _state[0] = super.saveState(_context);
        _state[1] = property1;
        _state[2] = property2;

        return _state;
    }
}
