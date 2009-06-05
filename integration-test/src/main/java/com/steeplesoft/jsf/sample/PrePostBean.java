/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.sample;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 *
 * @author jasonlee
 */
public class PrePostBean {
    public boolean postConstructCalled = false;
    public boolean preDestroyedCalled = false;

    @PostConstruct
    public void postConsruct() {
        this.postConstructCalled = true;
    }

    @PreDestroy
    public void preDestroy() {
        this.preDestroyedCalled = true;
    }
}
