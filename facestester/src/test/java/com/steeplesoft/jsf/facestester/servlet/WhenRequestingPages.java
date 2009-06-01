/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.servlet;

import com.steeplesoft.jsf.facestester.FacesTester;
import com.steeplesoft.jsf.facestester.test.TestFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jasonlee
 */
public class WhenRequestingPages {
    private FacesTester facestTester;
    @Before
    public void setUp() {
        this.facestTester = new FacesTester();
    }

    @Test
    public void shouldHaveFiltersCalled() {
        facestTester.requestPage("/queryTest.jsf");
        Assert.assertTrue(TestFilter.FILTER_RUN);
    }
}
