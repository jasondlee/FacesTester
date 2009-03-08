package com.steeplesoft.jsf.facestester.sample;

import com.steeplesoft.jsf.facestester.FacesForm;
import com.steeplesoft.jsf.facestester.FacesPage;
import com.steeplesoft.jsf.facestester.FacesTester;
import org.junit.Test;

public class WhenSubmittingZipCodeForm {
    @Test
    public void shouldDisplayCityForZipCode() {
        FacesTester tester = new FacesTester();
        FacesPage page = tester.requestPage("/zipCodeMapper.xhtml");

        FacesForm form = page.getFormById("form");
        form.setValue("zipCode", "95054");
        form.submit();
    }
}
