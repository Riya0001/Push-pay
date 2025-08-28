package com.tiptappay.store.app.model;

import com.tiptappay.store.app.model.payload.Payload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReqRes {
    private Payload request;
    private ResponsePayment response;
}
