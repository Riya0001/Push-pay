package com.tiptappay.store.app.dto.bundles.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentData {
    @JsonProperty("nameOnCard")
    private String nameOnCard;

    @JsonProperty("cardNumber")
    private String cardNumber;

    @JsonProperty("expirationDate")
    private String expiration;

    @JsonProperty("cvv")
    private String cvv;
}