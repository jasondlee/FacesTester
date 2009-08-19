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
public class MockEntityManager implements EntityManager {

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
