package com.steeplesoft.jsf.facestester;

import com.steeplesoft.jsf.facestester.metadata.FacesConfig;
import static com.steeplesoft.jsf.facestester.servlet.ServletContextFactory.createServletContext;

import org.springframework.mock.web.MockHttpServletResponse;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import static java.lang.String.format;

import javax.faces.FactoryFinder;
import static javax.faces.FactoryFinder.LIFECYCLE_FACTORY;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.LifecycleFactory;
import static javax.faces.lifecycle.LifecycleFactory.DEFAULT_LIFECYCLE;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;

import javax.xml.parsers.ParserConfigurationException;

/**
 * @author jasonlee
 */
public class FacesTester {
    private FacesContextBuilder facesContextBuilder;
    private FacesLifecycle lifecycle;

    public FacesTester() {
        facesContextBuilder = new FacesContextBuilderImpl(createServletContext());

        LifecycleFactory factory = (LifecycleFactory) FactoryFinder.getFactory(LIFECYCLE_FACTORY);
        lifecycle = new FacesLifecycleImpl(factory.getLifecycle(
                    DEFAULT_LIFECYCLE));
    }

    public FacesComponent createComponent(String componentType) {
        FacesContext context = facesContextBuilder.createFacesContext("/dummyPage.xhtml",
                "GET", lifecycle);
        lifecycle.execute(context);

        return new FacesComponent(context.getApplication()
                                         .createComponent(componentType));
    }

    public FacesPage requestPage(String uri) {
        FacesContext context = facesContextBuilder.createFacesContext(uri,
                "GET", lifecycle);

        lifecycle.execute(context);
        lifecycle.render(context);

        checkForErrors(context);

        return new FacesPage(context, facesContextBuilder, lifecycle, uri);
    }

    public void validateFacesConfig(String path)
        throws IOException, SAXException, ParserConfigurationException {
        validateFacesConfig(new File(path));
    }

    public void validateFacesConfig(File file)
        throws IOException, SAXException, ParserConfigurationException {
        FacesConfig config = new FacesConfig(file);
        config.validateManagedBeans();
        config.validateComponents();
    }

    private void checkForErrors(FacesContext context) {
        MockHttpServletResponse response = (MockHttpServletResponse) context.getExternalContext()
                                                                            .getResponse();

        if (SC_OK == response.getStatus()) {
            return;
        }

        switch (response.getStatus()) {
        case SC_NOT_FOUND:
            throw new FacesTesterException(format("The page %s was not found.",
                    response.getErrorMessage()));

        default:
            throw new FacesTesterException(response.getErrorMessage());
        }
    }
}
