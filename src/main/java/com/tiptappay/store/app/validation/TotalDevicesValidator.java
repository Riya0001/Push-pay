//package com.tiptappay.flatstyleorderform.validation;
//
//import com.tiptappay.flatstyleorderform.model.OrderForm;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
//public class TotalDevicesValidator implements ConstraintValidator<TotalDevicesValidation, Object> {
//    @Override
//    public boolean isValid(Object value, ConstraintValidatorContext context) {
//        OrderForm model = (OrderForm) value;
//
//        int totalDevices =
//                model.getNumberOf2DollarDevices() +
//                        model.getNumberOf5DollarDevices() +
//                        model.getNumberOf10DollarDevices() +
//                        model.getNumberOf20DollarDevices();
//
//        return totalDevices == model.getNumberOfClips();
//    }
//}
