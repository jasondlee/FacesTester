package com.steeplesoft.jsf.facestester.weld;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;

public class WeldApplicationFactory extends ApplicationFactory {
    private Application application;
    private ApplicationFactory factory;

    public WeldApplicationFactory (ApplicationFactory factory) {
        this.factory = factory;
    }

    @Override
    public Application getApplication() {
        if (application == null) {
            application = new WeldApplication(factory.getApplication());
        }

        return application;
    }

    @Override
    public void setApplication(Application application) {
        factory.setApplication(application);
    }
}
