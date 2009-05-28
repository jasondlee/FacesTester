/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author jasonlee
 */
public class TestServletContextListener implements ServletContextListener {
    public static boolean initializedCalled = false;
    public static boolean destroyedCalled = false;

    public void contextInitialized(ServletContextEvent sce) {
        initializedCalled = true;
    }

    public void contextDestroyed(ServletContextEvent sce) {
        destroyedCalled = true;
    }

}
