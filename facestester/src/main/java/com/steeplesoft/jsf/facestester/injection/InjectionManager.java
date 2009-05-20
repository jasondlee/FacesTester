/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.injection;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jasonlee
 */
public class InjectionManager {
    protected static Map<String, Object> registeredObjects = new HashMap<String, Object>();

    public static Object getRegisteredObject(String name) {
        return registeredObjects.get(name);
    }

    public static void registerObject(String name, Object object) {
        registeredObjects.put(name, object);
    }
}
