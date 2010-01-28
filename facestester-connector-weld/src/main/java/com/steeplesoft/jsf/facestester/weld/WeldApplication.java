package com.steeplesoft.jsf.facestester.weld;

import javax.el.ExpressionFactory;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.application.Application;
import javax.faces.application.ApplicationWrapper;

public class WeldApplication extends ApplicationWrapper {
    private Application delegate;
    private ExpressionFactory expressionFactory;

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
//            InitialContext context = new InitialContext();
//            return (BeanManager) context.lookup("java:comp/BeanManager");
        return null;

    }
}