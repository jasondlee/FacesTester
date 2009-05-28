/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.test;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 *
 * @author jasonlee
 */
public class TestServletRequestListener implements ServletRequestListener {
    public static boolean initializedCalled = false;
    public static boolean destroyedCalled = false;

    public void requestInitialized(ServletRequestEvent sce) {
        initializedCalled = true;
    }

    public void requestDestroyed(ServletRequestEvent sre) {
        destroyedCalled = true;
    }
}
