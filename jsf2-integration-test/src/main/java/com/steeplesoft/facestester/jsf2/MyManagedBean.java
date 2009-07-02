/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.facestester.jsf2;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author jasonlee
 */
@ManagedBean(name="myBean")
@RequestScoped
public class MyManagedBean {
    public MyManagedBean() {
        System.out.println("***** MyManagedBean ctor");
    }
}
