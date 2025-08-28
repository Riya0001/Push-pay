package com.tiptappay.web.constant;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author sanjay.gadhiya
 * @Create 3/31/2021
 * @Description
 */
// TODO : Remove unnecessary keys
public class TTPConstant {
    private TTPConstant() {
    }

    public static final String LOGIN              = "/login";
    public static final String REGEX_EMAIL        = "^[a-zA-Z0-9.+_-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,15}$";
    public static final String REGEX_PHONE_NUMBER = "^[0-9]+$";
    public static final String REGEX_POSTAL_CODE  = "^[a-zA-Z0-9]+$";
    public static final String REPLACE_GP_PWD     = "[^\\n]";  //NOSONAR

    public static final String REDIS_LABEL_TIPTAP_MPOS = "tiptap_mpos:";
    public static final String REDIS_LABEL_GEOLOCATED_CELL_TOWERS = "geolocated_cell_towers:";

    public static final String SALT_CHARS_AZ_09   = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final String SALT_CHARS_09      = "0123456789";

    public static final class RuntimeEnv {
        private RuntimeEnv() {
        }

        public static final String DEVELOPMENT = "development";
        public static final String PRODUCTION  = "production";
    }

    public static final class HttpConstants {
        private HttpConstants() {}

        public static final String HTTP_HEADER_NAME_CONTENT_TYPE = "Content-Type";
        public static final String HTTP_HEADER_NAME_AUTHORIZATION = "Authorization";
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

    public static final class ModelParameter {
        private ModelParameter() {
        }

        public static final String USER                              = "user";
        public static final String ROLE                              = "role";
        public static final String ORGANIZATION                      = "organization";
        public static final String ORGANIZATIONS                     = "organizations";
        public static final String ORGANIZATION_ROLES                = "organizationRoles";
        public static final String ORGANIZATION_LIST                 = "organizationList";
        public static final String SUCCESS_MESSAGE                   = "successMessage";
        public static final String ADD_USER_TO_ORGANIZATION          = "addUserToOrganization";
        public static final String MASTER_ROLES                      = "masterRoles";
        public static final String ATTRIBUTE                         = "attribute";
        public static final String CHANGE_PASSWORD                   = "changePassword";
        public static final String FORGOT_PASSWORD                   = "forgotPassword";
        public static final String RESET_PASSWORD                    = "resetPassword";
        public static final String ROLE_LIST                         = "roleList";
        public static final String NO_OF_TIPTAP_LIST                 = "noOfTipTapList";
        public static final String DEVICE_STATUS_LIST                = "deviceStatusList";
        public static final String VALUE_FILTER_LIST                 = "valueFilterList";
        public static final String USER_STATUS_LIST                  = "userStatusList";
        public static final String REGISTER_USER                     = "registerUser";
        public static final String DENOMINATION_LIST                 = "denominationList";
        public static final String ORGANIZATION_NAME_LIST            = "organizationNameList";
        public static final String CAMPAIGN_NAME_LIST                = "campaignNameList";
        public static final String TIP_TAP_CONFIG                    = "tipTapConfig";
        public static final String TIP_TAP                           = "tipTap";
        public static final String CONFIG_FILE_LIST                  = "configFileList";
        public static final String PAYMENT_PROCESSORS                = "paymentProcessors";
        public static final String CAMPAIGN_LIST                     = "campaignList";
        public static final String DEVICE_ITEM_VIEW                  = "deviceItemView";
        public static final String CURRENCY_LIST                     = "currencyList";
        public static final String IS_ORGANIZATION_PROFILE_FTUX_VIEW = "isOrganizationProfileFTUXView";
        public static final String IS_ATTRIBUTE_LIST_FTUX_VIEW       = "isAttributeListFTUXView";
        public static final String IS_VIEW_ATTRIBUTE_FTUX_VIEW       = "isViewAttributeFTUXView";
        public static final String IS_EDIT_ATTRIBUTE_FTUX_VIEW       = "isEditAttributeFTUXView";
        public static final String IS_DASHBOARD_FTUX_VIEW            = "isDashboardFTUXView";

        public static final String REQUEST_PAYLOAD                   = "requestPayload";
        public static final String REQUEST_PAYLOAD_PRETTY_JSON       = "requestPayloadPrettyJson";
        public static final String RESPONSE_PAYLOAD                  = "responsePayload";
        public static final String RESPONSE_PAYLOAD_PRETTY_JSON      = "responsePayloadPrettyJson";
        public static final String REDIS_DEVICE_INFO                 = "redisDeviceInfo";
        public static final String REDIS_DEVICE_INFO_PRETTY_JSON     = "redisDeviceInfoPrettyJson";
        public static final String TRANSACTION_TIME_HUMAN_READABLE   = "transactionTimeHumanReadable";
        public static final String TRANSACTION_RESPONSE_DETAIL       = "transactionResponseDetail";
    }
    public static final class TicketNames {
        private TicketNames() {
        }

        public static final String GENERAL_INQUIRY = "General Inquiry";
        public static final String EXISTING_ORDER_INQUIRY = "Existing Order Inquiry";
        public static final String BILLING = "Billing";
        public static final String RETURN_LABEL_REQUEST = "Return Label Request";
        public static final String TECHNICAL_ISSUE_DEVICE = "Technical Issue:Device";
        public static final String TECHNICAL_ISSUE_BATTERY = "Technical Issue:Battery";
        public static final String TECHNICAL_ISSUE_CABLE = "Technical Issue:Cable";
        public static final String TECHNICAL_ISSUE_WALL_PLUG = "Technical Issue:Wall Plug";
        public static final String TECHNICAL_ISSUE_OTHER = "Technical Issue:Other";
    }

    public static final class EmailTemplateParameter {
        private EmailTemplateParameter() {
        }

        public static final String NAME     = "name";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String LINK     = "link";
    }

    public static final class SessionAttribute {
        private SessionAttribute() {
        }

        public static final String USER_ID                = "userId";
        public static final String USER_ORGANIZATION_LIST = "userOrganizationList";
        public static final String ORGANIZATION_ID        = "organizationId";
        public static final String ORGANIZATION_MODULE    = "organizationModule";
        public static final String FTUX_USER_SETTINGS     = "ftuxUserSettings";
    }

    public static final class TemplateView {
        private TemplateView() {
        }

