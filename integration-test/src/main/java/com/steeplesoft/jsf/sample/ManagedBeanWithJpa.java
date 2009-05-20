/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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