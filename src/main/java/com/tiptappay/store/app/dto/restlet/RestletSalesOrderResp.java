package com.tiptappay.store.app.dto.restlet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RestletSalesOrderResp {
    @JsonProperty("isSuccess")
    private boolean isSuccess;

    @JsonProperty("errors")
    private List<String> errors;

    @JsonProperty("salesOrderData")
    private SalesOrderData salesOrderData;

    @Getter
    @Setter
    public static class SalesOrderData {
        private String id;
        private String number;
        private String customerId;
        private String customerName;
        private String totalAmount;
        private String pdf;
    }
}