        // General Views
        public static final String VIEW_USER_LOGIN            = "login/login";
        public static final String VIEW_CHANGE_PASSWORD       = "login/change_password";
        public static final String VIEW_FORGOT_PASSWORD       = "login/forgot_password";
        public static final String VIEW_RESET_PASSWORD_LINK   = "login/reset_password_link";
        public static final String VIEW_RESET_PASSWORD        = "login/reset_password";
        public static final String VIEW_ACCESS_DENIED         = "login/access_denied";
        public static final String VIEW_REGISTER_INVITED_USER = "login/register_invited_user";

        // Admin Views
        public static final String VIEW_ADMIN_PAGE_404                 = "admin/exceptions/page_404";
        public static final String VIEW_ADMIN_ADD_USER                 = "admin/user/add_user";
        public static final String VIEW_ADMIN_EDIT_USER                = "admin/user/edit_user";
        public static final String VIEW_ADMIN_USER_LIST                = "admin/user/user_list";
        public static final String VIEW_ADMIN_VIEW_USER                = "admin/user/view_user";
        public static final String VIEW_ADMIN_ADD_USER_TO_ORGANIZATION = "admin/user/add_user_to_organization";
        public static final String VIEW_ADMIN_EDIT_ORGANIZATION_ROLE   = "admin/user/edit_organization_role";
        public static final String VIEW_ADMIN_MY_PROFILE               = "admin/my_profile";
        public static final String VIEW_ADMIN_EDIT_PROFILE             = "admin/edit_profile";
        public static final String VIEW_ADMIN_DASHBOARD                = "admin/dashboard";
        public static final String VIEW_ADMIN_ADD_ORGANIZATION         = "admin/organization/add_organization";
        public static final String VIEW_ADMIN_EDIT_ORGANIZATION        = "admin/organization/edit_organization";
        public static final String VIEW_ADMIN_VIEW_ORGANIZATION        = "admin/organization/view_organization";
        public static final String VIEW_ADMIN_ORGANIZATION_LIST        = "admin/organization/organization_list";
        public static final String VIEW_ADMIN_DEVICE_LIST              = "admin/inventory/device_list";
        public static final String VIEW_ADMIN_ORGANIZATION_FTUX        = "admin/organization/organization_ftux";
        public static final String VIEW_ADMIN_VIEW_DEVICE              = "admin/inventory/view_device";
        public static final String VIEW_ADMIN_DEVICE_CONFIGURE_DEFAULT = "admin/inventory/configure_default";
        public static final String VIEW_ADMIN_INVENTORY_FTUX           = "admin/inventory/inventory_ftux";
        public static final String VIEW_ADMIN_EDIT_DEVICE              = "admin/inventory/edit_device";

        // Customer Views
        public static final String VIEW_CUSTOMER_PAGE_404                  = "customer/exceptions/page_404";
        public static final String VIEW_CUSTOMER_MY_PROFILE                = "customer/my_profile";
        public static final String VIEW_CUSTOMER_EDIT_PROFILE              = "customer/edit_profile";
        public static final String VIEW_CUSTOMER_CREATE_ATTRIBUTE          = "customer/organization/attribute/create_attribute";
        public static final String VIEW_CUSTOMER_EDIT_ATTRIBUTE            = "customer/organization/attribute/edit_attribute";
        public static final String VIEW_CUSTOMER_VIEW_ATTRIBUTE            = "customer/organization/attribute/view_attributes";
        public static final String VIEW_CUSTOMER_ATTRIBUTE_LIST            = "customer/organization/attribute/attribute_list";
        public static final String VIEW_CUSTOMER_DASHBOARD                 = "customer/dashboard";
        public static final String VIEW_CUSTOMER_ORGANIZATION_PROFILE      = "customer/organization/profile" +
                "/view_organization_profile";
        public static final String VIEW_CUSTOMER_EDIT_ORGANIZATION_PROFILE = "customer/organization/profile" +
                "/edit_organization_profile";
        public static final String VIEW_CUSTOMER_USER_LIST                 = "customer/user/user_list";
        public static final String VIEW_CUSTOMER_VIEW_USER                 = "customer/user/view_user";
        public static final String VIEW_CUSTOMER_EDIT_USER                 = "customer/user/edit_user";
        public static final String VIEW_CUSTOMER_ORGANIZATION_PROFILE_FTUX = "customer/organization/profile/organization_profile_ftux";
        public static final String VIEW_CUSTOMER_ATTRIBUTE_FTUX            = "customer/organization/attribute/attribute_ftux";

        // Payment Gateway Test Pages
        public static final String VIEW_PAYMENT_GATEWAY                    = "admin/paymentgateway/payment_gateway";
        public static final String VIEW_PAYMENT_GATEWAY_RESPONSE           = "admin/paymentgateway/payment_gateway_response";
    }

    public static final class RedirectView {
        private RedirectView() {
        }

        // General Redirect
        public static final String REDIRECT_LOGOUT = "redirect:/logout";
        public static final String REDIRECT_LOGIN  = "redirect:/login";

        // Admin Redirect
        public static final String REDIRECT_ADMIN_USER_LIST         = "redirect:/admin/user/list";
        public static final String REDIRECT_ADMIN_MY_PROFILE        = "redirect:/admin/myProfile";
        public static final String REDIRECT_ADMIN_DASHBOARD         = "redirect:/admin/dashboard";
        public static final String REDIRECT_ADMIN_ORGANIZATION_LIST = "redirect:/admin/organization/list";
        public static final String REDIRECT_ADMIN_ORGANIZATION_FTUX = "redirect:/admin/organization/ftux";
        public static final String REDIRECT_ADMIN_INVENTORY_FTUX    = "redirect:/admin/inventory/ftux";

        // Customer Redirect
        public static final String REDIRECT_CUSTOMER_ATTRIBUTE_LIST            = "redirect:/customer/organization/attribute/list";
        public static final String REDIRECT_CUSTOMER_MY_PROFILE                = "redirect:/customer/myProfile";
        public static final String REDIRECT_CUSTOMER_DASHBOARD                 = "redirect:/customer/dashboard";
        public static final String REDIRECT_CUSTOMER_ORGANIZATION_PROFILE      = "redirect:/customer/organization/profile";
        public static final String REDIRECT_CUSTOMER_USER_LIST                 = "redirect:/customer/user/list";
        public static final String REDIRECT_CUSTOMER_ORGANIZATION_PROFILE_FTUX = "redirect:/customer/organization/ftux";
        public static final String REDIRECT_CUSTOMER_ATTRIBUTE_FTUX            = "redirect:/customer/organization/attribute" +
                "/ftux";
    }

