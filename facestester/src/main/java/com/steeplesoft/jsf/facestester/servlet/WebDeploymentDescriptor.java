package com.steeplesoft.jsf.facestester.servlet;

import java.util.Map;
import java.io.InputStream;

public class WebDeploymentDescriptor {
    private Map<String, String> contextParameters;

    public static WebDeploymentDescriptor createFromStream(InputStream webXmlStream) {
        return new WebDeploymentDescriptorParser().parse(webXmlStream);
    }

    public Map<String, String> getContextParameters() {
        return contextParameters;
    }

    public void setContextParameters(Map<String, String> contextParameters) {
        this.contextParameters = contextParameters;
    }
}
