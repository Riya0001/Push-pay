package com.tiptappay.store.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BagAddResp {
    private boolean isAdded;
    private int totalDevices;
    private String message;
}
