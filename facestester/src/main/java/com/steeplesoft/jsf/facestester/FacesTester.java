package com.steeplesoft.jsf.facestester;

import com.steeplesoft.jsf.facestester.metadata.FacesConfig;
import static com.steeplesoft.jsf.facestester.servlet.ServletContextFactory.createServletContext;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.mock.web.MockHttpServletResponse;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import static java.lang.String.format;

import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import static javax.faces.FactoryFinder.LIFECYCLE_FACTORY;
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

    /**
     * The idea for this method is to have it interrogate the UIComponent for
     * all "local" getters and setters.  It would then set dummy data on the component
     * and call saveState() on it.  Next, it would create a new instance of the
     * component and call restoreState() on it, using the saved state from above.
     * Finally, it would call each getter on the new component and insure that the
     * values returned match the values set on the old component.  If the component
     * has a property that has not been added to the state saving code, this should
     * detect that oversight.  The problem is that we might detect a getter/setter
     * pair that are not really part of the component's state.  It seems odd to have
     * such a pair of methods, but only convention would prevent a component author
     * from doing that, and conventions are rarely followed completely.  We may have
     * to provide a blacklist parameter for this situations.
     * @return
     */
    public void testStateSaving(String componentType) {
        UIComponent origComp = createComponent(componentType).getWrappedComponent();
        Method[] methods = origComp.getClass().getDeclaredMethods();
        List<String> properties = new ArrayList<String>();

        for (Method method : methods) {
            if (isGetter(method)) {
                String property = method.getName().substring(3);
                Class type = method.getReturnType();
                try {
                    Method setter = origComp.getClass().getDeclaredMethod("set" + property, type);
                    if (isSetter(setter)) {
                        // We've found a readable/writable property, so let's generate some
                        // dummy data for testing
                        Object dummyData = generateDefaultValue(type);
                        setter.invoke(origComp, dummyData);
                        properties.add(property);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(FacesTester.class.getName()).info("Skipping '" + property + "'.  Does not appear to be a property:  " + ex.getLocalizedMessage());
                }
            }
        }

        if (properties.size() > 0) { // We found properties, so let's test state saving
            Object state = origComp.saveState(FacesContext.getCurrentInstance());
            UIComponent newComp = createComponent(componentType).getWrappedComponent();
            newComp.restoreState(FacesContext.getCurrentInstance(), state);
            for (String property : properties) {
                try {
                    Method getter = origComp.getClass().getDeclaredMethod("get" + property, new Class<?>[]{});
                    Object value1 = getter.invoke(origComp, new Object[]{});
                    Object value2 = getter.invoke(newComp, new Object[]{});
                    if (value1 != value2) {
                        throw new AssertionError("The restored state for '" + property + "' does not match.");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(FacesTester.class.getName()).log(Level.SEVERE, null, ex);
                    throw new AssertionError(ex.getLocalizedMessage());
                }
            }
        }
    }

    protected static boolean isGetter(Method method) {
        if (!method.getName().startsWith("get")) {
            return false;
        }
        if (method.getParameterTypes().length != 0) {
            return false;
        }
        if (void.class.equals(method.getReturnType())) {
            return false;
        }
        return true;
    }

    protected static boolean isSetter(Method method) {
        if (!method.getName().startsWith("set")) {
            return false;
        }
        if (method.getParameterTypes().length != 1) {
            return false;
        }
        return true;
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

    private Object generateDefaultValue(Class type) throws InstantiationException, IllegalAccessException {
        String name = type.getName();
        if (name.equals("java.lang.String")) {
            return "FacesTester dummy value";
        } else if (name.equals("java.lang.Boolean")) {
            return Boolean.FALSE;
        } else {
            return type.newInstance();
        }
    }
}
