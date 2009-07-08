/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
