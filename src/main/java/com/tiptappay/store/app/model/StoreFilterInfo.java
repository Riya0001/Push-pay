package com.tiptappay.store.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreFilterInfo {
    private boolean active;
    private String redirectTo;
}
