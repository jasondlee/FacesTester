package com.steeplesoft.jsf.facestester;

import com.steeplesoft.jsf.facestester.test.TestServletContextListener;
import com.steeplesoft.jsf.facestester.test.TestServletRequestListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class WhenProcessingRequests {
    protected FacesTester tester;
    
    @Before
    public void setUp() {
        tester = new FacesTester();
    }

    @Test
    public void shouldThrowExceptionForNonExistentView() {
        try {
            tester.requestPage("/foo.xhtml");
            fail("Excepted exception not thrown");
        } catch (FacesTesterException e) {
            assertThat(e.getMessage(), is("The page /foo.xhtml was not found."));
        }
    }

    /*
     * I'm not 100% sure this works correctly, but it's a start.
     */
    @Test
    public void shouldBeAbleToAccessQueryParameters() {
        FacesPage page = tester.requestPage("/queryTest.xhtml?foo=bar");
        Assert.assertNotNull(page);
        assertThat(page.getParameterValue("foo"), is("bar"));
    }

    @Test
    public void shouldHaveContextListenersExecuted() {
        tester.requestPage("/queryTest.xhtml");
        assertThat(TestServletContextListener.initializedCalled, is (true));
    }

    @Test
    public void shouldHaveRequestListenersExecuted() {
        tester.requestPage("/queryTest.xhtml");
        assertThat(TestServletRequestListener.initializedCalled, is (true));
        assertThat(TestServletRequestListener.destroyedCalled, is (true));
    }
}
