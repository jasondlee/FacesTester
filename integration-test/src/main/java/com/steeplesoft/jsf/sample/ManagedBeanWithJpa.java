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
package com.steeplesoft.jsf.sample;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author jasonlee
 */
public class ManagedBeanWithJpa {

    @PersistenceContext(unitName = "em", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager1;
    private EntityManager entityManager2;
    @PersistenceUnit(unitName = "emf")
    private EntityManagerFactory entityManagerFactory1;
    private EntityManagerFactory entityManagerFactory2;

    public EntityManager getEntityManager1() {
        return entityManager1;
    }

    public void setEntityManager1(EntityManager entityManager) {
        this.entityManager1 = entityManager;
    }

    public EntityManager getEntityManager2() {
        return entityManager2;
    }

    @PersistenceContext(unitName = "em", type = PersistenceContextType.EXTENDED)
    public void setEntityManager2(EntityManager entityManager2) {
        this.entityManager2 = entityManager2;
    }

    public EntityManagerFactory getEntityManagerFactory1() {
        return entityManagerFactory1;
    }

    public void setEntityManagerFactory1(EntityManagerFactory entityManagerFactory1) {
        this.entityManagerFactory1 = entityManagerFactory1;
    }

    public EntityManagerFactory getEntityManagerFactory2() {
        return entityManagerFactory2;
    }

    @PersistenceUnit(unitName = "emf")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory2) {
        this.entityManagerFactory2 = entityManagerFactory2;
    }
}