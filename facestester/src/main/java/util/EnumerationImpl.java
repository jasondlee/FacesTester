/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author jasonlee
 */
public class EnumerationImpl implements Enumeration {

    private List values = new ArrayList();
    private int index;
    
    public EnumerationImpl(Object value) {
        values.add(value);
        index = 0;
    }

    public EnumerationImpl(Set values) {
        Iterator i = values.iterator();
        while (i.hasNext()) {
            values.add(i.next());
        }
        index = 0;
    }

    public boolean hasMoreElements() {
        return index <= (values.size()-1);
    }

    public Object nextElement() {
        return values.get(index++);
    }
}