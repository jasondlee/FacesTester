/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.sample;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;

/**
 *
 * @author jasonlee
 */
class MockEntityManager implements EntityManager {

    public MockEntityManager() {
    }

    public void persist(Object arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T merge(T arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void remove(Object arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T find(Class<T> arg0, Object arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T getReference(Class<T> arg0, Object arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void flush() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setFlushMode(FlushModeType arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public FlushModeType getFlushMode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void lock(Object arg0, LockModeType arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void refresh(Object arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean contains(Object arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query createQuery(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query createNamedQuery(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query createNativeQuery(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query createNativeQuery(String arg0, Class arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query createNativeQuery(String arg0, String arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void joinTransaction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getDelegate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isOpen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public EntityTransaction getTransaction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
