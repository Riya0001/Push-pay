package com.tiptappay.store.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    // Credit Card Information
    private String nameOnCard;
    private String cardNumber;
    private String cardExpiryMonth;
    private String cardExpiryYear;
    private String cardCVV;

    // Billing Information
    private String attentionTo;
    private String billingAddr1;
    private String billingAddr2;
    private String billingCity;
    private String billingState;
    private String billingProvince;
    private String billingCountry;
    private String billingZip;
    private String billingPostalCode;

    private Boolean payByCreditCard;
}
