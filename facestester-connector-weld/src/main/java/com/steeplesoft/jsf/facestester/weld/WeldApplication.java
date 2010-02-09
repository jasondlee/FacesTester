package com.steeplesoft.jsf.facestester.weld;

import org.jboss.weld.bootstrap.WeldBootstrap;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;

import javax.el.ExpressionFactory;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.application.Application;
import javax.faces.application.ApplicationWrapper;

public class WeldApplication extends ApplicationWrapper {
    private Application delegate;
    private ExpressionFactory expressionFactory;
    private BeanManager beanManager;

    public WeldApplication(Application application) {
        delegate = application;
    }
    @Override
    public Application getWrapped() {
        return delegate;
    }

    @Override
    public ExpressionFactory getExpressionFactory() {
        if (this.expressionFactory == null) {
            BeanManager beanManager = getBeanManager();
            if (beanManager != null) {
                this.expressionFactory = beanManager.wrapExpressionFactory(delegate.getExpressionFactory());
          } else {
              this.expressionFactory = delegate.getExpressionFactory();
          }
        }
        return expressionFactory;
    }

    private BeanManager getBeanManager() {
        if (beanManager == null) {
            BeanDeploymentArchive bda = weldDeployer.getBeanDeploymentArchiveForBundle(bundle);
            if( bda != null ) {
                WeldBootstrap bootstrap = weldDeployer.getBootstrapForApp(bundle.getApplication());
                beanManager = bootstrap.getManager(bda);
            }
        }
        return beanManager;

    }
}