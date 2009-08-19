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
package com.steeplesoft.jsf.facestester.servlet.impl;

import java.io.OutputStream;
import java.util.Map;
import java.util.Stack;

import javax.faces.render.ResponseStateManager;

import com.steeplesoft.jsf.facestester.FacesTesterException;

/**
 *
 * @author jasonlee
 */
public class MyFacesFacesTesterPrintWriter extends FacesTesterPrintWriter {
	
	private Stack<Form> foundForms = new Stack<Form>();
	
	private boolean isOpeningForm;
	private boolean isOpeningTag;
	private boolean isWritingForm;
	
    public MyFacesFacesTesterPrintWriter(OutputStream baos) {
        super(baos);
    }
    
    StringBuilder builder = new StringBuilder();
    
    @Override
    public void write(int c) {
    	if('<' == c) {
    		isOpeningTag = true;
    		builder = new StringBuilder();
    	} else if ('>' == c) {
    		isOpeningTag = false;
    		String output = builder.toString();
        	if(output.contains("enctype=") && output.contains("action=") && output.contains("method=\"post\"")) {
        		foundForms.push(new Form(parseAttribue(output, "name")));
        	}
        	builder = new StringBuilder();
    	}
    	if(isOpeningTag) {
    		builder.append((char)c);
    	}
    	super.write(c);
    }
    
    @Override
    public void write(String s) {
    	if(isOpeningTag) {
    		builder.append(s);
    	}
    	if(s.contains(ResponseStateManager.VIEW_STATE_PARAM)) {
    		String viewState = parseViewStateParam(s);
    		if(foundForms.isEmpty()) {
    			throw new FacesTesterException("Found a ViewState, but no previous form was found, probably missed a form when parsing");
    		}
    		Form peek = foundForms.peek();
    		if(peek.getViewState() != null) {
    			throw new FacesTesterException("ViewState already filled in for Form, probably missed a form when parsing");
    		}
    		peek.setViewState(viewState);
    	}
    	super.write(s);
    }
    
	private String parseViewStateParam(String string) {
		return parseAttribue(string, "value");
	}
	
	public String getViewState(String clientId) {
		for(Form form : foundForms) {
			if(form.getClientId().equals(clientId)) {
				return form.getViewState();
			}
		}
		throw new FacesTesterException("No form with clientId " + clientId + " found in output");
	}
	
	public Map.Entry<String, String> getFormSubmitHiddenParameter(final String clientId) {
		return new Map.Entry<String, String>() {

			public String getKey() {
				return clientId + "_SUBMIT";
			}

			public String getValue() {
				return "1";
			}

			public String setValue(String arg0) {
				throw new UnsupportedOperationException("Should not happen");
			}
		};
	}
	
	private String parseAttribue(String string, String attribute) {
		int valueIndex = string.indexOf(attribute + "=");
		int firstValueQuote = string.indexOf("\"", valueIndex);
		int lastValueQuote = string.indexOf("\"", firstValueQuote + 1);
		if (lastValueQuote != -1) {
			return string.substring(firstValueQuote + 1,
					lastValueQuote);
		}
		return null;
	}
	
	private class Form {
		
		private String clientId;
		private String viewState;
		
		public Form(String clientId) {
			this.clientId = clientId;
		}
		
		public String getClientId() {
			return clientId;
		}
		
		public String getViewState() {
			return viewState;
		}

		public void setViewState(String viewState) {
			this.viewState = viewState;
		}
		
	}
    
}
