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
package com.steeplesoft.jsf.facestester.sample;

import java.io.File;

import javax.faces.FactoryFinder;

import com.steeplesoft.jsf.facestester.FacesForm;
import com.steeplesoft.jsf.facestester.FacesPage;
import com.steeplesoft.jsf.facestester.FacesTester;
import com.steeplesoft.jsf.facestester.servlet.WebDeploymentDescriptor;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class WhenSubmittingZipCodeForm {
    protected static FacesTester tester;

    @BeforeClass
    public static void beforeClass() {
		WebDeploymentDescriptor webDeploymentDescriptor = new WebDeploymentDescriptor(new File("sample-webapp-jsf1.2"));
        tester = new FacesTester(webDeploymentDescriptor);
    }

    @Test
    public void shouldDisplayCityForZipCode() {
        FacesPage page = tester.requestPage("/zipCodeMapper.xhtml");

        // Make sure the managed bean was loaded correctly
        assertThat(page.getComponentWithId("form:zipCode").getValueAsString(), is("12345"));

        FacesForm form = page.getFormById("form");
        form.setValue("zipCode", "95054");
        page = form.submit("submit");

        assertThat(page.getComponentWithId("form:city").getValueAsString(), is("Santa Clara"));
    }

    @Test
    public void shouldDisplayMessageIfZipCodeIsInvalid() {
        FacesPage page = tester.requestPage("/zipCodeMapper.xhtml");

        FacesForm form = page.getFormById("form");
        form.setValue("zipCode", "");
        page = form.submit("submit");

        assertThat(page.getMessageFor("form:zipCode"), is("Zip code is required"));
    }
    
}
