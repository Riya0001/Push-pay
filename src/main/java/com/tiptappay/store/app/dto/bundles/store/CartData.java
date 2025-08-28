package com.tiptappay.store.app.dto.bundles.store;

import lombok.Data;

@Data
public class CartData {
    private String title;
    private String deviceDenomination;
    private String solutionSelection;
    private String subscriptionTerm;
    private String paymentOption;
    private int bundlePrice;
    private int firstMonthPayment;
    private int secondMonthAndBeyond;
    private int payInFull;
    private String priceKey;
    private int priceKeyIndex;
    private boolean customizedSignage;
}