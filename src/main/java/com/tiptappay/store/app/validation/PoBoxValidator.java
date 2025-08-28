package com.tiptappay.store.app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PoBoxValidator implements ConstraintValidator<PoBoxValidation, String> {

    @Override
    public void initialize(PoBoxValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String lowercaseValue = value.toLowerCase();
        String substring1 = "po box";
        String substring2 = "p.o. box";

        return !((lowercaseValue.contains(substring1)) || (lowercaseValue.contains(substring2)));
    }
}