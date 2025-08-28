package com.tiptappay.store.app.dto.legion;

import lombok.Data;

/**
 * DTO representing individual cart item data for the Legion store.
 */
@Data
public class CartData {

    // Basic display info
    private String title;
    private String deviceDenomination;
    private String solutionSelection;

    // Subscription details
    private String subscriptionTerm;
    private String paymentOption;

    // Pricing breakdown (in cents)
    private int bundlePrice;
    private int firstMonthPayment;
    private int secondMonthAndBeyond;
    private int payInFull;

    // Price lookup support
    private String priceKey;
    private int priceKeyIndex;

    // Product quantity
    private int threeDeviceDisplayEnglishQuantity;
}
