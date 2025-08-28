package com.tiptappay.store.app.constant;

public class AppConstants {

    public static class MembershipConstants {
        public static final String DEFAULT_PASSWORD = "AutoGen#123";
        public static final String DEFAULT_PHONE_FALLBACK = "+10000000000";
        public static final String DEFAULT_ROLE = "manager";
        public static final String DEFAULT_STATUS = "active";
        public static final String LOGIN_DESTINATION_CANADA = "/companies/165/dashboard";
    }
    public static class CampaignConstants {
        public static final String DEFAULT_STATUS = "active";
        public static final String CAMPAIGNS_ENDPOINT = "/companies/165/campaigns";
    }
    public static final class AppEnvironmentOptions {
        private AppEnvironmentOptions() {
        }

        public static final String PRODUCTION = "prod";
        public static final String DEVELOPMENT = "dev";
    }

    public static final class AthenaAppBaseUrl {
        private AthenaAppBaseUrl() {
        }

        public static final String PRODUCTION = "https://app.tiptappay.ca";
        public static final String DEVELOPMENT = "https://app-dev.tiptappay.ca";
    }

    public static final class NetsuiteEnvironmentOptions {
        private NetsuiteEnvironmentOptions() {
        }

        public static final String PRODUCTION = "PRODUCTION";
        public static final String SANDBOX = "SB";
    }

    public static final class ViewTemplates {
        private ViewTemplates() {
        }

        public static final String VIEW_HOME_INDEX = "home/home_index";
        public static final String VIEW_STYLES_INDEX = "home/tiptap_styles";
        public static final String VIEW_STORE_INDEX = "store/store_index";
        public static final String VIEW_STORE_BAG = "store/store_bag";
        public static final String VIEW_STORE_CHECKOUT_STEP_1 = "store/store_checkout_step_1";
        public static final String VIEW_STORE_CHECKOUT_STEP_2 = "store/store_checkout_step_2";
        public static final String VIEW_STORE_RESPONSE = "store/store_response";

        public static final String VIEW_US_STORE_FRESNO_SCREEN_1 = "us/store/us_store_screen_1";
        public static final String VIEW_US_STORE_FRESNO_SCREEN_2 = "us/store/us_store_screen_2";
        public static final String VIEW_US_STORE_FRESNO_SCREEN_3 = "us/store/us_store_screen_3";
        public static final String VIEW_US_STORE_FRESNO_SCREEN_4 = "us/store/us_store_screen_4";
        public static final String VIEW_US_STORE_FRESNO_SCREEN_5 = "us/store/us_store_screen_5";
        public static final String VIEW_US_STORE_FRESNO_SCREEN_6 = "us/store/us_store_screen_6";
        public static final String VIEW_US_STORE_FRESNO_SCREEN_7 = "us/store/us_store_screen_7";
        public static final String VIEW_US_STORE_FRESNO_SCREEN_8 = "us/store/us_store_screen_8";
        public static final String VIEW_US_STORE_FRESNO_SCREEN_SHOPPING_BAG = "us/store/us_store_screen_shopping_bag";

        public static final String VIEW_MOSQUES_STORE_SCREEN_1 = "mosques/store/mosques_store_screen_1";
        public static final String VIEW_MOSQUES_STORE_SCREEN_2 = "mosques/store/mosques_store_screen_2";
        public static final String VIEW_MOSQUES_STORE_SCREEN_3 = "mosques/store/mosques_store_screen_3";
        public static final String VIEW_MOSQUES_STORE_SCREEN_4 = "mosques/store/mosques_store_screen_4";
        public static final String VIEW_MOSQUES_STORE_SCREEN_5 = "mosques/store/mosques_store_screen_5";
        public static final String VIEW_MOSQUES_STORE_SCREEN_6 = "mosques/store/mosques_store_screen_6";
        public static final String VIEW_MOSQUES_STORE_SCREEN_7 = "mosques/store/mosques_store_screen_7";
        public static final String VIEW_MOSQUES_STORE_SCREEN_8 = "mosques/store/mosques_store_screen_8";
        public static final String VIEW_MOSQUES_STORE_SCREEN_SHOPPING_BAG = "mosques/store/mosques_store_screen_shopping_bag";

