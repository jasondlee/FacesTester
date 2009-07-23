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

import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jasonlee
 */
public class WhenPerformingInjection {
    protected static FacesTester ft = new FacesTester();
    protected InjectionTest it;

    @BeforeClass
    public static void setup() {
        ft = new FacesTester();
    }

    @Before
    public void setUp() {
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
