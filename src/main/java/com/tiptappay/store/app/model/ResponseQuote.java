package com.tiptappay.store.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseQuote {
    @JsonProperty("id")
    private int id;

    @JsonProperty("isSuccess")
    private boolean isSuccess;

    @JsonProperty("errors")
    private String[] errors;

    @JsonProperty("taxAmount")
    private double taxAmount;

    @JsonProperty("shippingAmount")
    private double shippingAmount;
}