        public static final String VIEW_SAUS_STORE_SCREEN_1 = "saus/store/sa_us_store_screen_1";
        public static final String VIEW_SAUS_STORE_SCREEN_2 = "saus/store/sa_us_store_screen_2";
        public static final String VIEW_SAUS_STORE_SCREEN_3 = "saus/store/sa_us_store_screen_3";
        public static final String VIEW_SAUS_STORE_SCREEN_4 = "saus/store/sa_us_store_screen_4";
        public static final String VIEW_SAUS_STORE_SCREEN_5 = "saus/store/sa_us_store_screen_5";
        public static final String VIEW_SAUS_STORE_SCREEN_6 = "saus/store/sa_us_store_screen_6";
        public static final String VIEW_SAUS_STORE_SCREEN_7 = "saus/store/sa_us_store_screen_7";
        public static final String VIEW_SAUS_STORE_SCREEN_8 = "saus/store/sa_us_store_screen_8";
        public static final String VIEW_SAUS_STORE_SCREEN_SHOPPING_BAG = "saus/store/sa_us_store_screen_shopping_bag";

        public static final String VIEW_CANADA_STORE_SCREEN_1 = "saca/canada_store_screen_1";
        public static final String VIEW_CANADA_STORE_SCREEN_2 = "canada/canada_store_screen_2";
        public static final String VIEW_CANADA_STORE_SCREEN_3 = "canada/canada_store_screen_3";
        public static final String VIEW_CANADA_STORE_SCREEN_6 = "canada/canada_store_screen_7";
        public static final String VIEW_CANADA_STORE_SCREEN_7 = "canada/canada_store_screen_7";

        public static final String VIEW_LEGION_STORE_SCREEN_1 = "legion/legion_screen_1";
        public static final String VIEW_LEGION_STORE_SCREEN_2 = "legion/legion_screen_2";
        public static final String VIEW_LEGION_STORE_SCREEN_3 = "legion/legion_screen_3";
        public static final String VIEW_LEGION_STORE_SCREEN_1_FR = "legion/legion_screen_1_fr";
        public static final String VIEW_LEGION_STORE_SCREEN_2_FR= "legion/legion_screen_2_fr";
        public static final String VIEW_LEGION_STORE_SCREEN_3_FR = "legion/legion_screen_3_fr";

        public static final String VIEW_TTP_BUNDLES_STORE_SCREEN_1 = "bundles/store/ttp_bundles_store_screen_1";
        public static final String VIEW_TTP_BUNDLES_STORE_SCREEN_2 = "bundles/store/ttp_bundles_store_screen_2";
        public static final String VIEW_TTP_BUNDLES_STORE_SCREEN_3 = "bundles/store/ttp_bundles_store_screen_3";
        public static final String VIEW_TTP_BUNDLES_STORE_SCREEN_4 = "bundles/store/ttp_bundles_store_screen_4";
        public static final String VIEW_TTP_BUNDLES_STORE_SCREEN_5 = "bundles/store/ttp_bundles_store_screen_5";
        public static final String VIEW_TTP_BUNDLES_STORE_SCREEN_6 = "bundles/store/ttp_bundles_store_screen_6";
        public static final String VIEW_TTP_BUNDLES_STORE_SCREEN_7 = "bundles/store/ttp_bundles_store_screen_7";
        public static final String VIEW_TTP_BUNDLES_STORE_SCREEN_8 = "bundles/store/ttp_bundles_store_screen_8";
        public static final String VIEW_TTP_BUNDLES_STORE_SCREEN_SHOPPING_BAG = "bundles/store/ttp_bundles_store_screen_shopping_bag";

