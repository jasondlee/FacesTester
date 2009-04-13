/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.sample;

import com.steeplesoft.jsf.facestester.FacesTester;
import javax.faces.component.html.HtmlOutputText;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 * @author jasonlee
 */
public class WhenBuildingFacesTester {
    @Test
    public void shouldBeAbleToCreateStandardComponents() {
        FacesTester facesTester = new FacesTester();
        assertThat(facesTester.createComponent(HtmlOutputText.COMPONENT_TYPE).getWrappedComponent() instanceof HtmlOutputText,
                is(true));
    }
}
