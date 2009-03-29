package com.steeplesoft.jsf.facestester;

import org.junit.Test;
import static com.steeplesoft.jsf.facestester.FacesFormBuilder.newFacesForm;

public class WhenSubmittingForm {
    @Test(expected = FacesTesterException.class)
    public void shouldThrowAnExceptionIfSubmittedIdDoesNotExist() {
        FacesForm form = newFacesForm().withButton("foo").build();
        form.submit("bar");
    }
}
