package com.tiptappay.store.app.constant;

import java.util.List;

public class StoreConstants {

    public static final class CanadaStore {
        private CanadaStore() {
        }

        public static final String STORE_ID = "S04";

        // Customer Info
        public static final String CUSTOMER_TYPE = "Enterprise";
        public static final String CUSTOMER_REFERRAL = "";

        // Organization Info
        public static final int ORGANIZATION_CHILD_SB = 1398;
        public static final int ORGANIZATION_CHILD_PROD = 1398;

        // Currency
        public static final String CURRENCY = "CAD";

        // Fees
        public static final int FEE_ONE_TIME_IN_CENTS = 0;
        public static final int FEE_RENTAL_IN_CENTS = 0;
        public static final int FEE_RENTAL_IN_CENTS_2 = 0;
        public static final int FEE_SETUP_IN_CENTS = 0;

        // Product IDs
        public static final String PRODUCT_ID_DESKTOP_DISPLAY = "desktopDisplay";
        public static final String PRODUCT_ID_DEVICE_5_DOLLAR = "device5Dollar";
        public static final String PRODUCT_ID_DEVICE_10_DOLLAR = "device10Dollar";
        public static final String PRODUCT_ID_DEVICE_20_DOLLAR = "device20Dollar";

        public static final String PRODUCT_ID_3DEVICE_ENGLISH = "threeDeviceDisplayEnglish";
        public static final String PRODUCT_ID_3DEVICE_FRENCH = "threeDeviceDisplayFrench";
        public static final String PRODUCT_ID_3DEVICE_BILINGUAL = "threeDeviceDisplayBilingual";

        public static final String PRODUCT_ID_SINGLE_ENGLISH = "singleCurveEnglish";
        public static final String PRODUCT_ID_SINGLE_FRENCH = "singleCurveFrench";
        public static final String PRODUCT_ID_SINGLE_BILINGUAL = "singleCurveBilingual";

        // Product Names
        public static final String PRODUCT_NAME_DESKTOP_DISPLAY = "desktop display";
        public static final String PRODUCT_NAME_DEVICE_5_DOLLAR = "$5 tiptap device";
        public static final String PRODUCT_NAME_DEVICE_10_DOLLAR = "$10 tiptap device";
        public static final String PRODUCT_NAME_DEVICE_20_DOLLAR = "$20 tiptap device";

        public static final String PRODUCT_NAME_3DEVICE_ENGLISH = "Display Kit for Kettlestand (3 Devices) - ENG";
        public static final String PRODUCT_NAME_3DEVICE_FRENCH = "Display Kit for Kettlestand (3 Devices) - FR";
        public static final String PRODUCT_NAME_3DEVICE_BILINGUAL = "Display Kit for Kettlestand (3 Devices) - BL";

        public static final String PRODUCT_NAME_SINGLE_ENGLISH = "Counter Display (1 Device) - ENG";
        public static final String PRODUCT_NAME_SINGLE_FRENCH = "Counter Display (1 Device) - FR";
        public static final String PRODUCT_NAME_SINGLE_BILINGUAL = "Counter Display (1 Device) - BL";

        // Product Images
        public static final List<String> PRODUCT_IMAGES_DESKTOP_DISPLAY = List.of("/images/desktop_display.png");
        public static final List<String> PRODUCT_IMAGES_DEVICE_5_DOLLAR = List.of("/images/dollar5-device.png");
        public static final List<String> PRODUCT_IMAGES_DEVICE_10_DOLLAR = List.of("/images/dollar10-device.png");
        public static final List<String> PRODUCT_IMAGES_DEVICE_20_DOLLAR = List.of("/images/dollar20-device.png");

        public static final List<String> PRODUCT_IMAGES_3DEVICE_ENGLISH = List.of("/images/3device_display_eng.png");
        public static final List<String> PRODUCT_IMAGES_3DEVICE_FRENCH = List.of("/images/3device_display_fr.png");
        public static final List<String> PRODUCT_IMAGES_3DEVICE_BILINGUAL = List.of("/images/3device_display_bl.png");