        public static final String VIEW_BENEVITY_STORE_PRICING = "benevity/store/benevity_store_pricing";
        public static final String VIEW_BENEVITY_STORE_CUSTOMIZE = "benevity/store/benevity_store_customize";
        public static final String VIEW_BENEVITY_STORE_CART = "benevity/store/benevity_store_cart";
        public static final String VIEW_BENEVITY_STORE_SHIPPING = "benevity/store/benevity_store_shipping";
        public static final String VIEW_BENEVITY_STORE_ORGANIZATION_DETAILS = "benevity/store/benevity_store_organization_details";
        public static final String VIEW_BENEVITY_STORE_PAYMENT = "benevity/store/benevity_store_payment";
        public static final String VIEW_BENEVITY_STORE_COMPLETE_START_COLLECTING = "benevity/store/benevity_store_complete_start_collecting";
        public static final String VIEW_BENEVITY_STORE_COMPLETE_KEEP_COLLECTING = "benevity/store/benevity_store_complete_keep_collecting";
        public static final String VIEW_BENEVITY_ORDER_COMPLETE_MY_ACCOUNT_PREP_FAILED = "benevity/store/benevity_store_complete_my_account_prep_failed";
        public static final String VIEW_BENEVITY_STORE_COMPLETE_FAIL = "benevity/store/benevity_store_complete_fail";
        public static final String VIEW_BENEVITY_STORE_MY_ACCOUNT_SETUP_FOR_CREATING_MEMBER = "benevity/store/benevity_store_my_account_setup_for_creating_member";
        public static final String VIEW_BENEVITY_STORE_MY_ACCOUNT_FOR_EMAIL_ENTRY = "benevity/store/benevity_store_my_account_for_email_entry";
        public static final String VIEW_BENEVITY_STORE_MY_ACCOUNT_SETUP_VERIFY = "benevity/store/benevity_store_my_account_setup_verify";
        public static final String VIEW_BENEVITY_STORE_MY_ACCOUNT_SETUP_VERIFY_FAILED = "benevity/store/benevity_store_my_account_setup_verify_failed";

        public static final String VIEW_BENEVITY_FB_STORE_PRICING = "benevity/fb/benevity_fb_store_pricing";
        public static final String VIEW_BENEVITY_FB_STORE_CUSTOMIZE = "benevity/fb/benevity_fb_store_customize";
        public static final String VIEW_BENEVITY_FB_STORE_CART = "benevity/fb/benevity_fb_store_cart";
        public static final String VIEW_BENEVITY_FB_STORE_SHIPPING = "benevity/fb/benevity_fb_store_shipping";
        public static final String VIEW_BENEVITY_FB_STORE_ORGANIZATION_DETAILS = "benevity/fb/benevity_fb_store_organization_details";
        public static final String VIEW_BENEVITY_FB_STORE_PAYMENT = "benevity/fb/benevity_fb_store_payment";
        public static final String VIEW_BENEVITY_FB_STORE_COMPLETE_START_COLLECTING = "benevity/fb/benevity_fb_store_complete_start_collecting";
        public static final String VIEW_BENEVITY_FB_STORE_COMPLETE_KEEP_COLLECTING = "benevity/fb/benevity_fb_store_complete_keep_collecting";
        public static final String VIEW_BENEVITY_FB_STORE_ORDER_COMPLETE_MY_ACCOUNT_PREP_FAILED = "benevity/fb/benevity_fb_store_complete_my_account_prep_failed";
        public static final String VIEW_BENEVITY_FB_STORE_COMPLETE_FAIL = "benevity/fb/benevity_fb_store_complete_fail";
        public static final String VIEW_BENEVITY_FB_STORE_MY_ACCOUNT_SETUP_FOR_CREATING_MEMBER = "benevity/fb/benevity_fb_store_my_account_setup_for_creating_member";
        public static final String VIEW_BENEVITY_FB_STORE_MY_ACCOUNT_FOR_EMAIL_ENTRY = "benevity/fb/benevity_fb_store_my_account_for_email_entry";
        public static final String VIEW_BENEVITY_FB_STORE_MY_ACCOUNT_SETUP_VERIFY = "benevity/fb/benevity_fb_store_my_account_setup_verify";
        public static final String VIEW_BENEVITY_FB_STORE_MY_ACCOUNT_SETUP_VERIFY_FAILED = "benevity/fb/benevity_fb_store_my_account_setup_verify_failed";

