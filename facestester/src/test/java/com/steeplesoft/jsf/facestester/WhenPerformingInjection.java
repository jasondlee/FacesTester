/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester;

import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jasonlee
 */
public class WhenPerformingInjection {
    protected FacesTester ft = new FacesTester();
    protected InjectionTest it = new InjectionTest();

    @Before
    public void setUp() {
        ft = new FacesTester();
        it = new InjectionTest();
    }

    @Test
    public void shouldBeAbleToUsePublicSetter() {
        ft.inject(it, "test", "Test Value");
        Assert.assertEquals(it.getTest(), "Test Value");
    }

    @Test
    public void shouldBeAbleToUseFieldInjection() {
        ft.inject(it, "fieldTest", "Test Value");
        Assert.assertEquals(it.getFieldTest(), "Test Value");
    }

    @Test(expected=FacesTesterException.class)
    public void shouldFailForInvalidArgument() {
        ft.inject(it, "fieldTest", new Date());
        Assert.fail("Invalid argument types should fail.");
    }

    @Test(expected=FacesTesterException.class)
    public void shouldFailForNonexistentTarget() {
        ft.inject(it, "badField", new Date());
        Assert.fail("Invalid property names should fail.");
    }
}


class InjectionTest {
    private String test;
    private String fieldTest;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getFieldTest() {
        return fieldTest;
    }

}
