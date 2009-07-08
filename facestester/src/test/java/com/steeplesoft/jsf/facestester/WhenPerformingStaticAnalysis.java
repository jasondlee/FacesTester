/*
 * Copyright (c) 2009, Jason Lee <jason@steeplesoft.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the <ORGANIZATION> nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
        fc.validateManagedBeans();
    }

    @Test(expected = AssertionError.class)
    public void shouldFailOnBadBeanDefinition() throws IOException, ParserConfigurationException, SAXException {
        FacesConfig fc = new FacesConfig (new File ("src/test/resources/META-INF/faces-config-bad.xml"));
        fc.validateManagedBeans();
    }

    @Test
    public void shouldValidateGoodComponentDefinitions() throws IOException, ParserConfigurationException, SAXException {
        FacesConfig fc = new FacesConfig (new File ("src/test/resources/META-INF/faces-config-good.xml"));
        fc.validateComponents();
    }

    @Test(expected = AssertionError.class)
    public void shouldFailOnBadComponentDefinitions() throws IOException, ParserConfigurationException, SAXException {
        FacesConfig fc = new FacesConfig (new File ("src/test/resources/META-INF/faces-config-bad.xml"));
        fc.validateComponents();
    }

    @Test
    public void shouldValidateGoodRendererDefinitions() throws IOException, ParserConfigurationException, SAXException {
        FacesConfig fc = new FacesConfig (new File ("src/test/resources/META-INF/faces-config-good.xml"));
        fc.validateRenderers();
    }

    @Test(expected = AssertionError.class)
    public void shouldFailOnBadRendererDefinitions() throws IOException, ParserConfigurationException, SAXException {
        FacesConfig fc = new FacesConfig (new File ("src/test/resources/META-INF/faces-config-bad.xml"));
        fc.validateRenderers();
    }

    @Test
    public void shouldValidateGoodPhaseListenerDefinitions() throws IOException, ParserConfigurationException, SAXException {
        FacesConfig fc = new FacesConfig (new File ("src/test/resources/META-INF/faces-config-good.xml"));
        fc.validatePhaseListeners();
    }

    @Test(expected = AssertionError.class)
    public void shouldFailOnBadPhaseListenersDefinitions() throws IOException, ParserConfigurationException, SAXException {
        FacesConfig fc = new FacesConfig (new File ("src/test/resources/META-INF/faces-config-bad.xml"));
        fc.validatePhaseListeners();
    }

    @Test(expected = AssertionError.class)
    public void shouldFailOnInvalidPhaseListenersDefinitions() throws IOException, ParserConfigurationException, SAXException {
        FacesConfig fc = new FacesConfig (new File ("src/test/resources/META-INF/faces-config-invalid.xml"));
        fc.validatePhaseListeners();
    }

    @Test(expected = AssertionError.class)
    public void shouldFailOnInvalidComponentDefinitions() throws IOException, ParserConfigurationException, SAXException {
        FacesConfig fc = new FacesConfig (new File ("src/test/resources/META-INF/faces-config-invalid.xml"));
        fc.validateComponents();
    }

    @Test(expected = AssertionError.class)
    public void shouldFailOnInvalidRendererDefinitions() throws IOException, ParserConfigurationException, SAXException {
        FacesConfig fc = new FacesConfig (new File ("src/test/resources/META-INF/faces-config-invalid.xml"));
        fc.validateRenderers();
    }
}