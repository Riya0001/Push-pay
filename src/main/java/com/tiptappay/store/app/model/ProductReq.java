package com.tiptappay.store.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReq {
    private String id;
    private String bundle;
    private String option;
    private String denomination;
    private int quantity;

    @Override
    public String toString() {
        return "ProductReq{" +
                "id='" + id + '\'' +
                ", bundle='" + bundle + '\'' +
                ", option='" + option + '\'' +
                ", denomination='" + denomination + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