    public static final class UserStatus {
        private UserStatus() {
        }

        public static final String ACTIVE   = "active";
        public static final String INACTIVE = "inactive";
        public static final String PENDING  = "pending";

        public static List<String> getUserStatus() {
            return List.of(ACTIVE, INACTIVE, PENDING);
        }
    }

    public static final class OrganizationStatus {
        private OrganizationStatus() {
        }

        public static final String ACTIVE   = "active";
        public static final String INACTIVE = "inactive";
    }

    public static final class UserOrganizationRoleStatus {
        private UserOrganizationRoleStatus() {
        }

        public static final String ACTIVE   = "active";
        public static final String INACTIVE = "inactive";
    }

    public static final class TipTapStatus {
        private TipTapStatus() {
        }

        public static final String ACTIVE         = "active";
        public static final String DISABLED       = "disabled";
        public static final String DECOMMISSIONED = "decommissioned";

        public static List<String> getDeviceStatus() {
            return List.of(ACTIVE, DISABLED, DECOMMISSIONED);
        }
    }

    public static final class Roles {
        private Roles() {
        }

        // Customer - Master Roles
        public static final String ADMINISTRATOR              = "ADMINISTRATOR";
        public static final String CUSTOMER                   = "CUSTOMER";
        // Organization roles
        public static final String ORGANIZATION_ADMINISTRATOR = "ORGANIZATION_ADMINISTRATOR";
        public static final String ORGANIZATION_COLLECTOR     = "ORGANIZATION_COLLECTOR";
        public static final String ORGANIZATION_MANAGER       = "ORGANIZATION_MANAGER";
        public static final String ANALYST                    = "ANALYST";
        public static final String VIEW_ONLY                  = "VIEW_ONLY";

        public static Map<String, Boolean> getAllRoles() {
            LinkedHashMap<String, Boolean> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap.put(ADMINISTRATOR, true);
            linkedHashMap.put(CUSTOMER, true);
            linkedHashMap.put(ORGANIZATION_ADMINISTRATOR, false);
            linkedHashMap.put(ANALYST, false);
            linkedHashMap.put(VIEW_ONLY, false);
            return linkedHashMap;
        }
    }

    public static final class Permissions {
        private Permissions() {
        }

        public static final String ADMINISTRATOR              = "ADMINISTRATOR";
        public static final String CUSTOMER                   = "CUSTOMER";
        public static final String ORGANIZATION_ADMINISTRATOR = "ORGANIZATION_ADMINISTRATOR";
        public static final String ORGANIZATION_COLLECTOR = "ORGANIZATION_COLLECTOR";
        public static final String ORGANIZATION_MANAGER = "ORGANIZATION_MANAGER";
        public static final String ANALYST                    = "ANALYST";
        public static final String VIEW_ONLY                  = "VIEW_ONLY";
    }

    public static final class QueryBuilder {
        private QueryBuilder() {
        }

        public static final String AND      = " AND ";
        public static final String OR       = " OR ";
        public static final String ILIKE    = " ILIKE ";
        public static final String HAVING   = " HAVING ";
        public static final String BETWEEN  = " BETWEEN ";
        public static final String ORDER_BY = " ORDER BY ";
        public static final String LIMIT    = " LIMIT ";
        public static final String OFFSET   = " OFFSET ";
    }

    public static final class Language {
        private Language() {
        }

        public static final String ENGLISH = "en";
        public static final String FRENCH  = "fr";
    }

    public static final class CampaignStatus {
        private CampaignStatus() {
        }

        public static final String ACTIVE    = "active";
        public static final String INACTIVE = "inactive";
        public static final String COMPLETED = "completed";
        public static final String PENDING   = "pending";
    }

    public static final class Currency {
        private Currency() {
        }

        public static final String CAD = "CAD";
        public static final String USD = "USD";

        public static List<String> getCurrencies() {
            return List.of(CAD, USD);
        }
    }

    public static final class Denomination {
        private Denomination() {
        }

        public static final Integer DOLLAR_1  = 100; // Cents
        public static final Integer DOLLAR_2  = 200;
        public static final Integer DOLLAR_5  = 500;
        public static final Integer DOLLAR_10 = 1000;
        public static final Integer DOLLAR_20 = 2000;
        public static final Integer DOLLAR_50 = 5000;

        public static List<Integer> getDenominations() {
            return List.of(DOLLAR_1, DOLLAR_2, DOLLAR_5, DOLLAR_10, DOLLAR_20, DOLLAR_50);
        }
    }

    public static final class PaymentProcessor {
        private PaymentProcessor() {
        }

        public static final String MONERIS         = "moneris";
        public static final String GLOBAL_PAYMENTS = "global_payments";
        public static final String CYBERSOURCE     = "cybersource";
        public static final String ELAVON          = "elavon";
        public static final String UNKNOWN          = "unknown";

        public static Map<String, String> getPaymentProcessorMap() {
            LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap.put(MONERIS, "Moneris");
            linkedHashMap.put(GLOBAL_PAYMENTS, "Global Payments");
            linkedHashMap.put(CYBERSOURCE, "Cybersource");
            linkedHashMap.put(ELAVON, "Elavon");
            return linkedHashMap;
        }
    }

    public static final class DeviceRegistrationResponseCode {
        private DeviceRegistrationResponseCode() {
        }

        public static final int SUCCESS_201      = 201; // Created
        public static final int SUCCESS_200      = 200; // Created
        public static final int ERROR_422        = 422; // Unprocessable Entity
        public static final int ERROR_400        = 400; // Not Found
        public static final int ERROR_404        = 404; // Not Found
        public static final int UNAUTHORIZED_401 = 401; // Unauthorized
    }

    public static final class DeviceRegistrationFields {
        private DeviceRegistrationFields() {
        }