        public static final List<String> PRODUCT_IMAGES_SINGLE_5_ENGLISH = List.of("/images/single5_eng.png");
        public static final List<String> PRODUCT_IMAGES_SINGLE_5_FRENCH = List.of("/images/single5_fr.png");
        public static final List<String> PRODUCT_IMAGES_SINGLE_5_BILINGUAL = List.of("/images/single5_bl.png");
    }

    public static final class USStore {
        private USStore() {
        }

        public static final String STORE_ID = "S01";

        // Customer Info
        public static final String CUSTOMER_TYPE = "Enterprise";
        public static final String CUSTOMER_REFERRAL = "";

        // Organization Info
        public static final int ORGANIZATION_PARENT_SB = 35891;
        public static final int ORGANIZATION_PARENT_PROD = 35973;
        public static final int ORGANIZATION_CHILD_SB = 35892;
        public static final int ORGANIZATION_CHILD_PROD = 35974;

        // Currency
        public static final String CURRENCY = "USD";

        // Fees
        public static final int FEE_ONE_TIME_IN_CENTS = 4500;
        public static final int FEE_RENTAL_IN_CENTS = 2500;
        public static final int FEE_RENTAL_IN_CENTS_2 = 0;
        public static final int FEE_SETUP_IN_CENTS = 9900;

        // Product IDs
        public static final String PRODUCT_ID_MINI_CLIP_KIT = "miniClipKit";
        public static final String PRODUCT_ID_DEVICE_5_DOLLAR = "device5Dollar";
        public static final String PRODUCT_ID_DEVICE_10_DOLLAR = "device10Dollar";
        public static final String PRODUCT_ID_DEVICE_20_DOLLAR = "device20Dollar";

        // Product Names
        public static final String PRODUCT_NAME_MINI_CLIP_KIT = "mini clip kit";
        public static final String PRODUCT_NAME_DEVICE_5_DOLLAR = "$5 tiptap device";
        public static final String PRODUCT_NAME_DEVICE_10_DOLLAR = "$10 tiptap device";
        public static final String PRODUCT_NAME_DEVICE_20_DOLLAR = "$20 tiptap device";

        // Product Images
        public static final List<String> PRODUCT_IMAGES_MINI_CLIP_KIT = List.of("/images/clip.png");
        public static final List<String> PRODUCT_IMAGES_DEVICE_5_DOLLAR = List.of("/images/dollar5-device.png");
        public static final List<String> PRODUCT_IMAGES_DEVICE_10_DOLLAR = List.of("/images/dollar10-device.png");
        public static final List<String> PRODUCT_IMAGES_DEVICE_20_DOLLAR = List.of("/images/dollar20-device.png");

    }

    public static final class MosquesStore {
        private MosquesStore() {
        }

        public static final String STORE_ID = "S02";

        // Customer Info
        public static final String CUSTOMER_TYPE = "Referral";
        public static final String CUSTOMER_REFERRAL = "Rizek, Tariq";

        // Organization Info
        public static final int ORGANIZATION_PARENT_SB = 0;
        public static final int ORGANIZATION_PARENT_PROD = 0;
        public static final int ORGANIZATION_CHILD_SB = 0;
        public static final int ORGANIZATION_CHILD_PROD = 0;

        // Currency
        public static final String CURRENCY = "CAD";

        // Fees
        public static final int FEE_ONE_TIME_IN_CENTS = 15000;
        public static final int FEE_RENTAL_IN_CENTS = 2500;
        public static final int FEE_RENTAL_IN_CENTS_2 = 0;
        public static final int FEE_SETUP_IN_CENTS = 15000;

