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
package com.steeplesoft.jsf.facestester.jsf2.test;

import com.steeplesoft.facestester.jsf2.MyManagedBean;
import com.steeplesoft.jsf.facestester.FacesTester;


import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.junit.Test;
import org.junit.Before;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * @author jasonlee
 */
public class WhenBuildingFacesTester {

    private FacesTester tester;

    @Before
    public void setUp() {
        tester = new FacesTester();
    }

    //@Test
    public void shouldBeAbleToProcessWebInfClasses() {
        ServletContext context = (ServletContext)tester.getFacesContext().getExternalContext().getContext();
        System.err.println("Hi!");
        System.err.println(context.getResourcePaths("/"));
        System.err.println(context.getResourcePaths("/WEB-INF"));
    }

    @Test
    public void shouldBeAbleToCreateStandardComponents() {
        assertThat(tester.createComponent(HtmlOutputText.COMPONENT_TYPE).getWrappedComponent() instanceof HtmlOutputText, is(true));
    }

    @Test
    public void shouldBeAbleToAddFacesMessage() {
        FacesContext context = tester.getFacesContext();
        context.addMessage(null, new FacesMessage("add entry failed - see log for details"));
    }

    @Test
    public void shouldBeAbleToAccessJsf2AnnotatedManagedBeans() {
        MyManagedBean bean = (MyManagedBean)tester.getManagedBean("myBean");

        assertThat(bean, is(not(nullValue())));
    }

    /*
     * Maybe I should break this into multiple tests.
     */
    /*
    @Test
    public void shouldHaveInjectionPerformed() {
        InjectionManager.registerObject("em", new MockEntityManager());
        InjectionManager.registerObject("emf", new MockEntityManagerFactory());

        ManagedBeanWithJpa mb = (ManagedBeanWithJpa)tester.getManagedBean("jpaBean");

        assertNotNull(mb.getEntityManager1());
        assertNotNull(mb.getEntityManager2());

        assertNotNull(mb.getEntityManagerFactory1());
        assertNotNull(mb.getEntityManagerFactory2());
    }
     */
}