        public static final String VIEW_BENEVITY_EDUCATION_STORE_PRICING = "benevity/education/benevity_education_store_pricing";
        public static final String VIEW_BENEVITY_EDUCATION_STORE_CUSTOMIZE = "benevity/education/benevity_education_store_customize";
        public static final String VIEW_BENEVITY_EDUCATION_STORE_CART = "benevity/education/benevity_education_store_cart";
        public static final String VIEW_BENEVITY_EDUCATION_STORE_SHIPPING = "benevity/education/benevity_education_store_shipping";
        public static final String VIEW_BENEVITY_EDUCATION_STORE_ORGANIZATION_DETAILS = "benevity/education/benevity_education_store_organization_details";
        public static final String VIEW_BENEVITY_EDUCATION_STORE_PAYMENT = "benevity/education/benevity_education_store_payment";
        public static final String VIEW_BENEVITY_EDUCATION_STORE_COMPLETE_START_COLLECTING = "benevity/education/benevity_education_store_complete_start_collecting";
        public static final String VIEW_BENEVITY_EDUCATION_STORE_COMPLETE_KEEP_COLLECTING = "benevity/education/benevity_education_store_complete_keep_collecting";
        public static final String VIEW_BENEVITY_EDUCATION_STORE_ORDER_COMPLETE_MY_ACCOUNT_PREP_FAILED = "benevity/education/benevity_education_store_complete_my_account_prep_failed";
        public static final String VIEW_BENEVITY_EDUCATION_STORE_COMPLETE_FAIL = "benevity/education/benevity_education_store_complete_fail";
        public static final String VIEW_BENEVITY_EDUCATION_STORE_MY_ACCOUNT_SETUP_FOR_CREATING_MEMBER = "benevity/education/benevity_education_store_my_account_setup_for_creating_member";
        public static final String VIEW_BENEVITY_EDUCATION_STORE_MY_ACCOUNT_FOR_EMAIL_ENTRY = "benevity/education/benevity_education_store_my_account_for_email_entry";
        public static final String VIEW_BENEVITY_EDUCATION_STORE_MY_ACCOUNT_SETUP_VERIFY = "benevity/education/benevity_education_store_my_account_setup_verify";
        public static final String VIEW_BENEVITY_EDUCATION_STORE_MY_ACCOUNT_SETUP_VERIFY_FAILED = "benevity/education/benevity_education_store_my_account_setup_verify_failed";

        public static final String VIEW_BENEVITY_SESSION_EXPIRED = "benevity/benevity-session-expired";

    }

    public static final class RedirectTemplates {
        private RedirectTemplates() {
        }

        public static final String REDIRECT_HOME = "redirect:/";
        public static final String REDIRECT_STORE = "redirect:/store";
        public static final String REDIRECT_STORE_SUMMARY = "redirect:/store/summary";
        public static final String REDIRECT_STORE_CHECKOUT = "redirect:/store/checkout";

        public static final String REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ORDER = "redirect:/us-catholic/fresno-diocese/order";
        public static final String REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_CONTACT_AND_SHIPPING = "redirect:/us-catholic/fresno-diocese/contact-and-shipping";
        public static final String REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ORDER_REVIEW = "redirect:/us-catholic/fresno-diocese/order-review";
        public static final String REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_PAYMENT = "redirect:/us-catholic/fresno-diocese/payment";
        public static final String REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_THANKS = "redirect:/us-catholic/fresno-diocese/thanks";
        public static final String REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_FAILED = "redirect:/us-catholic/fresno-diocese/failed";
        public static final String REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ERROR = "redirect:/us-catholic/fresno-diocese/error";

        public static final String REDIRECT_MOSQUES_ORDER = "redirect:/mosques/order";
        public static final String REDIRECT_MOSQUES_CONTACT_AND_SHIPPING = "redirect:/mosques/contact-and-shipping";
        public static final String REDIRECT_MOSQUES_ORDER_REVIEW = "redirect:/mosques/order-review";
        public static final String REDIRECT_MOSQUES_PAYMENT = "redirect:/mosques/payment";
        public static final String REDIRECT_MOSQUES_THANKS = "redirect:/mosques/thanks";
        public static final String REDIRECT_MOSQUES_FAILED = "redirect:/mosques/failed";
        public static final String REDIRECT_MOSQUES_ERROR = "redirect:/mosques/error";

        public static final String REDIRECT_SAUS_ORDER = "redirect:/salvation-army-us/order";
        public static final String REDIRECT_SAUS_CONTACT_AND_SHIPPING = "redirect:/salvation-army-us/contact-and-shipping";
        public static final String REDIRECT_SAUS_ORDER_REVIEW = "redirect:/salvation-army-us/order-review";
        public static final String REDIRECT_SAUS_PAYMENT = "redirect:/salvation-army-us/payment";
        public static final String REDIRECT_SAUS_THANKS = "redirect:/salvation-army-us/thanks";
        public static final String REDIRECT_SAUS_FAILED = "redirect:/salvation-army-us/failed";
        public static final String REDIRECT_SAUS_ERROR = "redirect:/salvation-army-us/error";

