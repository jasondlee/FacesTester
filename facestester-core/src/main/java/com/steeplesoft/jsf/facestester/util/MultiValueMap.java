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

package com.steeplesoft.jsf.facestester.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author io
 */
public class MultiValueMap<T> {
    private Map<String,T[]> data = new HashMap<String, T[]>();

    public void remove(String key) {
        this.data.remove(key);
    }

    public void set(String key, T value) {
        Object[] v = new Object[1];
        v[0] = value;
        this.data.put(key,(T[]) v);
    }
    
    public void set(String key, T[] values) {
        this.data.put(key, values);
    }

    public void add(String key, T value) {
        T[] values = this.data.get(key);
        if(values == null) {
            this.set(key, value);
        } else {
            Object[] newValues = new Object[values.length+1];
            System.arraycopy(values, 0, newValues, 0, values.length);
            newValues[values.length] = value;
            this.data.put(key,(T[]) newValues);
        }
    }

    public T getSingle(String key) {
        T[] values = this.data.get(key);
        return values == null || values.length==0 ? null : values[0];
    }

    public T[] getAll(String key) {
        return this.data.get(key);
    }

    public Set<String> keySet() {
        return this.data.keySet();
    }

    public Map<String, T[]> getData() {
        return this.data;
    }

    public void setData(Map<String, T[]> data) {
        this.data = data;
    }


}
