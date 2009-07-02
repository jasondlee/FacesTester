/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.context.mojarra;

import com.steeplesoft.jsf.facestester.Util;
import  com.sun.faces.config.AnnotationScanner;
import javax.servlet.ServletContext;

/**
 *
 * @author jasonlee
 */
public class FacesTesterAnnotationScanner extends AnnotationScanner {

    public FacesTesterAnnotationScanner(ServletContext sc) {
        super(sc);
        Util.getLogger().info("Hello! :)");
    }

}
