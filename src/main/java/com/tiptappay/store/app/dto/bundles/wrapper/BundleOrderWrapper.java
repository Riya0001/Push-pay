package com.tiptappay.store.app.dto.bundles.wrapper;

import com.tiptappay.store.app.model.payload.Payload;
import lombok.Data;

import java.util.List;

@Data
public class BundleOrderWrapper {
    private List<Payload.BundleOrder> bundleOrders;
    private Payload.OrderDetails orderDetails;
}
