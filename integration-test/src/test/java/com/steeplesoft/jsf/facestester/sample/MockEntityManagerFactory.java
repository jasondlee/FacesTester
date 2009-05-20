/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.sample;

import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jasonlee
 */
class MockEntityManagerFactory implements EntityManagerFactory {

    public MockEntityManagerFactory() {
    }

    public EntityManager createEntityManager() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public EntityManager createEntityManager(Map arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isOpen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