        // Product IDs
        public static final String PRODUCT_ID_FLOOR_STAND = "floorStand";
        public static final String PRODUCT_ID_DEVICE_5_DOLLAR = "device5Dollar";
        public static final String PRODUCT_ID_DEVICE_10_DOLLAR = "device10Dollar";
        public static final String PRODUCT_ID_DEVICE_20_DOLLAR = "device20Dollar";
        public static final String PRODUCT_ID_BATTERY_FOR_FLOOR_STAND = "batteryForFloorStand";

        // Product Names
        public static final String PRODUCT_NAME_FLOOR_STAND = "floor stand display kit";
        public static final String PRODUCT_NAME_DEVICE_5_DOLLAR = "$5 tiptap device";
        public static final String PRODUCT_NAME_DEVICE_10_DOLLAR = "$10 tiptap device";
        public static final String PRODUCT_NAME_DEVICE_20_DOLLAR = "$20 tiptap device";
        public static final String PRODUCT_NAME_BATTERY_FOR_FLOOR_STAND = "battery";

        // Product Images
        public static final List<String> PRODUCT_IMAGES_FLOOR_STAND = List.of("/images/floor_stand.png");
        public static final List<String> PRODUCT_IMAGES_DEVICE_5_DOLLAR = List.of("/images/dollar5-device.png");
        public static final List<String> PRODUCT_IMAGES_DEVICE_10_DOLLAR = List.of("/images/dollar10-device.png");
        public static final List<String> PRODUCT_IMAGES_DEVICE_20_DOLLAR = List.of("/images/dollar20-device.png");
        public static final List<String> PRODUCT_IMAGES_BATTERY_FOR_FLOOR_STAND = List.of("/images/no_image_available.png");

    }
    public static final class LegionStore {
        private LegionStore() {
        }

        public static final String STORE_ID = "S05";

        // Customer Info
        public static final String CUSTOMER_TYPE = "Enterprise";
        public static final String CUSTOMER_REFERRAL = "";

        // Organization Info
        public static final int ORGANIZATION_CHILD_SB = 1400;
        public static final int ORGANIZATION_CHILD_PROD = 1400;

        // Currency
        public static final String CURRENCY = "CAD";

        // Fees
        public static final int FEE_ONE_TIME_IN_CENTS = 0;
        public static final int FEE_RENTAL_IN_CENTS = 0;
        public static final int FEE_RENTAL_IN_CENTS_2 = 0;
        public static final int FEE_SETUP_IN_CENTS = 0;

        // Product IDs
        public static final String PRODUCT_ID_POPPYBOX_3DEVICE = "poppybox3Device";
        public static final String PRODUCT_ID_DEVICE_5_DOLLAR = "device5Dollar";
        public static final String PRODUCT_ID_DEVICE_10_DOLLAR = "device10Dollar";
        public static final String PRODUCT_ID_DEVICE_20_DOLLAR = "device20Dollar";

        // Product Names
        public static final String PRODUCT_NAME_POPPYBOX_3DEVICE = "Poppybox Display Kit (3 Devices)";
        public static final String PRODUCT_NAME_DEVICE_5_DOLLAR = "$5 tiptap device";
        public static final String PRODUCT_NAME_DEVICE_10_DOLLAR = "$10 tiptap device";
        public static final String PRODUCT_NAME_DEVICE_20_DOLLAR = "$20 tiptap device";

        // Product Images
        public static final List<String> PRODUCT_IMAGES_POPPYBOX_3DEVICE = List.of("/images/poppybox_display.png");
        public static final List<String> PRODUCT_IMAGES_DEVICE_5_DOLLAR = List.of("/images/dollar5-device.png");
        public static final List<String> PRODUCT_IMAGES_DEVICE_10_DOLLAR = List.of("/images/dollar10-device.png");
        public static final List<String> PRODUCT_IMAGES_DEVICE_20_DOLLAR = List.of("/images/dollar20-device.png");
    }


    // Salvation Army Canada Store
    public static final class SaUsStore {
        private SaUsStore() {
        }

        public static final String STORE_ID = "S03";

        // Customer Info
        public static final String CUSTOMER_TYPE = "Enterprise";
        public static final String CUSTOMER_REFERRAL = "";

