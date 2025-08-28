package com.tiptappay.store.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShoppingBagUpdateResp {
    private boolean success;

    private int miniClipKitQty;
    private int floorStandQty;
    private int kettleDisplayQty;
    private int device5DollarQty;
    private int device10DollarQty;
    private int device20DollarQty;
    private int batteryForKettleDisplayQty;

    private String miniClipKitTotalAmount;
    private String floorStandTotalAmount;
    private String kettleDisplayTotalAmount;
    private String device5DollarTotalAmount;
    private String device10DollarTotalAmount;
    private String device20DollarTotalAmount;
    private String batteryForKettleDisplayTotalAmount;

    private int totalProducts;
    private String totalOneTimeFeeAmount;
    private String totalRentalFeeAmount;
    private String shippingAmount;
    private String taxAmount;
    private String totalAmount;
    private String message;
}