        public static final String SERIAL_NUMBER         = "SerialNumber";
        public static final String STATUS                = "Status";
        public static final String DENOMINATION          = "Denomination";
        public static final String DATE_CREATED          = "DateCreated";
        public static final String DATE_COMPLETED        = "DateCompleted";
        public static final String PRODUCT_HW_REVISION   = "ProductHwRevision";
        public static final String MSI_PCB_SERIAL_NUMBER = "MsiPcbSerialNumber";
        public static final String PRODUCT_SERIAL_NUMBER = "ProductSerialNumber";
        public static final String ST_MICRO_ID           = "StMicroId";
        public static final String DEVICE_IMEI           = "DeviceImei";
        public static final String PRODUCT_FW_REVISION   = "ProductFwRevision";
        public static final String SIM_CARD_NUMBER       = "SimCardNumber";
        public static final String ID_TECH_SERIAL_NUMBER = "IdTechSerialNumber";
        public static final String ENC_AES_KEY           = "EncAesKey";
        public static final String AES_KEY               = "AesKey";
        public static final String CURRENCY              = "Currency";
        public static final String OPERATOR_STATION_1    = "OperatorStation1";
        public static final String OPERATOR_STATION_2    = "OperatorStation2";
        public static final String TELEMETRY_FREQUENCY   = "TelemetryFrequency";
        // Device telemetry fields
        public static final String ERRORCODES            = "ErrorCodes";
        public static final String RSSI                  = "Rssi";
        public static final String TIMESTAMP             = "Timestamp";
        public static final String VER_ID_TECH           = "VerIdTech";
        public static final String VER_K81               = "VerK81";
        public static final String VER_MODEM             = "VerModem";
        public static final String VER_STM32             = "VerStm32";
    }

    public static final class UserFields {
        private UserFields() {
        }

        public static final String FIRST_NAME   = "firstName";
        public static final String LAST_NAME    = "lastName";
        public static final String EMAIL        = "email";
        public static final String PHONE_NUMBER = "phoneNumber";
    }

    public static final class FTUXModules {
        private FTUXModules() {
        }

        public static final String ORGANIZATION_ADMIN            = "organization_admin";
        public static final String INVENTORY_ADMIN               = "inventory_admin";
        public static final String CUSTOMER_DASHBOARD            = "customer_dashboard";
        public static final String CUSTOMER_ORGANIZATION_PROFILE = "customer_organization_profile";
        public static final String CUSTOMER_ATTRIBUTES           = "customer_attributes";
        public static final String CUSTOMER_ATTRIBUTES_LIST      = "customer_attributes_list";
        public static final String CUSTOMER_VIEW_ATTRIBUTES      = "customer_view_attributes";
        public static final String CUSTOMER_EDIT_ATTRIBUTES      = "customer_edit_attributes";
    }

    public static final class AdminInitiateAuthParameter {
        private AdminInitiateAuthParameter() {
        }

        public static final String USERNAME    = "USERNAME";
        public static final String PASSWORD    = "PASSWORD";
        public static final String SECRET_HASH = "SECRET_HASH";
    }

    public static final class AdminInitiateAuthChallengeNames {
        private AdminInitiateAuthChallengeNames() {
        }

        public static final String NEW_PASSWORD_REQUIRED = "NEW_PASSWORD_REQUIRED";
    }

    public static final class AdminInitiateAuthChallengeParameter {
        private AdminInitiateAuthChallengeParameter() {
        }

        public static final String USERNAME        = "USERNAME";
        public static final String PASSWORD        = "PASSWORD";
        public static final String NEW_PASSWORD    = "NEW_PASSWORD";
    }

    public static final class AWSCognitoAuthenticationServiceExceptionMessages {
        private AWSCognitoAuthenticationServiceExceptionMessages() {
        }

        public static final String NEW_PASSWORD_REQUIRED_MESSAGE = "Cognito user must change password : ";
        public static final String CHALLENGE_OCCURRED_MESSAGE    = "Challenge occurred : ";
    }

    public static final class AWSCognitoUserRoleServiceExceptionMessages {
        private AWSCognitoUserRoleServiceExceptionMessages() {
        }

        public static final String UNDEFINED_COGNITO_ROLE = "Undefined cognito role occurred : ";
    }

    public static final class AWSCognitoGroups {
        private AWSCognitoGroups() {
        }

        public static final String TIPTAP_ADMIN = "tiptap_admin";
        public static final String TIPTAP_USER  = "tiptap_user";
    }

    public static final class CsrfTokenFields {
        private CsrfTokenFields() {
        }

        public static final String XSRF_TOKEN = "XSRF-TOKEN";
    }

    public static final class TiptapWebKeyServiceFields {
        private TiptapWebKeyServiceFields() {
        }

        public static final String COOKIE_NAME       = "_tiptap_web_key";
        public static final String COOKIE_PATH       = "/";
        public static final String CSRF_TOKEN_KEY    = "_csrf_token";
        public static final String ACCESS_TOKEN_KEY  = "access_token";
        public static final String ID_TOKEN_KEY      = "id_token";
        public static final String LOGGED_IN_KEY     = "logged_in";

        public static final String HASHING_ALGORITHM = "HS256";
    }

    public static final class TiptapWebKeyServiceMessages {
        private TiptapWebKeyServiceMessages() {
        }

        public static final String TIPTAP_WEB_KEY_IS_VALID          = "_tiptap_web_key is valid";
        public static final String TIPTAP_WEB_KEY_IS_NOT_VALID      = "_tiptap_web_key is invalid";
        public static final String EXCEPTION_WHILE_VALIDATION_CHECK = "Exception occurred while validation check";
    }

    public static final class WebKeyProviderFields {
        private WebKeyProviderFields() {
        }

        public static final String WEB_KEY_NAME     = "_tiptap_web_key";
        public static final String WEB_KEY_PATH     = "/";
        public static final String CSRF_TOKEN_KEY   = "_csrf_token";
        public static final String ACCESS_TOKEN_KEY = "access_token";
        public static final String ID_TOKEN_KEY     = "id_token";
        public static final String LOGGED_IN_KEY    = "logged_in";
    }

    public static final class WebKeyProviderExceptionMessages {
        private WebKeyProviderExceptionMessages() {
        }

        public static final String NO_WEB_KEY_FOUND        = "No web key (cookie) found named ";
    }

    public static final class WebKeySecurityContextFields {
        private WebKeySecurityContextFields() {
        }

        public static final String ANONYMOUS_USER    = "anonymousUser";
        public static final String EMPTY_CREDENTIALS = "";
    }

