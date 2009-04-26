/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.sample;

import com.steeplesoft.jsf.facestester.FacesTester;
import com.steeplesoft.jsf.sample.ZipCodeMapperPage;

import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.el.ELContext;
import javax.el.ELResolver;

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

    @Test
    public void shouldBeAbleToCreateStandardComponents() {
        assertThat(tester.createComponent(HtmlOutputText.COMPONENT_TYPE).getWrappedComponent() instanceof HtmlOutputText,
                is(true));
    }

    @Test
    public void shouldBeAbleToAddFacesMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("add entry failed - see log for details"));
    }

    @Test
    public void shouldBeAbleToAccessManagedBeans() {
        FacesContext context = FacesContext.getCurrentInstance();
        ELResolver elResolver = context.getApplication().getELResolver();
        ELContext elContext = context.getELContext();
        ZipCodeMapperPage backingBean = (ZipCodeMapperPage) elResolver.getValue(elContext, null, "ZipCodeMapperPage");

        assertThat(backingBean, is(not(nullValue())));
    }
}
