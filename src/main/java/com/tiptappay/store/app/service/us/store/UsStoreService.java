package com.tiptappay.store.app.service.us.store;

import com.tiptappay.store.app.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tiptappay.store.app.constant.StoreConstants.USStore.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsStoreService {

    public final List<Product> products = List.of(
            new Product(PRODUCT_ID_MINI_CLIP_KIT, PRODUCT_NAME_MINI_CLIP_KIT, "", FEE_ONE_TIME_IN_CENTS, 0, 0, PRODUCT_IMAGES_MINI_CLIP_KIT),
            new Product(PRODUCT_ID_DEVICE_5_DOLLAR, PRODUCT_NAME_DEVICE_5_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, 0, PRODUCT_IMAGES_DEVICE_5_DOLLAR),
            new Product(PRODUCT_ID_DEVICE_10_DOLLAR, PRODUCT_NAME_DEVICE_10_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, 0, PRODUCT_IMAGES_DEVICE_10_DOLLAR),
            new Product(PRODUCT_ID_DEVICE_20_DOLLAR, PRODUCT_NAME_DEVICE_20_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, 0, PRODUCT_IMAGES_DEVICE_20_DOLLAR)
    );

    public List<Product> getProductList() {
        return products;
    }

    public List<Product> getProductList(int miniClipKitQty, int device5DollarQty, int device10DollarQty, int device20DollarQty) {
        return List.of(
                new Product(PRODUCT_ID_MINI_CLIP_KIT, PRODUCT_NAME_MINI_CLIP_KIT, "", FEE_ONE_TIME_IN_CENTS, 0, miniClipKitQty, PRODUCT_IMAGES_MINI_CLIP_KIT),
                new Product(PRODUCT_ID_DEVICE_5_DOLLAR, PRODUCT_NAME_DEVICE_5_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, device5DollarQty, PRODUCT_IMAGES_DEVICE_5_DOLLAR),
                new Product(PRODUCT_ID_DEVICE_10_DOLLAR, PRODUCT_NAME_DEVICE_10_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, device10DollarQty, PRODUCT_IMAGES_DEVICE_10_DOLLAR),
                new Product(PRODUCT_ID_DEVICE_20_DOLLAR, PRODUCT_NAME_DEVICE_20_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, device20DollarQty, PRODUCT_IMAGES_DEVICE_20_DOLLAR)
        );
    }
}
