package com.tiptappay.store.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BagRemoveResp {
    private boolean isRemoved;
    private int totalDevices;
    private String message;
}