        // Organization Info
        public static final int ORGANIZATION_PARENT_SB = 35891;
        public static final int ORGANIZATION_PARENT_PROD = 35973;
        public static final int ORGANIZATION_CHILD_SB = 35892;
        public static final int ORGANIZATION_CHILD_PROD = 35974;

        // Currency
        public static final String CURRENCY = "USD";

        // Fees
        public static final int FEE_ONE_TIME_IN_CENTS = 3000;
        public static final int FEE_RENTAL_IN_CENTS = 6000;
        public static final int FEE_RENTAL_IN_CENTS_2 = 1000;
        public static final int FEE_SETUP_IN_CENTS = 0;

        // Product IDs
        public static final String PRODUCT_ID_KETTLE_DISPLAY = "kettleDisplay";
        public static final String PRODUCT_ID_DEVICE_5_DOLLAR = "device5Dollar";
        public static final String PRODUCT_ID_DEVICE_10_DOLLAR = "device10Dollar";
        public static final String PRODUCT_ID_DEVICE_20_DOLLAR = "device20Dollar";
        public static final String PRODUCT_ID_BATTERY_FOR_KETTLE_DISPLAY = "batteryForKettleDisplay";

        // Product Names
        public static final String PRODUCT_NAME_KETTLE_DISPLAY = "kettle display";
        public static final String PRODUCT_NAME_DEVICE_5_DOLLAR = "$5 tiptap device";
        public static final String PRODUCT_NAME_DEVICE_10_DOLLAR = "$10 tiptap device";
        public static final String PRODUCT_NAME_DEVICE_20_DOLLAR = "$20 tiptap device";
        public static final String PRODUCT_NAME_BATTERY_FOR_KETTLE_DISPLAY = "battery";

        // Product Images
        public static final List<String> PRODUCT_IMAGES_KETTLE_DISPLAY = List.of("/images/kettle_display.png");
        public static final List<String> PRODUCT_IMAGES_DEVICE_5_DOLLAR = List.of("/images/dollar5-device.png");
        public static final List<String> PRODUCT_IMAGES_DEVICE_10_DOLLAR = List.of("/images/dollar10-device.png");
        public static final List<String> PRODUCT_IMAGES_DEVICE_20_DOLLAR = List.of("/images/dollar20-device.png");
        public static final List<String> PRODUCT_IMAGES_BATTERY_FOR_KETTLE_DISPLAY = List.of("/images/powerPack.png");

    }

    public static final class BundleStore {
        private BundleStore() {}

        // Product Types
        public static final String BUNDLE_STORE_PRODUCT_TYPE_1D = "1 Device Bundle";
        public static final String BUNDLE_STORE_PRODUCT_TYPE_3D = "3 Devices Bundle";
        public static final String BUNDLE_STORE_PRODUCT_TYPE_5D = "5 Device Bundle";

        // Products
        public static final String BUNDLE_STORE_PRODUCT_MINI_CLIP = "Mini Clip";
        public static final String BUNDLE_STORE_PRODUCT_MEDIUM_COUNTER_DISPLAY = "Medium Counter Display";
        public static final String BUNDLE_STORE_PRODUCT_LARGE_COUNTER_DISPLAY = "Large Counter Display";
        public static final String BUNDLE_STORE_PRODUCT_FRAMED_FLOOR_STAND = "Framed Floorstand";
        public static final String BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_FAITH = "Premium Benevity Bundle - Faith";
        public static final String BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_EDUCATION = "Premium Benevity Bundle - Education";
        public static final String BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_NONPROFIT = "Premium Benevity Bundle - Nonprofit";