    public static final class WebKeySecurityContextLogs {
        private WebKeySecurityContextLogs() {
        }
        public static final String NO_SECURITY_CONTEXT_AUTH_LOG        = "SecurityContext has not authenticated, saveContext() will be skipped";
        public static final String SECURITY_CONTEXT_ANONYMOUS_USER_LOG = "SecurityContext has anonymous user, saveContext() will be skipped";

        public static String getUnexpectedTypeOfAuthPrincipalLog(String principalName){
            return String.format("securityContext.authentication.principal of unexpected type %s, saveContext() will be skipped", principalName);
        }

        public static String getSecurityContextPrincipalSavedLog(String userName) {
            return String.format("SecurityContext for principal %s saved in web key (cookie)", userName);
        }
    }

    public static final class AmazonCognitoAuthenticationProviderLogs {
        private AmazonCognitoAuthenticationProviderLogs() {
        }

        public static final String USER_CREDENTIALS_ARE_VALID = "Valid response from AdminInitiateAuth API";
        public static final String AUTHENTICATION_INSTANCE    = "Authentication instanceof UsernamePasswordAuthenticationToken";
        public static final String USER_NOT_FOUND             = "User does not exist in the user pool";

    }

    public static final class AWSCognitoUtilExceptionMessages {
        private AWSCognitoUtilExceptionMessages() {
        }

        public static final String ERROR_WHILE_CALCULATING_SECRET_HASH = "Error while calculating secret hash : ";
    }

    public static final class AESUtilExceptionMessages {
        private AESUtilExceptionMessages() {
        }

        public static final String ERROR_WHILE_ENCRYPTING = "Error while encrypting : ";
        public static final String ERROR_WHILE_DECRYPTING = "Error while decrypting : ";
    }

    public static final class HMACUtilExceptionMessages {
        private HMACUtilExceptionMessages() {
        }

        public static final String ERROR_WHILE_CALCULATING_HMAC = "Failed to calculate hmac-sha256 : ";
    }

    public static final class CookieFinderServiceMessages {
        private CookieFinderServiceMessages() {
        }

        public static String getNoCookieInRequestLog(String webKeyName){
            return String.format("No cookie named \"%s\" in request", webKeyName);
        }
    }

    public static final class TiptapWebKeyAuthFilterMessages {
        private TiptapWebKeyAuthFilterMessages() {
        }

        public static final String TIPTAP_WEB_KEY_FOUND       = "_tiptap_web_key found in cookies!";
        public static final String TIPTAP_WEB_KEY_NOT_FOUND   = "_tiptap_web_key not found in cookies!";
        public static final String TIPTAP_WEB_KEY_DELETED     = "_tiptap_web_key deleted from cookies!";
        public static final String TIPTAP_WEB_KEY_ADDED       = "_tiptap_web_key added to cookies!";
    }

    public static final class UsernamePasswordAuthFilterMessages {
        private UsernamePasswordAuthFilterMessages() {
        }
        public static String getLoginCredentialsReceivedLog(String username){
            return String.format("Login credentials received, username: %s", username);
        }
    }

    public static final class CustomSuccessHandlerMessages {
        private CustomSuccessHandlerMessages() {
        }

        public static final String AUTHENTICATION_IS_SUCCESSFUL              = "Authentication is successful";
        public static final String AUTHENTICATION_INSTANCE_AMAZONCOGNITOUSER = "Authentication is instance of AmazonCognitoUser";
        public static final String REDIRECT_URL                              = "/";
        public static final String AUTHENTICATION_INSTANCE_UNEXPECTED        = "Unexpected authentication instance occurred";
    }

    public static final class AmazonCognitoAuthenticationProviderMessages {
        private AmazonCognitoAuthenticationProviderMessages() {
        }
        public static final String AUTHENTICATION_REQUESTED     = "Authentication requested";
        public static final String USER_CREDENTIALS_ARE_VALID   = "User Credentials are valid";
        public static final String USER_CREDENTIALS_ARE_INVALID = "User Credentials are invalid";
        public static final String USER_NOT_FOUND               = "User does not found in Amazon Cognito User Pool";
        public static final String AUTHENTICATION_IS_NULL       = "Authentication is null";
    }

    public static final class NavigationPanelConstants {
        private NavigationPanelConstants() {
        }

        public static final String ATTRIBUTE_NAVIGATION_PANEL = "navigationPanel";
    }

    public static final class NotificationConstants {
        private NotificationConstants() {
        }

        public static final String ATTRIBUTE_TAX_RECEIPT_REQUEST_SUMMARY = "taxReceiptRequestSummary";
        public static final String ATTRIBUTE_SELECTED_TAX_RECEIPT_REQUEST = "selectedTaxReceiptRequest";
        public static final String ATTRIBUTE_SELECTED_TAX_RECEIPT_REQUEST_SUMMARY = "selectedTaxReceiptRequestSummary";
        public static final String ATTRIBUTE_APPROVED_TRANSACTION_FOR_SELECTED_REQUEST = "approvedTransactionsForSelectedRequest";
        public static final String ATTRIBUTE_LIST_OF_NEW_ENTRIES = "listOfNewEntries";
        public static final String ATTRIBUTE_LIST_OF_SEEN_ENTRIES = "listOfSeenEntries";
        public static final String ATTRIBUTE_LIST_OF_DONE_ENTRIES = "listOfDoneEntries";
        public static final String ATTRIBUTE_DEFAULT_WEB_PAGE_DETAILS = "defaultWebPageDetails";
        public static final String ATTRIBUTE_IS_SUBSCRIBED = "isSubscribed";
        public static final String ATTRIBUTE_ACTIVE_LINK_FOR_NOTIFICATIONS = "activeLinkForNotifications";
        public static final String ATTRIBUTE_ACTIVE_LINK_FOR_SUPPORT = "activeLinkForSupport";
        public static final String ATTRIBUTE_CURRENT_PAGE_OF_SEEN = "currentPageOfSeen";
        public static final String ATTRIBUTE_TOTAL_PAGE_OF_SEEN = "totalPagesOfSeen";
        public static final String ATTRIBUTE_NUMBER_OF_NEW_ENTRIES = "numberOfNewEntries";
        public static final String ATTRIBUTE_IS_EMAIL_OBFUSCATED = "isEmailObfuscated";
        public static final String ATTRIBUTE_CURRENT_YEAR = "currentYear";
        public static final String ATTRIBUTE_DONOR_FIRST_NAME = "donorFirstName";
        public static final String ATTRIBUTE_CURRENT_PAGE_OF_NEW = "currentPageOfNew";
        public static final String ATTRIBUTE_TOTAL_PAGES_OF_NEW = "totalPagesOfNew";
        public static final String ATTRIBUTE_CURRENT_PAGE_OF_DONE = "currentPageOfDone";
        public static final String ATTRIBUTE_TOTAL_PAGES_OF_DONE = "totalPagesOfDone";
        public static final String ATTRIBUTE_CURRENT_PAGE = "currentPage";
        public static final String ATTRIBUTE_TOTAL_PAGES = "totalPages";


