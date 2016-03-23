package com.realdolmen.course.controller;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by heleneshaikh on 07/03/16.
 */

@Target({ElementType.METHOD, ElementType.FIELD}) //annotation type is applicable to
@Retention(RetentionPolicy.RUNTIME) //how long the annotation has to be retained
@Constraint(validatedBy = EmailConstraintValidator.class) //Marks an annotation as being a Bean Validation constraint.

public @interface Email {
    // Needs to have 3 attributes: message, groups, payload

    String message() default "Error validating email address";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}