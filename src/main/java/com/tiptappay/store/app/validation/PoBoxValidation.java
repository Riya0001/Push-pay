package com.tiptappay.store.app.validation;

import com.tiptappay.store.app.constant.AppConstants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PoBoxValidator.class)
@Documented
public @interface PoBoxValidation {
    String message() default AppConstants.DefaultValidatorMessages.PO_BOX_VALIDATOR_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}