        public static final String REDIRECT_CANADA_ORDER = "redirect:/canada-store/order";
        public static final String REDIRECT_CANADA_CONTACT_AND_SHIPPING = "redirect:/canada-store/contact-and-shipping";
        public static final String REDIRECT_CANADA_ORDER_REVIEW = "redirect:/canada-store/order-review";
        public static final String REDIRECT_CANADA_THANKS = "redirect:/canada-store/thanks";
        public static final String REDIRECT_CANADA_FAILED = "redirect:/canada-store/failed";

        public static final String REDIRECT_LEGION_ORDER = "redirect:/legion/en/order";
        public static final String REDIRECT_LEGION_ORDER_CONFIRM = "redirect:/legion/en/order-confirm";
        public static final String REDIRECT_CANADA_ORDER_SUBMIT = "redirect:/legion/en/order-submit";
        public static final String REDIRECT_LEGION_ORDER_FR = "redirect:/legion/fr/order";
        public static final String REDIRECT_LEGION_ORDER_CONFIRM_FR = "redirect:/legion/fr/order-confirm";
        public static final String REDIRECT_CANADA_ORDER_SUBMIT_FR = "redirect:/legion/fr/order-submit";

        public static final String REDIRECT_TTP_BUNDLES_ORDER = "redirect:/bundles/order";
        public static final String REDIRECT_TTP_BUNDLES_ORDER_CUSTOMIZE = "redirect:/bundles/order/customize";
        public static final String REDIRECT_TTP_BUNDLES_ORDER_CART = "redirect:/bundles/order/cart";
        public static final String REDIRECT_TTP_BUNDLES_ORDER_CHECKOUT_SHIPPING = "redirect:/bundles/order/checkout/shipping";
        public static final String REDIRECT_TTP_BUNDLES_ORDER_CHECKOUT_PAYMENT = "redirect:/bundles/order/checkout/payment";
        public static final String REDIRECT_TTP_BUNDLES_ORDER_COMPLETE = "redirect:/bundles/order/complete";

        public static final String REDIRECT_BENEVITY_ORDER = "redirect:/benevity/order";
        public static final String REDIRECT_BENEVITY_ORDER_CUSTOMIZE = "redirect:/benevity/order/customize";
        public static final String REDIRECT_BENEVITY_ORDER_CART = "redirect:/benevity/order/cart";
        public static final String REDIRECT_BENEVITY_ORDER_CHECKOUT_ORGANIZATION_DETAILS = "redirect:/benevity/order/checkout/organization-details";
        public static final String REDIRECT_BENEVITY_ORDER_CHECKOUT_SHIPPING = "redirect:/benevity/order/checkout/shipping";
        public static final String REDIRECT_BENEVITY_ORDER_CHECKOUT_PAYMENT = "redirect:/benevity/order/checkout/payment";
        public static final String REDIRECT_BENEVITY_ORDER_COMPLETE = "redirect:/benevity/order/complete";
        public static final String REDIRECT_BENEVITY_MY_ACCOUNT = "redirect:/benevity/my-account";
        public static final String REDIRECT_BENEVITY_MY_ACCOUNT_SETUP = "redirect:/benevity/my-account-setup";
        public static final String REDIRECT_BENEVITY_MY_ACCOUNT_RETURNING = "redirect:/benevity/my-account/returning";
        public static final String REDIRECT_BENEVITY_MY_ACCOUNT_SETUP_VERIFY = "redirect:/benevity/my-account-setup/verify";
        public static final String REDIRECT_BENEVITY_MY_ACCOUNT_VERIFY = "redirect:/benevity/my-account/verify";
        public static final String REDIRECT_BENEVITY_MY_ACCOUNT_SETUP_ERROR = "redirect:/benevity/my-account-setup/error";

