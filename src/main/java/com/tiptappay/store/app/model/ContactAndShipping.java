package com.tiptappay.store.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactAndShipping {
    // Customer Information
    private String customerName;

    private boolean freeCustomerNameEntryAllowed;
    private String customerParent;
    private String customerChild;
    private String customerGrandChild;
    private String customerNameFree;

    //Ministry Information
    private String division;
    private String ministryName;
    private String respcNumber;
    private java.sql.Date campaignStartDate;

    // Contact Information
    private String contactFirstName;
    private String contactLastName;
    private String contactPhoneCountryCode;
    private String contactPhone;
    private String contactEmail;
    private String contactPrefContactMethod;

    // Shipping Information
    private String attentionTo;
    private String shippingAddr1;
    private String shippingAddr2;
    private String shippingCity;
    private String shippingState;
    private String shippingProvince;
    private String shippingCountry;
    private String shippingZip;
    private String shippingPostalCode;

    public String toShippingString() {
        return "Shipping{" +
                ", shippingAddr1='" + shippingAddr1 + '\'' +
                ", shippingAddr2='" + shippingAddr2 + '\'' +
                ", shippingCity='" + shippingCity + '\'' +
                ", shippingState='" + shippingState + '\'' +
                ", shippingProvince='" + shippingProvince + '\'' +
                ", shippingCountry='" + shippingCountry + '\'' +
                ", shippingZip='" + shippingZip + '\'' +
                ", shippingPostalCode='" + shippingPostalCode + '\'' +
                '}';
    }
}
