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
package com.steeplesoft.jsf.facestester.servlet.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;

import com.steeplesoft.jsf.facestester.servlet.CookieManager;

public class FacesTesterCookieManager implements CookieManager {
	
	private List<Cookie> cookies = new ArrayList<Cookie>();
	
	public synchronized void addCookie(Cookie cookie) {
		Iterator<Cookie> it = cookies.iterator();
		while(it.hasNext()) {
			if(areEqual(it.next(), cookie)) {
				it.remove();
				break;
			}
		}
		if(cookie.getMaxAge() != 0) {
			cookies.add(cookie);
		}
	}

	private boolean areEqual(Cookie cookie, Cookie other) {
		return areEqual(cookie.getName(), other.getName()) &&
		areEqual(cookie.getDomain(), other.getDomain()) &&
		areEqual(cookie.getPath(), other.getPath());
	}
	
	private boolean areEqual(String string, String other) {
		if(string == other) {
			return true;
		}
		if(string != null) {
			return string.equals(other);
		}
		return areEqual(other, string);
	}

	public Cookie[] getCookies() {
		return cookies.toArray(new Cookie[cookies.size()]);
	}

}
