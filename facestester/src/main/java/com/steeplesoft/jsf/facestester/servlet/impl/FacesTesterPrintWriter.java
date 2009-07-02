/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.jsf.facestester.servlet.impl;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 *
 * @author jasonlee
 */
class FacesTesterPrintWriter extends PrintWriter {
    public FacesTesterPrintWriter(String fileName) throws FileNotFoundException {
        super(fileName);
    }
}