        public static final String VIEW_NOTIFICATIONS = "notification/notifications";
        public static final String VIEW_TAX_RECEIPT_NOTIFICATIONS = "notification/taxreceiptrequest/taxReceiptRequestNotifications";
        public static final String VIEW_PROVIDED_TAX_RECEIPTS = "notification/taxreceiptrequest/providedTaxReceiptRequests";
        public static final String VIEW_TAX_RECEIPT_NOTIFICATION_DETAILS = "notification/taxreceiptrequest/taxReceiptRequestNotificationDetails";
        public static final String VIEW_TAX_RECEIPT_NOTIFICATION_NOT_FOUND = "notification/taxreceiptrequest/taxReceiptRequestNotificationNotFound";
        public static final String VIEW_TAX_RECEIPT_EDIT_DONOR_CA = "notification/taxreceiptrequest/taxReceiptRequestNotificationEditDonorCA";
        public static final String VIEW_TAX_RECEIPT_EDIT_DONOR_US = "notification/taxreceiptrequest/taxReceiptRequestNotificationEditDonorUS";
        public static final String VIEW_BASIC_REPORTING_TAX_RECEIPT_EDIT_DONOR_CA = "basicreporting/notification/taxreceiptrequest/basicReportingTaxReceiptRequestNotificationEditDonorCA";
        public static final String VIEW_BASIC_REPORTING_TAX_RECEIPT_EDIT_DONOR_US = "basicreporting/notification/taxreceiptrequest/basicReportingTaxReceiptRequestNotificationEditDonorUS";
        public static final String VIEW_SEEN_TAX_RECEIPT_REQUEST = "notification/taxreceiptrequest/seenTaxReceiptRequest";
        public static final String VIEW_NEW_TAX_RECEIPT_REQUEST = "notification/taxreceiptrequest/newTaxReceiptRequest";

        public static final String LOG_TAX_RECEIPT_REQUEST_NOT_FOUND = "Tax receipt request is not found";

        public static final String DEFAULT_PAGE_REQUEST_SIZE = "50";

        public static String redirectToNotificationsByOrganizationId(long organizationId){
            return String.format("redirect:/companies/%s/notifications", organizationId);
        }

        public static String redirectToTaxReceiptRequestsByOrganizationId(long organizationId){
            return String.format("redirect:/companies/%s/notifications/tax-receipt-requests", organizationId);
        }

        public static String redirectToBasicReportingTaxReceiptRequestsByOrganizationId(long organizationId){
            return String.format("redirect:/companies/%s/basic_reporting/notifications/tax-receipt-requests", organizationId);
        }

        public static String redirectToNewTaxReceiptRequestsByOrganizationId(long organizationId){
            return String.format("redirect:/companies/%s/notifications/tax-receipt-requests/new", organizationId);
        }

        public static String redirectToBasicReportingNewTaxReceiptRequestsByOrganizationId(long organizationId){
            return String.format("redirect:/companies/%s/basic_reporting/notifications/tax-receipt-requests/new", organizationId);
        }
    }

    public static final class ToolsConstants {
        private ToolsConstants() {
        }

        public static final String VIEW_TOOLS = "tools/tools";
    }

    public static final class QRCodeGeneratorConstants {
        private QRCodeGeneratorConstants() {
        }

        public static final String QR_CODE_FORMAT = "PNG";
        public static final String FILE_NAME_INITIAL = "qr_code_";
        public static final Integer DEFAULT_PIXEL_VALUE = 600;
        public static final Integer MIN_PIXEL_VALUE = 300;
        public static final Integer MAX_PIXEL_VALUE = 1000;
        public static final Integer DEFAULT_FRAME_SIZE = 30;

        public static final String ATTRIBUTE_FORM_DATA = "formData";
        public static final String ATTRIBUTE_COMPANIES = "companies";
        public static final String ATTRIBUTE_QR_CODE = "qrCode";

        public static final String VIEW_INDEX = "index";

        public static final String REDIRECT_QR_CODE_GENERATOR = "redirect:/qr-code-generator";

        public static final String PARAMETER_COMPANY_ID = "companyId";

        public static final String COLOR_HEX_TIPTAP_PURPLE = "#402253";
        public static final String COLOR_HEX_BLACK = "#000000";
        public static final String COLOR_HEX_WHITE = "#FFFFFF";

        public static final String FIELD_ID = "id";
        public static final String FIELD_NAME = "name";

        public static final String CLASSPATH_TIPTAP_LOGO_CIRCLE = "classpath:static/images/athenajava/tiptap_logo_circle.png";

        public static final String BASE_URL_FOR_TAX_RECEIPT_REQUEST = "app-dev.tiptappay.ca";

        public static final String VIEW_QR_CODE_GENERATOR = "tools/qrcodegenerator/qrCodeGenerator";

        public static final String ATTRIBUTE_QR_CODE_GENERATOR_FORM_DATA = "qrCodeGeneratorFormData";

        public static String redirectToToolsByOrganizationId(long organizationId){
            return String.format("redirect:/companies/%s/tools", organizationId);
        }
    }

    public static final class TiptapWebKeyServiceLogs {
        private TiptapWebKeyServiceLogs() {
        }

        public static final String MEMBERSHIP_DETAILS_CREATED = "Membership details created.";
        public static final String NO_MEMBERSHIP_DETAILS = "No membership details";
        public static final String USER_DETAILS_CREATED = "User details created.";
        public static final String NO_USER_DETAILS = "No user details";
    }

    public static final class TaxReceiptRequestServiceConstants {
        private TaxReceiptRequestServiceConstants() {
        }

