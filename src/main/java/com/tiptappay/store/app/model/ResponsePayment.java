package com.tiptappay.store.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collection;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePayment {
    @JsonProperty("id")
    private int id;

    @JsonProperty("customerId")
    private Integer customerId;

    @JsonProperty("isSuccess")
    private boolean isSuccess;

    @JsonProperty("errors")
    private String[] errors;

    @JsonProperty("soNumber")
    private String soNumber;

    @JsonProperty("ccApproved")
    private boolean ccApproved;

    @JsonProperty("ccHoldMessage")
    private String ccHoldMessage;

    @Override
    public String toString() {
        return "ResponsePayment{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", isSuccess=" + isSuccess +
                ", errors=" + Arrays.toString(errors) +
                ", soNumber='" + soNumber + '\'' +
                ", ccApproved=" + ccApproved +
                ", ccHoldMessage='" + ccHoldMessage + '\'' +
                '}';
    }
}