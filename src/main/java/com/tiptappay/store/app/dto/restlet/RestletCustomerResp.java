package com.tiptappay.store.app.dto.restlet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RestletCustomerResp {
    @JsonProperty("isSuccess")
    private boolean isSuccess;

    @JsonProperty("errors")
    private List<String> errors;

    @JsonProperty("customerData")
    private CustomerData customerData;

    @Getter
    @Setter
    public static class CustomerData {
        private String id;
        private String companyName;
        private String category;
        private String status;
        private String email;
        private String phone;
        private String parentId;
        private String parentName;

        @JsonProperty("billAddressee")
        private String billingAddressee;

        @JsonProperty("billAddress1")
        private String billingAddress1;

        @JsonProperty("billAddress2")
        private String billingAddress2;

        @JsonProperty("billCity")
        private String billingCity;

        @JsonProperty("billState")
        private String billingState;

        @JsonProperty("billZip")
        private String billingZip;

        @JsonProperty("billCountry")
        private String billingCountry;

        @JsonProperty("shipAddressee")
        private String shippingAddressee;

        @JsonProperty("shipAddress1")
        private String shippingAddress1;

        @JsonProperty("shipAddress2")
        private String shippingAddress2;

        @JsonProperty("shipCity")
        private String shippingCity;

        @JsonProperty("shipState")
        private String shippingState;

        @JsonProperty("shipZip")
        private String shippingZip;

        @JsonProperty("shipCountry")
        private String shippingCountry;

        private String primaryCurrency;
        private List<String> allCurrencies;
        private double balance;
        private List<Invoice> invoices;
        private List<SalesOrder> salesOrders;

        @Getter
        @Setter
        public static class Invoice {
            private String id;
            private String number;
            private String date;
            private double amount;
            private double amountDue;
        }

        @Getter
        @Setter
        public static class SalesOrder {
            private String id;
            private String number;
            private String date;
            private double amount;
            private double amountDue;
        }
    }
}