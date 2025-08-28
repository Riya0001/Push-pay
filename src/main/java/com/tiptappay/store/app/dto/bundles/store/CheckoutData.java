package com.tiptappay.store.app.dto.bundles.store;

import lombok.Data;

import java.util.Date;

@Data
public class CheckoutData {
    private String organizationName;
    private String phone;
    private String countryForSettings;
    private String email;
    private Date today;
    private Date campaignStartDate;
    private String shippingFullName;
    private String shippingAddressLine1;
    private String shippingAddressLine2;
    private String shippingCity;
    private String shippingZipCode;
    private String shippingCountry;
    private String shippingState;
    private String shippingPhone;
    private String shippingEmail;
    private String billingFullName;
    private String billingAddressLine1;
    private String billingAddressLine2;
    private String billingCity;
    private String billingZipCode;
    private String billingCountry;
    private String billingState;
    private String billingPhone;
    private String billingEmail;
    private boolean checkboxToUseShippingAddress;
}