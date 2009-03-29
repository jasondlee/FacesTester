package com.steeplesoft.jsf.facestester;

import org.junit.Test;
import static com.steeplesoft.jsf.facestester.FacesFormBuilder.newFacesForm;

public class WhenSettingValueOnForm {
    @Test(expected = FacesTesterException.class)
    public void shouldThrowAnExceptionIfFieldDoesNotExist() {
        FacesForm form = newFacesForm().withInputText("foo").build();
        form.setValue("bar", "test");
    }
}