        public static final String REDIRECT_BENEVITY_FB_ORDER = "redirect:/benevity/faith/order";
        public static final String REDIRECT_BENEVITY_FB_ORDER_CUSTOMIZE = "redirect:/benevity/faith/order/customize";
        public static final String REDIRECT_BENEVITY_FB_ORDER_CART = "redirect:/benevity/faith/order/cart";
        public static final String REDIRECT_BENEVITY_FB_ORDER_CHECKOUT_ORGANIZATION_DETAILS = "redirect:/benevity/faith/order/checkout/organization-details";
        public static final String REDIRECT_BENEVITY_FB_ORDER_CHECKOUT_SHIPPING = "redirect:/benevity/faith/order/checkout/shipping";
        public static final String REDIRECT_BENEVITY_FB_ORDER_CHECKOUT_PAYMENT = "redirect:/benevity/faith/order/checkout/payment";
        public static final String REDIRECT_BENEVITY_FB_ORDER_COMPLETE = "redirect:/benevity/faith/order/complete";
        public static final String REDIRECT_BENEVITY_FB_MY_ACCOUNT = "redirect:/benevity/faith/my-account";
        public static final String REDIRECT_BENEVITY_FB_MY_ACCOUNT_SETUP = "redirect:/benevity/faith/my-account-setup";
        public static final String REDIRECT_BENEVITY_FB_MY_ACCOUNT_RETURNING = "redirect:/benevity/faith/my-account/returning";
        public static final String REDIRECT_BENEVITY_FB_MY_ACCOUNT_SETUP_VERIFY = "redirect:/benevity/faith/my-account-setup/verify";
        public static final String REDIRECT_BENEVITY_FB_MY_ACCOUNT_VERIFY = "redirect:/benevity/faith/my-account/verify";
        public static final String REDIRECT_BENEVITY_FB_MY_ACCOUNT_SETUP_ERROR = "redirect:/benevity/faith/my-account-setup/error";

        public static final String REDIRECT_BENEVITY_EDUCATION_ORDER = "redirect:/benevity/education/order";
        public static final String REDIRECT_BENEVITY_EDUCATION_ORDER_CUSTOMIZE = "redirect:/benevity/education/order/customize";
        public static final String REDIRECT_BENEVITY_EDUCATION_ORDER_CART = "redirect:/benevity/education/order/cart";
        public static final String REDIRECT_BENEVITY_EDUCATION_ORDER_CHECKOUT_ORGANIZATION_DETAILS = "redirect:/benevity/education/order/checkout/organization-details";
        public static final String REDIRECT_BENEVITY_EDUCATION_ORDER_CHECKOUT_SHIPPING = "redirect:/benevity/education/order/checkout/shipping";
        public static final String REDIRECT_BENEVITY_EDUCATION_ORDER_CHECKOUT_PAYMENT = "redirect:/benevity/education/order/checkout/payment";
        public static final String REDIRECT_BENEVITY_EDUCATION_ORDER_COMPLETE = "redirect:/benevity/education/order/complete";
        public static final String REDIRECT_BENEVITY_EDUCATION_MY_ACCOUNT = "redirect:/benevity/education/my-account";
        public static final String REDIRECT_BENEVITY_EDUCATION_MY_ACCOUNT_SETUP = "redirect:/benevity/education/my-account-setup";
        public static final String REDIRECT_BENEVITY_EDUCATION_MY_ACCOUNT_RETURNING = "redirect:/benevity/education/my-account/returning";
        public static final String REDIRECT_BENEVITY_EDUCATION_MY_ACCOUNT_SETUP_VERIFY = "redirect:/benevity/education/my-account-setup/verify";
        public static final String REDIRECT_BENEVITY_EDUCATION_MY_ACCOUNT_VERIFY = "redirect:/benevity/education/my-account/verify";
        public static final String REDIRECT_BENEVITY_EDUCATION_MY_ACCOUNT_SETUP_ERROR = "redirect:/benevity/education/my-account-setup/error";


        public static final String REDIRECT_STRING_FORMAT_BENEVITY_SESSION_EXPIRED = "redirect:/%s/my-account-setup/session-expired";
    }

    public static final class Attributes {
        private Attributes() {
        }

