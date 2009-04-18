package com.steeplesoft.jsf.facestester.sample;

import com.steeplesoft.jsf.facestester.FacesForm;
import com.steeplesoft.jsf.facestester.FacesPage;
import com.steeplesoft.jsf.facestester.FacesTester;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class WhenSubmittingZipCodeForm {

    @Test
    public void shouldDisplayCityForZipCode() {
        FacesTester tester = new FacesTester();
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
        FacesTester tester = new FacesTester();
        FacesPage page = tester.requestPage("/zipCodeMapper.xhtml");

        FacesForm form = page.getFormById("form");
        form.setValue("zipCode", "");
        page = form.submit("submit");

        assertThat(page.getMessageFor("form:zipCode"), is("Zip code is required"));
    }
}
