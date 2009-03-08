package com.steeplesoft.jsf.facestester.sample;

import com.steeplesoft.jsf.facestester.FacesForm;
import com.steeplesoft.jsf.facestester.FacesPage;
import com.steeplesoft.jsf.facestester.FacesTester;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class WhenSubmittingZipCodeForm {
    @Test
    public void shouldDisplayCityForZipCode() {
        FacesTester tester = new FacesTester();
        FacesPage page = tester.requestPage("/zipCodeMapper.xhtml");
        FacesForm form = page.getFormById("form");

        assertThat(form, is(notNullValue()));
    }
}
