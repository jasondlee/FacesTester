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

import javax.faces.context.FacesContext;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

public class WhenBuildingFacesTester {
    static FacesTester facesTester;

    @BeforeClass
    public static void setup() {
        facesTester = new FacesTester();
    }

    @Test
    public void shouldReturnSameFacesContextInstance() {
        FacesContext context1 = facesTester.getFacesContext();
        FacesContext context2 = facesTester.getFacesContext();
        assertThat(context1, is(context2));
    }

    @Test
    public void shouldReturnValidFacesContext() {
        assertThat(facesTester.getFacesContext(), is (not(nullValue())));
    }

    @Test
    public void shouldExposeServletContext() {
        assertThat(facesTester.getServletContext(), is(not(nullValue())));
    }

    @Test
    public void shouldReleaseFacesContext() {
        FacesContext context1 = facesTester.getFacesContext();
        facesTester = new FacesTester();
        FacesContext context2 = facesTester.getFacesContext();
        Assert.assertNotSame(context1, context2);
    }
}