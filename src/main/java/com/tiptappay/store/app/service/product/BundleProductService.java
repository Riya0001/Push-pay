package com.tiptappay.store.app.service.product;

import com.tiptappay.store.app.constant.AppConstants;
import com.tiptappay.store.app.dto.bundles.store.CartData;
import com.tiptappay.store.app.dto.bundles.store.CheckoutData;
import com.tiptappay.store.app.dto.bundles.wrapper.BundleOrderWrapper;
import com.tiptappay.store.app.model.payload.Payload;
import com.tiptappay.store.app.service.app.AppService;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.tiptappay.store.app.constant.StoreConstants.BundleStore.*;

/*

Service for Product and Their Prices
Used for Bundles and Benevity Order Form

 */
@Service
public class BundleProductService {
    private final AppService appService;

    public BundleProductService(AppService appService) {
        this.appService = appService;
    }

    public List<CartData> setBundlesPriceKeyAndCalculate(List<CartData> bundles) {
        int KEY_DISCOUNT = 0;

        for (int index = 0; index < bundles.size(); index++) {
            CartData bundle = bundles.get(index);

            if (bundle.getTitle().equals(BUNDLE_STORE_PRODUCT_TYPE_1D)) {
                switch (bundle.getSubscriptionTerm()) {
                    case BUNDLE_STORE_SUBSCRIPTION_TERM_MONTHLY:
                        if (bundle.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_MONTHLY)) {
                            KEY_DISCOUNT++;
                            bundle.setPriceKey(BUNDLE_STORE_CPK_1D1MPM);
                            bundle.setPriceKeyIndex(KEY_DISCOUNT);
                        } else if (bundle.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_PAY_IN_FULL)) {
                            KEY_DISCOUNT++;
                            bundle.setPriceKey(BUNDLE_STORE_CPK_1D1MPY);
                            bundle.setPriceKeyIndex(KEY_DISCOUNT);
                        } else {
                            bundle.setPriceKey(BUNDLE_STORE_CPK_NONE);
                            bundle.setPriceKeyIndex(-1);
                        }
                        break;

                    case BUNDLE_STORE_SUBSCRIPTION_TERM_6_MONTHS:
                        if (bundle.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_MONTHLY_X6)) {
                            KEY_DISCOUNT++;
                            bundle.setPriceKey(BUNDLE_STORE_CPK_1D6MPM);
                            bundle.setPriceKeyIndex(KEY_DISCOUNT);
                        } else if (bundle.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_PAY_IN_FULL)) {
                            KEY_DISCOUNT++;
                            bundle.setPriceKey(BUNDLE_STORE_CPK_1D6MPY);
                            bundle.setPriceKeyIndex(KEY_DISCOUNT);
                        } else {
                            bundle.setPriceKey(BUNDLE_STORE_CPK_NONE);
                            bundle.setPriceKeyIndex(-1);
                        }
                        break;

