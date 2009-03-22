package com.steeplesoft.jsf.facestester;

import static com.steeplesoft.jsf.facestester.MapOfStringsMatcher.containsKey;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import javax.faces.render.ResponseStateManager;

public class WhenBuildingParameterMapFromForm {
    @Test
    public void shouldContainViewStateProperty() {
        FacesForm form = new FacesForm(null);

        assertThat(form.getParameterMap(), containsKey(ResponseStateManager.VIEW_STATE_PARAM));
    }
}
