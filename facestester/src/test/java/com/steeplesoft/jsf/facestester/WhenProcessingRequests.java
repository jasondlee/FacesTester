package com.steeplesoft.jsf.facestester;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class WhenProcessingRequests {
    @Test()
    public void shouldThrowExceptionForNonExistentView() {
        FacesTester tester = new FacesTester();

        try {
            tester.requestPage("/foo.xhtml");
            fail("Excepted exception not thrown");
        } catch (FacesTesterException e) {
            assertThat(e.getMessage(), is("The page /foo.xhtml was not found."));
        }
    }
}
