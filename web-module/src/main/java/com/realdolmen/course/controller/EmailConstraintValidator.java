package com.realdolmen.course.controller;

import org.hibernate.validator.constraints.Email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by heleneshaikh on 07/03/16.
 */
public class EmailConstraintValidator implements ConstraintValidator<Email, String> {
    private static final String DOMAIN_SUFFIX = "@gmail.com";

    @Override
    public void initialize(Email constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null
                && !value.trim().equals("")
                && value.endsWith(DOMAIN_SUFFIX)
                && !value.startsWith(DOMAIN_SUFFIX);
    }
}
