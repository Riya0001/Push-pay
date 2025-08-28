package com.tiptappay.store.app.dto.legion;

import lombok.Data;

import java.util.Date;

@Data
public class CheckoutData {
    private String organizationName;
    private int twoPackPoppyDisplay;

    private String branchNumber;
    private String provincialCommand;
    private String selectedLanguage;

    // ✅ Contact Info
    private String contactFirstName;
    private String contactLastName;
    private String contactEmail;
    private String contactPhoneNumber;

    // ✅ Partner Info
    private String selectedPartner;
    private String partner;
    private String province;
    private String estimatedPopulation;

    // ✅ Customer Info
    private String phone;
    private String countryForSettings;
    private String email;
    private Date today;
    private Date campaignStartDate;

    // ✅ Shipping Info
    private String shippingFullName;
    private String shippingAddressLine1;
    private String shippingAddressLine2;
    private String shippingCity;
    private String shippingZipCode;
    private String shippingCountry;
    private String shippingState;
    private String shippingPhone;
    private String shippingEmail;

    // ✅ Billing Info
    private String billingFullName;
    private String billingAddressLine1;
    private String billingAddressLine2;
    private String billingCity;
    private String billingZipCode;
    private String billingCountry;
    private String billingState;
    private String billingPhone;
    private String billingEmail;
    private String billingAttention;

    // ✅ Additional Delivery Info
    private String deliveryInstructions;
    private boolean checkboxToUseShippingAddress;

    // ✅ Program Details
    private String programName;
    private String programType;
    private String programStartDate;
    private String programEndDate;

    // ✅ Order Details
    private String productName;
    private String productId;
    private int productQuantity;
    private String productUnit;
    private double unitPrice;
    private double totalPrice;

    // ✅ Bundle Order
    private String bundleName;
    private String bundleId;
    private int bundleQuantity;
    private double bundlePrice;

    // ✅ Pricing
    private double subTotal;
    private double tax;
    private double shippingFee;
    private double total;
    private String currency;

    // ✅ Payment Info
    private String paymentMethod;
    private String paymentStatus;
    private String transactionId;
    private String paymentDate;

    // ✅ Division & Ministry
    private String division;
    private String ministryName;
    private String respcNumber;
}
