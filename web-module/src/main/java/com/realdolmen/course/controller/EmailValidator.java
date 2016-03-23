package com.realdolmen.course.controller;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Created by heleneshaikh on 06/03/16.
 */

@FacesValidator(value = "emailValidator")
public class EmailValidator implements Validator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value != null) {
            if (!(value instanceof String)) {
                throw new IllegalArgumentException(
                        "The value must be a String");
            }
            String email = (String) value;
            if (!email.contains("@")) {
                throw new ValidatorException(
                    new FacesMessage("Invalid Email Address"));
            }
        }
    }
}
