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
public class QuoteReqRes {
    private Payload request;
    private ResponseQuote response;
}