        public static final String ATTRIBUTE_ORDER_FORM = "form";
        public static final String ATTRIBUTE_STORE_BAG = "bag";
        public static final String ATTRIBUTE_STORE_PRICING = "pricing";
        public static final String ATTRIBUTE_ORDER_RESPONSE = "orderResponse";
        public static final String ATTRIBUTE_BAG_EMPTY = "bagEmpty";

        public static final String ATTRIBUTE_PRODUCTS = "products";
        public static final String ATTRIBUTE_STORE_SHOPPING_BAG = "storeShoppingBag";
        public static final String ATTRIBUTE_STEPPER = "stepper";
        public static final String ATTRIBUTE_CURRENT_YEAR = "currentYear";
        public static final String ATTRIBUTE_CONTACT_AND_SHIPPING = "contactAndShipping";
        public static final String ATTRIBUTE_FORMAT_CENTS = "fmt";
        public static final String ATTRIBUTE_PAYMENT = "payment";
        public static final String ATTRIBUTE_RESPONSE_PAYMENT = "responsePayment";
        public static final String ATTRIBUTE_DEVELOPER_INSIGHTS_ENABLED = "developerInsightsEnabled";
        public static final String ATTRIBUTE_PRETTY_JSON_QUOTE_REQUEST = "prettyJsonQuoteRequest";
        public static final String ATTRIBUTE_PRETTY_JSON_QUOTE_RESPONSE = "prettyJsonQuoteResponse";
        public static final String ATTRIBUTE_PRETTY_JSON_PAYMENT_REQUEST = "prettyJsonPaymentRequest";
        public static final String ATTRIBUTE_PRETTY_JSON_PAYMENT_RESPONSE = "prettyJsonPaymentResponse";
        public static final String ATTRIBUTE_QUOTE_FAILED = "quoteFailed";
        public static final String ATTRIBUTE_EMPTY_BAG = "emptyBag";
        public static final String ATTRIBUTE_IS_QUOTE_AVAILABLE = "isQuoteAvailable";
        public static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
        public static final String ATTRIBUTE_ERROR_CODE = "errorCode";
    }

    public static final class DefaultValidatorMessages {
        private DefaultValidatorMessages() {
        }

        public static final String PO_BOX_VALIDATOR_MESSAGE = "PO Box is not allowed.";
    }

    public static final class ResponseMessages {
        private ResponseMessages() {
        }

        public static final String RESTLET_CREDENTIAL_ERROR = "No response received, please confirm RESTlet credentials.";
    }

    public static final class AppMessages {
        private AppMessages() {
        }

        public static final String ERROR_SENDING_PAYLOAD = "Error while sending payload to the NetSuite";
        public static final String ERROR_PREPARING_PAYLOAD = "Error while preparing payload";
        public static final String BAG_EMPTY_MESSAGE = "No device added to the bag. Please add a device to continue.";
        public static final String ERROR_ADDRESS_ISSUE = "Please double-check that your address is complete and accurate. If everything looks correct and the problem persists, contact support for help.";
        public static final String ERROR_INVALID_CAMPAIGN_START_DATE = "'For Program Start Date' you’ve entered %s days that is less than the required 4 weeks needed to guarantee the delivery on time. We cannot ensure your order will be ready and shipping in time for your desired delivery date. Please choose either a date that is 4 weeks from now or click on the 'more info' link below the date field for other options.";
    }

    public static final class PricingConstants {
        private PricingConstants() {
        }

        public static final String DEFAULT_CURRENCY = "CAD";
        public static final boolean OVERRIDE_SETUP_FEE = true;
        public static final int SETUP_FEE_AMOUNT_IN_CENTS = 9900;
        public static final int ONE_TIME_FEE_AMOUNT_IN_CENTS = 5000;
        public static final int MONTHLY_FEE_AMOUNT_IN_CENTS = 2500;
    }

    public static final class OrderFormTypes {
        private OrderFormTypes() {
        }

        public static final String FORM_TYPE_BUNDLES = "Bundles Order Form";
        public static final String FORM_TYPE_BENEVITY = "Benevity Order Form";
        public static final String FORM_TYPE_BENEVITY_NONPROFIT = "Benevity NPO Order Form";
        public static final String FORM_TYPE_BENEVITY_FAITH_BASED = "Benevity Faith Based Order Form";
        public static final String FORM_TYPE_BENEVITY_EDUCATION = "Benevity Education Order Form";
    }