        public static final String ADMIN_HTML_TEMPLATE = "forms/requestfortaxreceipt/email/confirmationEmailTemplateAdmin.html";
        public static final String ADMIN_HTML_TEMPLATE_BASIC = "forms/requestfortaxreceipt/email/confirmationEmailTemplateAdminBasic.html";
        public static final String USER_HTML_TEMPLATE = "forms/requestfortaxreceipt/email/confirmationEmailTemplateUser.html";
        public static final String USER_HTML_TEMPLATE_BASIC = "forms/requestfortaxreceipt/email/confirmationEmailTemplateUserBasic.html";

        public static final String TAX_RECEIPT_REQUEST_FORM_CA = "forms/requestfortaxreceipt/taxReceiptRequestFormCA";
        public static final String TAX_RECEIPT_REQUEST_FORM_US = "forms/requestfortaxreceipt/taxReceiptRequestFormUS";
        public static final String TAX_RECEIPT_REQUEST_FORM_BASIC = "forms/requestfortaxreceipt/taxReceiptRequestFormBasic";

    }

    public static final class TRRDataExporterConstants {
        private TRRDataExporterConstants() {
        }

        public static final String ERROR_LOG_CARD_TYPE_CANNOT_READ = "Card Type cannot be found or read!";

    }

    public static final class SubscriptionConstants {
        private SubscriptionConstants() {
        }

        public static final String ATTRIBUTE_CREATE_ORGANIZATION_WRAPPER = "createOrganizationWrapper";
    }

    public static final class AdminOrganizationConstants {
        private AdminOrganizationConstants() {
        }

        public static final String VIEW_ADD_ORGANIZATION = "admin/organization/add_organization";
        public static final String VIEW_EDIT_ORGANIZATION = "admin/organization/edit_organization";
        public static final String VIEW_VIEW_ORGANIZATION = "admin/organization/view_organization";

        public static String redirectToShowViewCompanyByOrganizationId(long organizationId){
            return String.format("redirect:/admin/companies/%s", organizationId);
        }
    }

    public static final class TermsAndConditionsAgreementConstants {
        private TermsAndConditionsAgreementConstants() {
        }

        public static final String ATTRIBUTE_TERMS_AND_CONDITIONS_AGREEMENT_BASE = "termsAndConditionsAgreementBase";
    }

    public static final class TerminalProfileConstants {
        private TerminalProfileConstants() {
        }

        public static final String ATTRIBUTE_NUMBER_OF_TERMINAL_PROFILE = "numberOfEmptyTerminalProfile";
    }

    public static final class TiptapMposConstants {
        private TiptapMposConstants() {
        }

        public static final String ATTRIBUTE_UNUSED_TIPTAP_LIST = "unusedTiptapList";
        public static final String ATTRIBUTE_UNUSED_TIPTAP_GROUP_LIST = "unusedTiptapGroupList";
    }

    public static final class BaseUrlHelper {

        private BaseUrlHelper() {
        }

        public static String redirectToBaseUrl(String baseUrl, String pathComponent){
            return String.format("redirect:%s%s", baseUrl, pathComponent);
        }
    }

    public static final class RequestForTaxReceiptControllerConstants {
        private RequestForTaxReceiptControllerConstants() {
        }

        public static final String VIEW_TAX_RECEIPT_REQUEST_NOT_FOUND = "forms/requestfortaxreceipt/taxReceiptRequestNotFound";
        public static final String VIEW_TAX_RECEIPT_REQUEST_SUCCESS = "forms/requestfortaxreceipt/taxReceiptRequestSuccess";
        public static final String VIEW_TAX_RECEIPT_REQUEST_BASIC_SUCCESS = "forms/requestfortaxreceipt/taxReceiptRequestFormBasicSuccess";
        public static final String VIEW_TAX_RECEIPT_REQUEST_ERROR = "forms/requestfortaxreceipt/taxReceiptRequestError";
        public static final String VIEW_TAX_RECEIPT_REQUEST_BASIC_ERROR = "forms/requestfortaxreceipt/taxReceiptRequestFormBasicError";
        public static final String ATTRIBUTE_TAX_RECEIPT_REQUEST = "taxReceiptRequest";
        public static final String ATTRIBUTE_ORGANIZATION_NAME = "organizationName";
        public static final String LOG_SAME_REQUEST_BY_SAME_PERSON = "Same request by same person : ";
    }

    public static final class TransactionResponseFields {
        private TransactionResponseFields() {
        }

        public static final class Moneris {
            private Moneris() {
            }
            public static final String FIELD_CARD_TYPE = "CardType";

            public static final class CardType {

                /**
                 * @link <a href="https://developer.moneris.com/Documentation/NA/E-Commerce%20Solutions/API/Response%20Fields">Moneris - Transaction Response Fields</a>
                 */
                private CardType() {
                }

                public static final String MASTERCARD_KEY = "M";
                public static final String MASTERCARD_VALUE = "Mastercard";

                public static final String VISA_KEY = "V";
                public static final String VISA_VALUE = "Visa";

                public static final String AMEX_KEY = "AX";
                public static final String AMEX_VALUE = "American Express";

                public static final String NOVUS_DISCOVER_KEY = "NO";  // Canada Only
                public static final String NOVUS_DISCOVER_VALUE = "Novus/Discover";  // Canada Only

                public static final String INTERAC_KEY = "P";  // Canada Only
                public static final String INTERAC_VALUE = "Interac";  // Canada Only

                public static final String DEBIT_KEY = "D";  // Canada Only
                public static final String DEBIT_VALUE = "Debit";  // Canada Only

                public static final String JCB_KEY = "C1";  // Canada Only
                public static final String JCB_VALUE = "JCB";  // Canada Only

                public static final String SEARS_KEY = "SE";  // Canada Only
                public static final String SEARS_VALUE = "Sears";  // Canada Only
            }
        }

        public static final class GlobalPayments {
            private GlobalPayments() {
            }
            public static final String FIELD_CARD_TYPE = "cardType";

            /**
             * @link <a href="https://developer.globalpay.com/resources/test-card-numbers">GlobalPayments - Card type strings</a>
             */
            public static final class CardType {

                private CardType() {
                }

                public static final String MASTERCARD_KEY = "MC";
                public static final String MASTERCARD_VALUE = "Mastercard";

                public static final String VISA_KEY = "VISA";
                public static final String VISA_VALUE = "Visa";

