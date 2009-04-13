/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester;

import com.steeplesoft.jsf.facestester.test.InvalidStateSavingComponent;
import com.steeplesoft.jsf.facestester.test.TestComponent;
import org.junit.Test;

/**
 *
 * @author jasonlee
 */
public class WhenTestingStateSaving {
    @Test
    public void shouldValidateCorrectStateSaving() {
        FacesTester facesTester = new FacesTester();
        facesTester.testStateSaving(TestComponent.COMPONENT_TYPE);
    }

    @Test(expected=AssertionError.class)
    public void shouldCatchInvalidStateSaving() {
        FacesTester facesTester = new FacesTester();
        facesTester.testStateSaving(InvalidStateSavingComponent.COMPONENT_TYPE);
    }
}