        // Products Netsuite IDs
        public static final String BUNDLE_STORE_PRODUCT_MINI_CLIP_ID = "2011";
        public static final String BUNDLE_STORE_PRODUCT_MEDIUM_COUNTER_DISPLAY_ID = "2009";
        public static final String BUNDLE_STORE_PRODUCT_LARGE_COUNTER_DISPLAY_ID = "2007";
        public static final String BUNDLE_STORE_PRODUCT_FRAMED_FLOOR_STAND_ID = "2062";
        public static final String BUNDLE_STORE_PRODUCT_FRAMED_FLOOR_STAND_ID_SANDBOX = "2022";
        public static final String BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_FAITH_ID_SANDBOX = "2027";
        public static final String BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_EDUCATION_ID_SANDBOX = "2028";
        public static final String BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_NONPROFIT_ID_SANDBOX = "2029";
        public static final String BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_FAITH_ID = "2117";
        public static final String BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_EDUCATION_ID = "2118";
        public static final String BUNDLE_STORE_PRODUCT_PREMIUM_BENEVITY_BUNDLE_NONPROFIT_ID = "2119";

        // Subscription Terms
        public static final String BUNDLE_STORE_SUBSCRIPTION_TERM_MONTHLY = "Monthly";
        public static final String BUNDLE_STORE_SUBSCRIPTION_TERM_6_MONTHS = "6 Months";
        public static final String BUNDLE_STORE_SUBSCRIPTION_TERM_1_YEAR = "1 Year";

        // Payment Options
        public static final String BUNDLE_STORE_PAYMENT_OPTION_MONTHLY = "Monthly";
        public static final String BUNDLE_STORE_PAYMENT_OPTION_MONTHLY_X6  = "Monthly (x6)";
        public static final String BUNDLE_STORE_PAYMENT_OPTION_MONTHLY_X12  = "Monthly (x12)";
        public static final String BUNDLE_STORE_PAYMENT_OPTION_PAY_IN_FULL = "Pay In Full";

        // Netsuite Price Levels
        public static final String BUNDLE_STORE_NS_PRICE_LEVEL_INITIAL_CMP_MONTHLY_RENTAL = "9";
        public static final String BUNDLE_STORE_NS_PRICE_LEVEL_6_MONTHS_SUBS = "12";
        public static final String BUNDLE_STORE_NS_PRICE_LEVEL_6_MONTHS_SUBS_PREPAID = "13";
        public static final String BUNDLE_STORE_NS_PRICE_LEVEL_1_YEAR_SUBS = "10";
        public static final String BUNDLE_STORE_NS_PRICE_LEVEL_1_YEAR_SUBS_PREPAID = "11";

        // Calculation Price Keys
        public static final String BUNDLE_STORE_CPK_1D1MPM = "1D1MPM";
        public static final String BUNDLE_STORE_CPK_1D1MPY = "1D1MPY";
        public static final String BUNDLE_STORE_CPK_1D6MPM = "1D6MPM";
        public static final String BUNDLE_STORE_CPK_1D6MPY = "1D6MPY";
        public static final String BUNDLE_STORE_CPK_1D1YPM = "1D1YPM";
        public static final String BUNDLE_STORE_CPK_1D1YPY = "1D1YPY";

        public static final String BUNDLE_STORE_CPK_3D1MPM = "3D1MPM";
        public static final String BUNDLE_STORE_CPK_3D1MPY = "3D1MPY";
        public static final String BUNDLE_STORE_CPK_3D6MPM = "3D6MPM";
        public static final String BUNDLE_STORE_CPK_3D6MPY = "3D6MPY";
        public static final String BUNDLE_STORE_CPK_3D1YPM = "3D1YPM";
        public static final String BUNDLE_STORE_CPK_3D1YPY = "3D1YPY";

        public static final String BUNDLE_STORE_CPK_4D6MPM = "4D6MPM";
        public static final String BUNDLE_STORE_CPK_4D6MPY = "4D6MPY";
        public static final String BUNDLE_STORE_CPK_4D1YPM = "4D1YPM";
        public static final String BUNDLE_STORE_CPK_4D1YPY = "4D1YPY";

        public static final String BUNDLE_STORE_CPK_NONE = "NONE";
    }
}
