//package com.tiptappay.flatstyleorderform.validation;
//
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//
//import java.lang.annotation.*;
//
//import static com.tiptappay.flatstyleorderform.constant.FormConstants.DefaultValidatorMessages.TOTAL_DEVICES_VALIDATOR_MESSAGE;
//
//@Documented
//@Constraint(validatedBy = TotalDevicesValidator.class)
//@Target({ElementType.TYPE})
//@Retention(RetentionPolicy.RUNTIME)
//public @interface TotalDevicesValidation {
//    String message() default TOTAL_DEVICES_VALIDATOR_MESSAGE;
//
//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
//}
