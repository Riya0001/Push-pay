package com.tiptappay.store.app.service.canada;

import com.tiptappay.store.app.dto.canada.CanadaStoreShoppingBag;
import com.tiptappay.store.app.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tiptappay.store.app.constant.StoreConstants.CanadaStore.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CanadaStoreService {

    public final List<Product> products = List.of(
            new Product(PRODUCT_ID_DESKTOP_DISPLAY, PRODUCT_NAME_DESKTOP_DISPLAY, "", FEE_ONE_TIME_IN_CENTS, 0, 0, PRODUCT_IMAGES_DESKTOP_DISPLAY),
            new Product(PRODUCT_ID_DEVICE_5_DOLLAR, PRODUCT_NAME_DEVICE_5_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, 0, PRODUCT_IMAGES_DEVICE_5_DOLLAR),
            new Product(PRODUCT_ID_DEVICE_10_DOLLAR, PRODUCT_NAME_DEVICE_10_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, 0, PRODUCT_IMAGES_DEVICE_10_DOLLAR),
            new Product(PRODUCT_ID_DEVICE_20_DOLLAR, PRODUCT_NAME_DEVICE_20_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, 0, PRODUCT_IMAGES_DEVICE_20_DOLLAR),

            new Product(PRODUCT_ID_3DEVICE_ENGLISH, PRODUCT_NAME_3DEVICE_ENGLISH, "", 0, 0, 0, PRODUCT_IMAGES_3DEVICE_ENGLISH),
            new Product(PRODUCT_ID_3DEVICE_FRENCH, PRODUCT_NAME_3DEVICE_FRENCH, "", 0, 0, 0, PRODUCT_IMAGES_3DEVICE_FRENCH),
            new Product(PRODUCT_ID_3DEVICE_BILINGUAL, PRODUCT_NAME_3DEVICE_BILINGUAL, "", 0, 0, 0, PRODUCT_IMAGES_3DEVICE_BILINGUAL),

            new Product(PRODUCT_ID_SINGLE_ENGLISH, PRODUCT_NAME_SINGLE_ENGLISH, "", 0, 0, 0, PRODUCT_IMAGES_SINGLE_5_ENGLISH),
            new Product(PRODUCT_ID_SINGLE_FRENCH, PRODUCT_NAME_SINGLE_FRENCH, "", 0, 0, 0, PRODUCT_IMAGES_SINGLE_5_FRENCH),
            new Product(PRODUCT_ID_SINGLE_BILINGUAL, PRODUCT_NAME_SINGLE_BILINGUAL, "", 0, 0, 0, PRODUCT_IMAGES_SINGLE_5_BILINGUAL)
    );

    public List<Product> getProductList() {
        return products;
    }

    public List<Product> getProductList(CanadaStoreShoppingBag bag) {
        return List.of(
                new Product(PRODUCT_ID_DESKTOP_DISPLAY, PRODUCT_NAME_DESKTOP_DISPLAY, "", FEE_ONE_TIME_IN_CENTS, 0, bag.getDesktopDisplayQty(), PRODUCT_IMAGES_DESKTOP_DISPLAY),
                new Product(PRODUCT_ID_DEVICE_5_DOLLAR, PRODUCT_NAME_DEVICE_5_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, bag.getDevice5DollarQty(), PRODUCT_IMAGES_DEVICE_5_DOLLAR),
                new Product(PRODUCT_ID_DEVICE_10_DOLLAR, PRODUCT_NAME_DEVICE_10_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, bag.getDevice10DollarQty(), PRODUCT_IMAGES_DEVICE_10_DOLLAR),
                new Product(PRODUCT_ID_DEVICE_20_DOLLAR, PRODUCT_NAME_DEVICE_20_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, bag.getDevice20DollarQty(), PRODUCT_IMAGES_DEVICE_20_DOLLAR),

                new Product(PRODUCT_ID_3DEVICE_ENGLISH, PRODUCT_NAME_3DEVICE_ENGLISH, "", 0, 0, bag.getThreeDeviceDisplayEnglishQuantity(), PRODUCT_IMAGES_3DEVICE_ENGLISH),
                new Product(PRODUCT_ID_3DEVICE_FRENCH, PRODUCT_NAME_3DEVICE_FRENCH, "", 0, 0, bag.getThreeDeviceDisplayFrenchQuantity(), PRODUCT_IMAGES_3DEVICE_FRENCH),
                new Product(PRODUCT_ID_3DEVICE_BILINGUAL, PRODUCT_NAME_3DEVICE_BILINGUAL, "", 0, 0, bag.getThreeDeviceDisplayBilingualQuantity(), PRODUCT_IMAGES_3DEVICE_BILINGUAL),

                new Product(PRODUCT_ID_SINGLE_ENGLISH, PRODUCT_NAME_SINGLE_ENGLISH, "", 0, 0, bag.getSingleCurveEnglishQuantity(), PRODUCT_IMAGES_SINGLE_5_ENGLISH),
                new Product(PRODUCT_ID_SINGLE_FRENCH, PRODUCT_NAME_SINGLE_FRENCH, "", 0, 0, bag.getSingleCurveFrenchQuantity(), PRODUCT_IMAGES_SINGLE_5_FRENCH),
                new Product(PRODUCT_ID_SINGLE_BILINGUAL, PRODUCT_NAME_SINGLE_BILINGUAL, "", 0, 0, bag.getSingleCurveBilingualQuantity(), PRODUCT_IMAGES_SINGLE_5_BILINGUAL)
        );

}}
