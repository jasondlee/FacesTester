/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester;

import com.steeplesoft.jsf.facestester.metadata.FacesConfig;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author jasonlee
 */
public class WhenPerformingStaticAnalysis {

    @Test
    public void shouldValidateGoodBeanDefinition() throws IOException, ParserConfigurationException, SAXException {
        FacesConfig fc = new FacesConfig (new File ("src/test/resources/META-INF/faces-config-good.xml"));
        fc.performStaticAnalysis();
    }

    @Test(expected = AssertionError.class)
    public void shouldFailOnBadBeanDefinition() throws IOException, ParserConfigurationException, SAXException {
        FacesConfig fc = new FacesConfig (new File ("src/test/resources/META-INF/faces-config-bad.xml"));
        fc.performStaticAnalysis();
    }

}
