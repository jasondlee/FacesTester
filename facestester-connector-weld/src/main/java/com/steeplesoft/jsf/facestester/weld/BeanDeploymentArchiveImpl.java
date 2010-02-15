package com.steeplesoft.jsf.facestester.weld;

import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.ejb.spi.EjbDescriptor;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BeanDeploymentArchiveImpl implements BeanDeploymentArchive {
    private String id;
    private SimpleServiceRegistry serviceRegistry = null;
    private List<Class<?>> beanClasses;
    private List<URL> beansXml;

    public BeanDeploymentArchiveImpl(String id, List<Class<?>> beanClasses, List<URL> beansXml) {
        this.id = id;
        this.beanClasses = beanClasses;
        this.beansXml = beansXml;
    }
    public String getId() {
        return id;
    }

    public Collection<BeanDeploymentArchive> getBeanDeploymentArchives() {
        return new ArrayList<BeanDeploymentArchive>();
    }

    public Collection<Class<?>> getBeanClasses() {
        return beanClasses;
    }

    public Collection<URL> getBeansXml() {
        return beansXml;
    }

    public Collection<EjbDescriptor<?>> getEjbs() {
        return Collections.emptyList(); // For now?
    }

    public ServiceRegistry getServices() {
        if (null == serviceRegistry) {
            serviceRegistry = new SimpleServiceRegistry();
        }
        return serviceRegistry;
    }
}