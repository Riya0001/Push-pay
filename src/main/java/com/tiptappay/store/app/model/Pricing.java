package com.tiptappay.store.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pricing {
    private boolean overrideSetupFee;
    private int setupFeeAmount;
    private int oneTimeFeeAmount;
    private int monthlyFeeAmount;
    private String currency;
}
