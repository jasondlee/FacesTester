/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