                public static final String AMEX_KEY = "AMEX";
                public static final String AMEX_VALUE = "American Express";

                public static final String DEBIT_KEY = "DEBIT";
                public static final String DEBIT_VALUE = "Debit";

                public static final String DINERS_KEY = "DINERS";
                public static final String DINERS_VALUE = "Diners";

                public static final String JCB_KEY = "JCB";
                public static final String JCB_VALUE = "JCB";

                public static final String UATP_KEY = "UATP";
                public static final String UATP_VALUE = "UATP";
            }
        }

        public static final class Elavon {
            private Elavon() {
            }
            public static final String FIELD_ELAVON_BLOCK_02 = "elavonBlock02";
            public static final String FIELD_ELAVON_ASSOCIATION_NAME = "Association_Name";
        }

        public static final class CyberSource {
            private CyberSource() {
            }
            public static final String FIELD_PAYMENT_ACCOUNT_INFORMATION = "paymentAccountInformation";
            public static final String FIELD_CARD = "card";
            public static final String FIELD_TYPE = "type";

            /**
             * @link <a href="https://developer.cybersource.com/docs/cybs/en-us/apifields/reference/all/rest/api-fields/payment-info/payment-info-card-card-type.html">Cybersource - paymentInformation. card.cardType</a>
             */
            public static final class Type {

                private Type() {
                }

                public static final String VISA_KEY = "001";
                public static final String VISA_VALUE = "Visa";

                public static final String MASTERCARD_EUROCARD_KEY = "002";
                public static final String MASTERCARD_EUROCARD_VALUE = "Mastercard and Eurocard";

                public static final String AMEX_KEY = "003";
                public static final String AMEX_VALUE = "American Express";

                public static final String DISCOVER_KEY = "004";
                public static final String DISCOVER_VALUE = "Discover";

                public static final String DINERS_KEY = "005";
                public static final String DINERS_VALUE = "Diners Club";

                public static final String JCB_KEY = "007";
                public static final String JCB_VALUE = "JCB";

                public static final String UATP_KEY = "040";
                public static final String UATP_VALUE = "UATP";
            }
        }

    }

    public static final class TransactionStatusConstants {
        private TransactionStatusConstants() {
        }

        public static final String APPROVED = "approved";
        public static final String DECLINED = "declined";
    }

    public static final class SupportFormOrigins {
        private SupportFormOrigins() {
        }

        public static final String DASHBOARD = "Dashboard";
        public static final String OTHER = "Other";
    }

    public static final class ApiErrorCodes {
        private ApiErrorCodes() {
        }

        public static final String API_ERROR_CODE_MISSING_SIGNATURE_HEADER = "MISSING_SIGNATURE_HEADER";
        public static final String API_ERROR_CODE_EMPTY_REQUEST_BODY = "EMPTY_REQUEST_BODY";
        public static final String API_ERROR_CODE_MISSING_FIELDS = "MISSING_FIELDS";
        public static final String API_ERROR_CODE_INVALID_TIMESTAMP = "INVALID_TIMESTAMP";
        public static final String API_ERROR_CODE_EXPIRED_TIMESTAMP = "EXPIRED_TIMESTAMP";
        public static final String API_ERROR_CODE_INVALID_SIGNATURE = "INVALID_SIGNATURE";
        public static final String API_ERROR_CODE_INVALID_STATUS = "INVALID_STATUS";
        public static final String API_ERROR_CODE_DEVICE_NOT_FOUND = "DEVICE_NOT_FOUND";
        public static final String API_ERROR_CODE_CACHE_STATUS_UPDATE_ERROR = "CACHE_STATUS_UPDATE_ERROR";
        public static final String API_ERROR_CODE_DB_STATUS_UPDATE_ERROR = "DB_STATUS_UPDATE_ERROR";
        public static final String API_ERROR_CODE_DB_STATUS_UPDATE_UNSUCCESSFUL = "DB_STATUS_UPDATE_UNSUCCESSFUL";
    }

    public static final class ApiErrorMessages {
        private ApiErrorMessages() {
        }

        public static final String API_ERROR_MESSAGE_DEVICE_NOT_FOUND = "Device not found";
        public static final String API_ERROR_MESSAGE_MISSING_SIGNATURE_HEADER = "Missing signature header";
        public static final String API_ERROR_MESSAGE_EMPTY_REQUEST_BODY = "Empty request body";
        public static final String API_ERROR_MESSAGE_MISSING_EMPTY_FIELDS = "Missing or empty fields: ";
        public static final String API_ERROR_MESSAGE_INVALID_TIMESTAMP = "Invalid timestamp";
        public static final String API_ERROR_MESSAGE_EXPIRED_TIMESTAMP = "Expired timestamp";
        public static final String API_ERROR_MESSAGE_INVALID_SIGNATURE = "Invalid signature";
        public static final String API_ERROR_MESSAGE_UNABLE_TO_FIND_SN = "Unable to find device with serial number %s.";
        public static final String API_ERROR_MESSAGE_FAILED_TO_UPDATE_STATUS_CACHE = "Failed to update status in cache";
        public static final String API_ERROR_MESSAGE_FAILED_TO_UPDATE_STATUS_DB = "Failed to update status in database";
        public static final String API_ERROR_MESSAGE_DB_UPDATE_FAILED = "Database update was unsuccessful";
        public static final String API_ERROR_MESSAGE_STATUS_UPDATED_SUCCESSFULLY = "Status updated successfully";
    }

    public static final class SupportConstants {
        private SupportConstants() {}

        public static final String ATTRIBUTE_SUPPORT_SUPPORT_CASE_FORM = "supportCaseForm";
        public static final String ATTRIBUTE_SUPPORT_DEVICE_BASE_LIST = "deviceBaseList";
        public static final String ATTRIBUTE_SUPPORT_INVOICE =  "invoice";
        public static final String ATTRIBUTE_SUPPORT_SALES_ORDERS = "salesOrders";
        public static final String ATTRIBUTE_SUPPORT_SUPPORT_CASE_RESPONSE_JSON = "supportCaseResponseJson";
        public static final String ATTRIBUTE_SUPPORT_RESPONSE = "response";
    }

    public static final class PaymentPageConstants {
        private PaymentPageConstants() {}

        public static final String HTML_TEMPLATE_BASIC_PAYMENT_PAGE = "paymentpage/basic-payment-page-email-template.html";
    }
}
