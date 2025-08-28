package com.tiptappay.store.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomHttpResponse {
    private int responseCode;
    private String responseBody;
}