                    case BUNDLE_STORE_SUBSCRIPTION_TERM_1_YEAR:
                        if (bundle.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_MONTHLY_X12)) {
                            KEY_DISCOUNT++;
                            bundle.setPriceKey(BUNDLE_STORE_CPK_1D1YPM);
                            bundle.setPriceKeyIndex(KEY_DISCOUNT);
                        } else if (bundle.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_PAY_IN_FULL)) {
                            KEY_DISCOUNT++;
                            bundle.setPriceKey(BUNDLE_STORE_CPK_1D1YPY);
                            bundle.setPriceKeyIndex(KEY_DISCOUNT);
                        } else {
                            bundle.setPriceKey(BUNDLE_STORE_CPK_NONE);
                            bundle.setPriceKeyIndex(-1);
                        }
                        break;

                    default:
                        bundle.setPriceKey(BUNDLE_STORE_CPK_NONE);
                        bundle.setPriceKeyIndex(-1);
                }
            }

            if (bundle.getTitle().equals(BUNDLE_STORE_PRODUCT_TYPE_3D)) {
                switch (bundle.getSubscriptionTerm()) {
                    case BUNDLE_STORE_SUBSCRIPTION_TERM_MONTHLY:
                        if (bundle.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_MONTHLY)) {
                            KEY_DISCOUNT++;
                            bundle.setPriceKey(BUNDLE_STORE_CPK_3D1MPM);
                            bundle.setPriceKeyIndex(KEY_DISCOUNT);
                        } else if (bundle.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_PAY_IN_FULL)) {
                            KEY_DISCOUNT++;
                            bundle.setPriceKey(BUNDLE_STORE_CPK_3D1MPY);
                            bundle.setPriceKeyIndex(KEY_DISCOUNT);
                        } else {
                            bundle.setPriceKey(BUNDLE_STORE_CPK_NONE);
                            bundle.setPriceKeyIndex(-1);
                        }
                        break;

                    case BUNDLE_STORE_SUBSCRIPTION_TERM_6_MONTHS:
                        if (bundle.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_MONTHLY_X6)) {
                            KEY_DISCOUNT++;
                            bundle.setPriceKey(BUNDLE_STORE_CPK_3D6MPM);
                            bundle.setPriceKeyIndex(KEY_DISCOUNT);
                        } else if (bundle.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_PAY_IN_FULL)) {
                            KEY_DISCOUNT++;
                            bundle.setPriceKey(BUNDLE_STORE_CPK_3D6MPY);
                            bundle.setPriceKeyIndex(KEY_DISCOUNT);
                        } else {
                            bundle.setPriceKey(BUNDLE_STORE_CPK_NONE);
                            bundle.setPriceKeyIndex(-1);
                        }
                        break;

                    case BUNDLE_STORE_SUBSCRIPTION_TERM_1_YEAR:
                        if (bundle.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_MONTHLY_X12)) {
                            KEY_DISCOUNT++;
                            bundle.setPriceKey(BUNDLE_STORE_CPK_3D1YPM);
                            bundle.setPriceKeyIndex(KEY_DISCOUNT);
                        } else if (bundle.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_PAY_IN_FULL)) {
                            KEY_DISCOUNT++;
                            bundle.setPriceKey(BUNDLE_STORE_CPK_3D1YPY);
                            bundle.setPriceKeyIndex(KEY_DISCOUNT);
                        } else {
                            bundle.setPriceKey(BUNDLE_STORE_CPK_NONE);
                            bundle.setPriceKeyIndex(-1);
                        }
                        break;

                    default:
                        bundle.setPriceKey(BUNDLE_STORE_CPK_NONE);
                        bundle.setPriceKeyIndex(-1);
                }
            }


            if (bundle.getTitle().equals(BUNDLE_STORE_PRODUCT_TYPE_5D)) {
                switch (bundle.getSubscriptionTerm()) {
                    case BUNDLE_STORE_SUBSCRIPTION_TERM_6_MONTHS:
                        if (bundle.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_MONTHLY)) {
                            KEY_DISCOUNT++;
                            bundle.setPriceKey(BUNDLE_STORE_CPK_4D6MPM);
                            bundle.setPriceKeyIndex(KEY_DISCOUNT);
                        } else if (bundle.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_PAY_IN_FULL)) {
                            KEY_DISCOUNT++;
                            bundle.setPriceKey(BUNDLE_STORE_CPK_4D6MPY);
                            bundle.setPriceKeyIndex(KEY_DISCOUNT);
                        } else {
                            bundle.setPriceKey(BUNDLE_STORE_CPK_NONE);
                            bundle.setPriceKeyIndex(-1);
                        }
                        break;

                    case BUNDLE_STORE_SUBSCRIPTION_TERM_1_YEAR:
                        if (bundle.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_MONTHLY)) {
                            KEY_DISCOUNT++;
                            bundle.setPriceKey(BUNDLE_STORE_CPK_4D1YPM);
                            bundle.setPriceKeyIndex(KEY_DISCOUNT);
                        } else if (bundle.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_PAY_IN_FULL)) {
                            KEY_DISCOUNT++;
                            bundle.setPriceKey(BUNDLE_STORE_CPK_4D1YPY);
                            bundle.setPriceKeyIndex(KEY_DISCOUNT);
                        } else {
                            bundle.setPriceKey(BUNDLE_STORE_CPK_NONE);
                            bundle.setPriceKeyIndex(-1);
                        }
                        break;

                    default:
                        bundle.setPriceKey(BUNDLE_STORE_CPK_NONE);
                        bundle.setPriceKeyIndex(-1);
                }
            }


            // Update price calculations directly in the bundle list
            calculatePrice(index, bundles);
        }

        return bundles; // returning modified bundles list
    }

    private void calculatePrice(int index, List<CartData> bundles) {
        CartData bundle = bundles.get(index);

        switch (bundle.getPriceKey()) {
            case BUNDLE_STORE_CPK_1D1MPM:
                if (bundle.getPriceKeyIndex() == 1) {
                    bundle.setBundlePrice(33900);
                    bundle.setFirstMonthPayment(33900);
                    bundle.setSecondMonthAndBeyond(3500);
                    bundle.setPayInFull((12 * 3500) + 33900);
                } else {
                    bundle.setBundlePrice(13900);
                    bundle.setFirstMonthPayment(13900);
                    bundle.setSecondMonthAndBeyond(3500);
                    bundle.setPayInFull((12 * 3500) + 13900);
                }
                break;

            case BUNDLE_STORE_CPK_1D1MPY:
                if (bundle.getPriceKeyIndex() == 1) {
                    bundle.setBundlePrice(33900);
                    bundle.setFirstMonthPayment((12 * 3500) + 33900);
                    bundle.setSecondMonthAndBeyond(0);
                    bundle.setPayInFull((12 * 3500) + 33900);
                } else {
                    bundle.setBundlePrice(13900);
                    bundle.setFirstMonthPayment((12 * 3500) + 13900);
                    bundle.setSecondMonthAndBeyond(0);
                    bundle.setPayInFull((12 * 3500) + 13900);
                }
                break;

            case BUNDLE_STORE_CPK_1D6MPM:
                if (bundle.getPriceKeyIndex() == 1) {
                    bundle.setBundlePrice(5900);
                    bundle.setFirstMonthPayment(5900);
                    bundle.setSecondMonthAndBeyond(5900);
                    bundle.setPayInFull(32900);
                } else {
                    bundle.setBundlePrice(3900);
                    bundle.setFirstMonthPayment(3900);
                    bundle.setSecondMonthAndBeyond(3900);
                    bundle.setPayInFull(22900);
                }
                break;

            case BUNDLE_STORE_CPK_1D6MPY:
                if (bundle.getPriceKeyIndex() == 1) {
                    bundle.setBundlePrice(5900);
                    bundle.setFirstMonthPayment(32900);
                    bundle.setSecondMonthAndBeyond(0);
                    bundle.setPayInFull(32900);
                } else {
                    bundle.setBundlePrice(3900);
                    bundle.setFirstMonthPayment(22900);
                    bundle.setSecondMonthAndBeyond(0);
                    bundle.setPayInFull(22900);
                }
                break;

            case BUNDLE_STORE_CPK_1D1YPM:
                if (bundle.getPriceKeyIndex() == 1) {
                    bundle.setBundlePrice(3900);
                    bundle.setFirstMonthPayment(3900);
                    bundle.setSecondMonthAndBeyond(3900);
                    bundle.setPayInFull(43500);
                } else {
                    bundle.setBundlePrice(2900);
                    bundle.setFirstMonthPayment(2900);
                    bundle.setSecondMonthAndBeyond(2900);
                    bundle.setPayInFull(33500);
                }
                break;

            case BUNDLE_STORE_CPK_1D1YPY:
                if (bundle.getPriceKeyIndex() == 1) {
                    bundle.setBundlePrice(3900);
                    bundle.setFirstMonthPayment(43500);
                    bundle.setSecondMonthAndBeyond(0);
                    bundle.setPayInFull(43500);
                } else {
                    bundle.setBundlePrice(2900);
                    bundle.setFirstMonthPayment(33500);
                    bundle.setSecondMonthAndBeyond(0);
                    bundle.setPayInFull(33500);
                }
                break;

            case BUNDLE_STORE_CPK_3D1MPM:
                if (bundle.getPriceKeyIndex() == 1) {
                    bundle.setBundlePrice(51900);
                    bundle.setFirstMonthPayment(51900);
                    bundle.setSecondMonthAndBeyond(10500);
                    bundle.setPayInFull((12 * 10500) + 51900);
                } else {
                    bundle.setBundlePrice(31900);
                    bundle.setFirstMonthPayment(31900);
                    bundle.setSecondMonthAndBeyond(10500);
                    bundle.setPayInFull((12 * 10500) + 31900);
                }
                break;

            case BUNDLE_STORE_CPK_3D1MPY:
                if (bundle.getPriceKeyIndex() == 1) {
                    bundle.setBundlePrice(51900);
                    bundle.setFirstMonthPayment((12 * 10500) + 51900);
                    bundle.setSecondMonthAndBeyond(0);
                    bundle.setPayInFull((12 * 10500) + 51900);
                } else {
                    bundle.setBundlePrice(31900);
                    bundle.setFirstMonthPayment((12 * 10500) + 31900);
                    bundle.setSecondMonthAndBeyond(0);
                    bundle.setPayInFull((12 * 10500) + 31900);
                }
                break;

            case BUNDLE_STORE_CPK_3D6MPM:
                if (bundle.getPriceKeyIndex() == 1) {
                    bundle.setBundlePrice(13900);
                    bundle.setFirstMonthPayment(13900);
                    bundle.setSecondMonthAndBeyond(13900);
                    bundle.setPayInFull(77500);
                } else {
                    bundle.setBundlePrice(11900);
                    bundle.setFirstMonthPayment(11900);
                    bundle.setSecondMonthAndBeyond(11900);
                    bundle.setPayInFull(67500);
                }
                break;

            case BUNDLE_STORE_CPK_3D6MPY:
                if (bundle.getPriceKeyIndex() == 1) {
                    bundle.setBundlePrice(13900);
                    bundle.setFirstMonthPayment(77500);
                    bundle.setSecondMonthAndBeyond(0);
                    bundle.setPayInFull(77500);
                } else {
                    bundle.setBundlePrice(11900);
                    bundle.setFirstMonthPayment(67500);
                    bundle.setSecondMonthAndBeyond(0);
                    bundle.setPayInFull(67500);
                }
                break;

            case BUNDLE_STORE_CPK_3D1YPM:
                if (bundle.getPriceKeyIndex() == 1) {
                    bundle.setBundlePrice(9900);
                    bundle.setFirstMonthPayment(9900);
                    bundle.setSecondMonthAndBeyond(9900);
                    bundle.setPayInFull(110000);
                } else {
                    bundle.setBundlePrice(8900);
                    bundle.setFirstMonthPayment(8900);
                    bundle.setSecondMonthAndBeyond(8900);
                    bundle.setPayInFull(99900);
                }
                break;

            case BUNDLE_STORE_CPK_3D1YPY:
                if (bundle.getPriceKeyIndex() == 1) {
                    bundle.setBundlePrice(9900);
                    bundle.setFirstMonthPayment(110000);
                    bundle.setSecondMonthAndBeyond(0);
                    bundle.setPayInFull(110000);
                } else {
                    bundle.setBundlePrice(8900);
                    bundle.setFirstMonthPayment(99900);
                    bundle.setSecondMonthAndBeyond(0);
                    bundle.setPayInFull(99900);
                }
                break;

            case BUNDLE_STORE_CPK_4D6MPM:
                bundle.setBundlePrice(13900);
                bundle.setFirstMonthPayment(13900);
                bundle.setSecondMonthAndBeyond(13900);
                bundle.setPayInFull(166800);
                break;

            case BUNDLE_STORE_CPK_4D6MPY:
                bundle.setBundlePrice(79500);
                bundle.setFirstMonthPayment(79500);
                bundle.setSecondMonthAndBeyond(0);
                bundle.setPayInFull(79500);
                break;

            case BUNDLE_STORE_CPK_4D1YPM:
                bundle.setBundlePrice(10900);
                bundle.setFirstMonthPayment(10900);
                bundle.setSecondMonthAndBeyond(10900);
                bundle.setPayInFull(65400);
                break;

            case BUNDLE_STORE_CPK_4D1YPY:
                bundle.setBundlePrice(124500);
                bundle.setFirstMonthPayment(124500);
                bundle.setSecondMonthAndBeyond(0);
                bundle.setPayInFull(124500);
                break;

            default:
                bundle.setBundlePrice(0);
                bundle.setFirstMonthPayment(0);
                bundle.setSecondMonthAndBeyond(0);
                bundle.setPayInFull(0);
        }
    }

    public String getPriceLevel(CartData cartData) {
        String priceLevel = "-1"; // Custom

        if (cartData == null) {
            return priceLevel;
        }

        switch (cartData.getSubscriptionTerm()) {
            case BUNDLE_STORE_SUBSCRIPTION_TERM_MONTHLY: {
                if (cartData.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_MONTHLY)) {
                    priceLevel = BUNDLE_STORE_NS_PRICE_LEVEL_INITIAL_CMP_MONTHLY_RENTAL;
                }
                break;
            }
            case BUNDLE_STORE_SUBSCRIPTION_TERM_6_MONTHS: {
                if (cartData.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_MONTHLY)) {
                    priceLevel = BUNDLE_STORE_NS_PRICE_LEVEL_6_MONTHS_SUBS;
                }
                if (cartData.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_MONTHLY_X6)) {
                    priceLevel = BUNDLE_STORE_NS_PRICE_LEVEL_6_MONTHS_SUBS;
                }
                if (cartData.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_PAY_IN_FULL)) {
                    priceLevel = BUNDLE_STORE_NS_PRICE_LEVEL_6_MONTHS_SUBS_PREPAID;
                }
                break;
            }
            case BUNDLE_STORE_SUBSCRIPTION_TERM_1_YEAR: {
                if (cartData.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_MONTHLY)) {
                    priceLevel = BUNDLE_STORE_NS_PRICE_LEVEL_1_YEAR_SUBS;
                }
                if (cartData.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_MONTHLY_X12)) {
                    priceLevel = BUNDLE_STORE_NS_PRICE_LEVEL_1_YEAR_SUBS;
                }
                if (cartData.getPaymentOption().equals(BUNDLE_STORE_PAYMENT_OPTION_PAY_IN_FULL)) {
                    priceLevel = BUNDLE_STORE_NS_PRICE_LEVEL_1_YEAR_SUBS_PREPAID;
                }
                break;
            }
            default: {
                break;
            }
        }

        return priceLevel;
    }

    public BundleOrderWrapper getOrderDetails(List<CartData> cartDataList) {
        BundleOrderWrapper bundleOrderWrapper = new BundleOrderWrapper();
        Payload.OrderDetails orderDetails = new Payload.OrderDetails();

        int totalNumberOf2DollarDevices = 0;
        int totalNumberOf5DollarDevices = 0;
        int totalNumberOf10DollarDevices = 0;
        int totalNumberOf20DollarDevices = 0;
        int totalNumberOf50DollarDevices = 0;

        List<Payload.BundleOrder> bundleOrders = new ArrayList<>();

        for (CartData cartData : cartDataList) {

            if (cartData.getTitle().equalsIgnoreCase(BUNDLE_STORE_PRODUCT_TYPE_1D)) {
                int qtyOf2dollarDevice = cartData.getDeviceDenomination().equals("$2") ? 1 : 0;
                int qtyOf5dollarDevice = cartData.getDeviceDenomination().equals("$5") ? 1 : 0;
                int qtyOf10dollarDevice = cartData.getDeviceDenomination().equals("$10") ? 1 : 0;
                int qtyOf20dollarDevice = cartData.getDeviceDenomination().equals("$20") ? 1 : 0;
                int qtyOf50dollarDevice = cartData.getDeviceDenomination().equals("$50") ? 1 : 0;

                if (cartData.getSolutionSelection().equals(BUNDLE_STORE_PRODUCT_MINI_CLIP)) {
                    Payload.BundleOrder bundleOrder = new Payload.BundleOrder(
                            BUNDLE_STORE_PRODUCT_MINI_CLIP_ID, "1", getPriceLevel(cartData), priceFormat(cartData.getFirstMonthPayment())
                    );
                    bundleOrders.add(bundleOrder);
                }

                if (cartData.getSolutionSelection().equals(BUNDLE_STORE_PRODUCT_MEDIUM_COUNTER_DISPLAY)) {
                    Payload.BundleOrder bundleOrder = new Payload.BundleOrder(
                            BUNDLE_STORE_PRODUCT_MEDIUM_COUNTER_DISPLAY_ID, "1", getPriceLevel(cartData), priceFormat(cartData.getFirstMonthPayment())
                    );
                    bundleOrders.add(bundleOrder);
                }

                totalNumberOf2DollarDevices += qtyOf2dollarDevice;
                totalNumberOf5DollarDevices += qtyOf5dollarDevice;
                totalNumberOf10DollarDevices += qtyOf10dollarDevice;
                totalNumberOf20DollarDevices += qtyOf20dollarDevice;
                totalNumberOf50DollarDevices += qtyOf50dollarDevice;
            }

            if (cartData.getTitle().equalsIgnoreCase(BUNDLE_STORE_PRODUCT_TYPE_3D)) {
                int qtyOf2510dollarDevice = cartData.getDeviceDenomination().equals("$2 - $5 - $10") ? 1 : 0;
                int qtyOf51020dollarDevice = cartData.getDeviceDenomination().equals("$5 - $10 - $20") ? 1 : 0;
                int qtyOf102050dollarDevice = cartData.getDeviceDenomination().equals("$10 - $20 - $50") ? 1 : 0;

                if (cartData.getSolutionSelection().equals(BUNDLE_STORE_PRODUCT_LARGE_COUNTER_DISPLAY)) {
                    Payload.BundleOrder bundleOrder = new Payload.BundleOrder(
                            BUNDLE_STORE_PRODUCT_LARGE_COUNTER_DISPLAY_ID, "1", getPriceLevel(cartData), priceFormat(cartData.getFirstMonthPayment())
                    );
                    bundleOrders.add(bundleOrder);
                }

                if (cartData.getSolutionSelection().equals(BUNDLE_STORE_PRODUCT_FRAMED_FLOOR_STAND)) {

                    String framedFloorStandID = (appService.getNetsuiteEnvironment().equals(AppConstants.NetsuiteEnvironmentOptions.PRODUCTION)) ?
                            BUNDLE_STORE_PRODUCT_FRAMED_FLOOR_STAND_ID : BUNDLE_STORE_PRODUCT_FRAMED_FLOOR_STAND_ID_SANDBOX;

                    Payload.BundleOrder bundleOrder = new Payload.BundleOrder(
                            framedFloorStandID, "1", getPriceLevel(cartData), priceFormat(cartData.getFirstMonthPayment())
                    );
                    bundleOrders.add(bundleOrder);
                }

                totalNumberOf2DollarDevices += (qtyOf2510dollarDevice);
                totalNumberOf5DollarDevices += (qtyOf2510dollarDevice + qtyOf51020dollarDevice);
                totalNumberOf10DollarDevices += (qtyOf51020dollarDevice + qtyOf102050dollarDevice);
                totalNumberOf20DollarDevices += (qtyOf51020dollarDevice + qtyOf102050dollarDevice);
                totalNumberOf50DollarDevices += (qtyOf102050dollarDevice);
            }

            if (cartData.getTitle().equalsIgnoreCase(BUNDLE_STORE_PRODUCT_TYPE_5D)) {
                String[] prices = cartData.getDeviceDenomination().split(" and ");

                int qtyOf51020dollarDevice = prices[0].equals("$5 - $10 - $20") ? 1 : 0;
                int qtyOf102050dollarDevice = prices[0].equals("$10 - $20 - $50") ? 1 : 0;
                int qtyOf2dollarDevice = prices[0].equals("$2") ? 1 : 0;
                int qtyOf5dollarDevice = prices[0].equals("$5") ? 1 : 0;
                int qtyOf10dollarDevice = prices[0].equals("$10") ? 1 : 0;
                int qtyOf20dollarDevice = prices[0].equals("$20") ? 1 : 0;
                int qtyOf50dollarDevice = prices[0].equals("$50") ? 1 : 0;

                qtyOf51020dollarDevice += prices[1].equals("$5 - $10 - $20") ? 1 : 0;
                qtyOf102050dollarDevice += prices[1].equals("$10 - $20 - $50") ? 1 : 0;
                qtyOf2dollarDevice += prices[1].equals("$2") ? 1 : 0;
                qtyOf5dollarDevice += prices[1].equals("$5") ? 1 : 0;
                qtyOf10dollarDevice += prices[1].equals("$10") ? 1 : 0;
                qtyOf20dollarDevice += prices[1].equals("$20") ? 1 : 0;
                qtyOf50dollarDevice += prices[1].equals("$50") ? 1 : 0;

                if (cartData.getSolutionSelection().equals(BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_FAITH)) {
                    String benevityBundleFaithID = (appService.getNetsuiteEnvironment().equals(AppConstants.NetsuiteEnvironmentOptions.PRODUCTION)) ?
                            BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_FAITH_ID : BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_FAITH_ID_SANDBOX;

                    Payload.BundleOrder bundleOrder = new Payload.BundleOrder(
                            benevityBundleFaithID, "1", getPriceLevel(cartData), priceFormat(cartData.getFirstMonthPayment())
                    );
                    bundleOrders.add(bundleOrder);
                }

                if (cartData.getSolutionSelection().equals(BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_EDUCATION)) {
                    String benevityBundleEducationID = (appService.getNetsuiteEnvironment().equals(AppConstants.NetsuiteEnvironmentOptions.PRODUCTION)) ?
                            BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_EDUCATION_ID : BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_EDUCATION_ID_SANDBOX;

                    Payload.BundleOrder bundleOrder = new Payload.BundleOrder(
                            benevityBundleEducationID, "1", getPriceLevel(cartData), priceFormat(cartData.getFirstMonthPayment())
                    );
                    bundleOrders.add(bundleOrder);
                }

                if (cartData.getSolutionSelection().equals(BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_NONPROFIT)) {
                    String benevityBundleNonprofitID = (appService.getNetsuiteEnvironment().equals(AppConstants.NetsuiteEnvironmentOptions.PRODUCTION)) ?
                            BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_NONPROFIT_ID : BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_NONPROFIT_ID_SANDBOX;

                    Payload.BundleOrder bundleOrder = new Payload.BundleOrder(
                            benevityBundleNonprofitID, "1", getPriceLevel(cartData), priceFormat(cartData.getFirstMonthPayment())
                    );
                    bundleOrders.add(bundleOrder);
                }


                totalNumberOf2DollarDevices += (qtyOf2dollarDevice);
                totalNumberOf5DollarDevices += (qtyOf5dollarDevice + qtyOf51020dollarDevice);
                totalNumberOf10DollarDevices += (qtyOf10dollarDevice + qtyOf51020dollarDevice + qtyOf102050dollarDevice);
                totalNumberOf20DollarDevices += (qtyOf20dollarDevice + qtyOf51020dollarDevice + qtyOf102050dollarDevice);
                totalNumberOf50DollarDevices += (qtyOf50dollarDevice + qtyOf102050dollarDevice);
            }

        }

        orderDetails.setNumberOf2DollarDevices(String.valueOf(totalNumberOf2DollarDevices));
        orderDetails.setNumberOf5DollarDevices(String.valueOf(totalNumberOf5DollarDevices));
        orderDetails.setNumberOf10DollarDevices(String.valueOf(totalNumberOf10DollarDevices));
        orderDetails.setNumberOf20DollarDevices(String.valueOf(totalNumberOf20DollarDevices));
        orderDetails.setNumberOf50DollarDevices(String.valueOf(totalNumberOf50DollarDevices));
        orderDetails.setNumberOfFloorStands("0");
        orderDetails.setNumberOfClips("0");
        orderDetails.setNumberOfKettleDisplay("0");
        orderDetails.setNumberOfBatteryForKettleDisplay("0");


        bundleOrderWrapper.setOrderDetails(orderDetails);
        bundleOrderWrapper.setBundleOrders(groupOrders(bundleOrders));

        return bundleOrderWrapper;
    }

    // Added default values
    public Payload.Pricing getPricing(CheckoutData checkoutData) {

        Payload.Pricing payloadPricing = new Payload.Pricing();

        payloadPricing.setOverrideSetupFee(true);
        payloadPricing.setOverrideMonthlyDeviceRentalFee(true);

        payloadPricing.setCurrency(checkoutData.getShippingCountry().equalsIgnoreCase("canada") ? "CAD" : "USD");
        payloadPricing.setSetupFeeAmount(0);
        payloadPricing.setMonthlyDeviceRentalFeeAmount(0);

        return payloadPricing;
    }

    private String priceFormat(int priceInCents) {
        return String.format("%.2f", priceInCents / 100.0);
    }

    private List<Payload.BundleOrder> groupOrders(List<Payload.BundleOrder> bundleOrderList) {
        if (bundleOrderList == null || bundleOrderList.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, Payload.BundleOrder> combinedMap = new HashMap<>();

        for (Payload.BundleOrder item : bundleOrderList) {
            String key = item.getId() + "-" + item.getPriceLevel() + "-" + item.getRate();

            if (combinedMap.containsKey(key)) {
                Payload.BundleOrder existingItem = combinedMap.get(key);
                int newQuantity = Integer.parseInt(existingItem.getQuantity()) + Integer.parseInt(item.getQuantity());
                existingItem.setQuantity(String.valueOf(newQuantity));
            } else {
                combinedMap.put(key, new Payload.BundleOrder(item.getId(), item.getQuantity(), item.getPriceLevel(), item.getRate()));
            }
        }

        return new ArrayList<>(combinedMap.values());
    }
}
