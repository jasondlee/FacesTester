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

import com.steeplesoft.jsf.facestester.test.TestFilter;
import com.steeplesoft.jsf.facestester.test.TestServletContextListener;
import com.steeplesoft.jsf.facestester.test.TestServletRequestListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class WhenProcessingRequests {
    protected FacesTester tester;
    
    @Before
    public void setUp() {
        tester = new FacesTester();
    }

    @Test
    public void shouldThrowExceptionForNonExistentView() {
        try {
            tester.requestPage("/foo.xhtml");
            fail("Excepted exception not thrown");
        } catch (FacesTesterException e) {
            assertThat(e.getMessage(), is("The page /foo.xhtml was not found."));
        }
    }

    /*
     * I'm not 100% sure this works correctly, but it's a start.
     */
    @Test
    public void shouldBeAbleToAccessQueryParameters() {
        FacesPage page = tester.requestPage("/queryTest.xhtml?foo=bar");
        Assert.assertNotNull(page);
        assertThat(page.getParameterValue("foo"), is("bar"));
    }

    @Test
    public void shouldHaveContextListenersExecuted() {
        tester.requestPage("/queryTest.xhtml");
        assertThat(TestServletContextListener.initializedCalled, is (true));
    }

    @Test
    public void shouldHaveRequestListenersExecuted() {
        tester.requestPage("/queryTest.xhtml");
        assertThat(TestServletRequestListener.initializedCalled, is (true));
        assertThat(TestServletRequestListener.destroyedCalled, is (true));
    }

    @Test
    public void shouldHaveFiltersCalled() {
        tester.requestPage("/queryTest.jsf");
        Assert.assertEquals(TestFilter.RUN_COUNT % 2, 0);
    }
}