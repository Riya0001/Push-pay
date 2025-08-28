package com.tiptappay.store.app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SupportCaseFormValidator.class)
public @interface ValidSupportCaseForm {
    String message() default "Invalid contact information";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}