    public static final class CookiesConstants {
        private CookiesConstants() {
        }

        public static final String COOKIE_SHOPPING_BAG = "_ttp_ac_sb";
        public static final String COOKIE_CONTACT_SHIPPING = "_ttp_ac_cands";
        public static final String COOKIE_TAX_SHIPPING_QUOTE = "_ttp_ac_tsq";
        public static final String COOKIE_NETSUITE_COMMUNICATE_QUOTE = "_ttp_ac_ncq";
        public static final String COOKIE_NETSUITE_COMMUNICATE_PAYMENT = "_ttp_ac_ncp";
        public static final String COOKIE_RESPONSE_QUOTE_BODY = "_ttp_ac_rqb";
        public static final String COOKIE_RESPONSE_PAYMENT_BODY = "_ttp_ac_rppb";
        public static final String COOKIE_MY_ACCOUNT_TOKEN = "_ttp_c_mat";

        public static final String COOKIE_ID_BENEVITY_MAIN = "_g";
        public static final String COOKIE_ID_BENEVITY_FB = "_fb";
        public static final String COOKIE_ID_BENEVITY_EDUCATION = "_edu";
    }

    public static final class NetSuiteActions {
        private NetSuiteActions() {
        }

        public static final String ACTION_QUOTE_TAX_AND_SHIPPING = "quoteTaxAndShipping";
        public static final String ACTION_COMPLETE_ORDER_WITH_PAYMENT = "completeOrderWithPayment";
        public static final String ACTION_COMPLETE_ORDER_WITHOUT_PAYMENT = "completeOrderWithoutPayment";
    }

    public static final class HttpConstants {
        private HttpConstants() {
        }

        public static final String HTTP_HEADER_NAME_CONTENT_TYPE = "Content-Type";
        public static final String HTTP_HEADER_NAME_ACCEPT = "Accept";
        public static final String HTTP_HEADER_NAME_AUTHORIZATION = "Authorization";
        public static final String HTTP_HEADER_NAME_API_KEY = "Api-Key";
        public static final String HTTP_HEADER_NAME_ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
        public static final String HTTP_HEADER_NAME_ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
        public static final String HTTP_HEADER_NAME_ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
        public static final String HTTP_HEADER_NAME_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";


        public static final String HTTP_HEADER_VALUE_CONTENT_TYPE_URL_ENCODED = "application/x-www-form-urlencoded";
        public static final String HTTP_HEADER_VALUE_CONTENT_TYPE_VND_API_JSON = "application/vnd.api+json";
        public static final String HTTP_HEADER_VALUE_CONTENT_TYPE_JSON = "application/json";

        public static final String HTTP_METHOD_GET = "GET";
        public static final String HTTP_METHOD_POST = "POST";

        public static final int HTTP_RESPONSE_SUCCESS = 200;
        public static final int HTTP_RESPONSE_CREATED = 201;
        public static final int HTTP_RESPONSE_UNAUTHORIZED = 401;
        public static final int HTTP_RESPONSE_UNPROCESSABLE_ENTITY = 422;
        public static final int HTTP_RESPONSE_FORBIDDEN = 403;
        public static final int HTTP_RESPONSE_NOT_FOUND = 404;
        public static final int HTTP_RESPONSE_CONFLICT = 409;
        public static final int HTTP_RESPONSE_INTERNAL_SERVER_ERROR = 500;

    }

    public static final class MembershipRoles {
        private MembershipRoles() {
        }

        public static final String MEMBERSHIP_ROLE_ADMIN = "admin";
        public static final String MEMBERSHIP_ROLE_MANAGER = "manager";

    }

    public static final class MembershipStatus {
        private MembershipStatus() {
        }

        public static final String MEMBERSHIP_STATUS_ACTIVE = "active";
        public static final String MEMBERSHIP_STATUS_PENDING_APPROVAL = "pending_approval";

    }

    public static final class EmailConstants {
        private EmailConstants() {
        }

        public static final String TEMPLATE_KEY_SUBJECT = "subject";
        public static final String TEMPLATE_KEY_MY_ACCOUNT_TOKEN = "myAccountToken";
        public static final String TEMPLATE_THYMELEAF_MEMBERSHIP_EMAIL = "email/membership_magic_link_email.html";

    }
}
