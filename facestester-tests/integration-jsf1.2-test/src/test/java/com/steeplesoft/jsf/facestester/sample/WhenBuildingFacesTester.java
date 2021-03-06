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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.File;

import javax.faces.FactoryFinder;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.steeplesoft.jsf.facestester.FacesTester;
import com.steeplesoft.jsf.facestester.injection.InjectionManager;
import com.steeplesoft.jsf.facestester.sample.MockEntityManager;
import com.steeplesoft.jsf.facestester.sample.MockEntityManagerFactory;
import com.steeplesoft.jsf.facestester.servlet.WebDeploymentDescriptor;
import com.steeplesoft.jsf.sample.ManagedBeanWithJpa;
import com.steeplesoft.jsf.sample.PrePostBean;
import com.steeplesoft.jsf.sample.ZipCodeMapperPage;

/**
 * @author jasonlee
 */
public class WhenBuildingFacesTester {

    protected static FacesTester tester;

    @BeforeClass
    public static void configureFacesTester() {
		WebDeploymentDescriptor webDeploymentDescriptor = new WebDeploymentDescriptor(new File("sample-webapp-jsf1.2"));
        tester = new FacesTester(webDeploymentDescriptor);
    }


    @Test
    public void shouldBeAbleToCreateStandardComponents() {
        assertThat(tester.createComponent(HtmlOutputText.COMPONENT_TYPE).getWrappedComponent() instanceof HtmlOutputText,
                is(true));
    }

    @Test
    public void shouldBeAbleToAddFacesMessage() {
        FacesContext context = tester.getFacesContext();
        context.addMessage(null, new FacesMessage("add entry failed - see log for details"));
    }

    @Test
    public void shouldBeAbleToAccessManagedBeans() {
        ZipCodeMapperPage backingBean = (ZipCodeMapperPage)tester.getManagedBean("ZipCodeMapperPage");

        assertThat(backingBean, is(not(nullValue())));
    }

    /*
     * Maybe I should break this into multiple tests.
     */
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

    @Test
    public void shouldExecutePostConstruct() {
        PrePostBean pcb = (PrePostBean)tester.getManagedBean("prePost");
        Assert.assertTrue(pcb.postConstructCalled);
    }

    @Test
    public void shouldExecutePreDestroy() {
        PrePostBean pcb = (PrePostBean)tester.getManagedBean("prePost");
        tester = new FacesTester(new WebDeploymentDescriptor(new File("sample-webapp-jsf1.2")));
        Assert.assertTrue(pcb.preDestroyedCalled);
    }
 
    
}
