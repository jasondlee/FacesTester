package com.steeplesoft.jsf.facestester;

import javax.faces.component.UIInput;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import java.util.Map;
import java.util.HashMap;

public class FacesForm extends FacesComponent {
    private HtmlForm form;

    public FacesForm(HtmlForm htmlForm) {
        super(htmlForm);
        form = htmlForm;
    }



    public void setValue(String fieldName, String value) {
        UIInput component = (UIInput) form.findComponent(fieldName);
        component.setValue(value);
    }


    /**
     * This method will cause a form submit, as if the user had click on the
     * UICommand component specified by <code>id</code>.
     * @param id
     */
    public void submit() {
//        FacesComponent uiCommand = getComponentWithId(id);
//        assert (uiCommand != null);
//
//        if (!(uiCommand.getWrappedComponent() instanceof UICommand)) {
//            throw new AssertionError ("Id '" + id +"' does not refer to a UICommand component");
//        }
//        FacesForm form = uiCommand.getEnclosingForm();
//        if (uiCommand == null) {
//            throw new AssertionError("UICommand '" + id + "' is not enclosed in a <h:form>");
//        }
    }


    public Map<String, String> getParameters() {
        Map<String, String> parameters = new HashMap<String, String>();
        for (UIComponent each : form.getChildren()) {
            if (each instanceof UIInput) {
                parameters.put(each.getId(), ((UIInput) each).getValue().toString());
            }
        }
        return parameters;
    }
}
