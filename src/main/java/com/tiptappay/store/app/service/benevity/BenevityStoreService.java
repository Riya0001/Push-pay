package com.tiptappay.store.app.service.benevity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiptappay.store.app.constant.AppConstants;
import com.tiptappay.store.app.dto.benevity.store.BenevityCause;
import com.tiptappay.store.app.dto.benevity.store.MyAccountToken;
import com.tiptappay.store.app.dto.bundles.store.CartData;
import com.tiptappay.store.app.dto.bundles.store.CheckoutData;
import com.tiptappay.store.app.dto.bundles.store.PaymentData;
import com.tiptappay.store.app.dto.bundles.wrapper.BundleOrderWrapper;
import com.tiptappay.store.app.model.CustomHttpResponse;
import com.tiptappay.store.app.model.ResponsePayment;
import com.tiptappay.store.app.model.SalesRep;
import com.tiptappay.store.app.model.payload.Payload;
import com.tiptappay.store.app.service.CookieService;
import com.tiptappay.store.app.service.CustomHttpService;
import com.tiptappay.store.app.service.app.AppService;
import com.tiptappay.store.app.service.app.CustomLoggerService;
import com.tiptappay.store.app.service.email.EmailService;
import com.tiptappay.store.app.service.product.BundleProductService;
import com.tiptappay.store.app.util.AppUtil;
import com.tiptappay.store.app.util.DataUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.tiptappay.store.app.constant.AppConstants.CookiesConstants.COOKIE_MY_ACCOUNT_TOKEN;
import static com.tiptappay.store.app.constant.AppConstants.EmailConstants.TEMPLATE_KEY_MY_ACCOUNT_TOKEN;
import static com.tiptappay.store.app.constant.AppConstants.EmailConstants.TEMPLATE_KEY_SUBJECT;
import static com.tiptappay.store.app.constant.AppConstants.HttpConstants.*;

@Service
@RequiredArgsConstructor
public class BenevityStoreService {
    private final CustomLoggerService logger;
    private final CustomHttpService customHttpService;
    private final CookieService cookieService;
    private final EmailService emailService;
    private final AppService app;
    private final BundleProductService bundleProductService;
    private static final String PRODUCTION_BENEVITY_SO_FORM_INTERNAL_ID = "182";
    private static final String SANDBOX_BENEVITY_SO_FORM_INTERNAL_ID = "183";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final List<SalesRep> CONSTANT_SALES_REPS;

    static {
        CONSTANT_SALES_REPS = List.of(
                new SalesRep("101", "Rizek, Tariq", "mark@tiptappay.com"),
                new SalesRep("102", "Mark Jordan", "mark@tiptappay.com"),
                new SalesRep("104", "Andrew Brousse", "andrew@tiptappay.com"),
                new SalesRep("105", "Henrietta Sung", "henrietta@tiptappay.com"),
                new SalesRep("106", "Robert McGarry", "robert@tiptappay.com"),
                new SalesRep("107", "Parker, Jason", "jason@tiptappay.com")
        );
    }

    public String getSalesRepNameById(String id) {
        return SalesRep.getNameById(CONSTANT_SALES_REPS, id);
    }

    public String getSalesRepEmailById(String id) {
        return SalesRep.getEmailById(CONSTANT_SALES_REPS, id);
    }

    public boolean isSalesRepIdExist(String id) {
        return SalesRep.isIdExist(CONSTANT_SALES_REPS, id);
    }

    public boolean isValidPayment(ResponsePayment payment) {
        if (payment == null) {
            return false;
        }
        return (payment.isSuccess() && payment.getSoNumber() != null && payment.isCcApproved());
    }

    public Payload preparePayload(
            String orderFormType,
            String salesRepId,
            CheckoutData checkoutData,
            List<CartData> cartDataList,
            PaymentData paymentData,
            String action) {

        // Make server side correction if any case
        cartDataList = bundleProductService.setBundlesPriceKeyAndCalculate(cartDataList);

        Payload payload = new Payload();
        try {
            // Set Header
            Payload.Header header = new Payload.Header();
            header.setTimestamp(new Date());

            header.setOrderFormType(orderFormType);

            if (AppConstants.NetsuiteEnvironmentOptions.PRODUCTION.equals(app.getNetsuiteEnvironment())) {
                header.setNsSOFormInternalID(PRODUCTION_BENEVITY_SO_FORM_INTERNAL_ID);
            } else {
                header.setNsSOFormInternalID(SANDBOX_BENEVITY_SO_FORM_INTERNAL_ID);
            }

            payload.setHeader(header);

            // Set Contact Information
            Payload.ContactInformation contactInformation = getContactInformation(checkoutData);
            payload.setContactInformation(contactInformation);

            // Set Order Details
            BundleOrderWrapper bundleOrderWrapper = bundleProductService.getOrderDetails(cartDataList);
            payload.setOrderDetails(bundleOrderWrapper.getOrderDetails());
            payload.setBundleOrders(bundleOrderWrapper.getBundleOrders());

            // Set Pricing
            Payload.Pricing pricing = bundleProductService.getPricing(checkoutData);
            payload.setPricing(pricing);

            // Set Billing
            Payload.Billing billing = getBilling(checkoutData);
            payload.setBilling(billing);

            // Set Shipping
            Payload.Shipping shipping = getShipping(checkoutData);
            payload.setShipping(shipping);

            // Set Customer Information
            Payload.CustomerInformation customerInformation = getCustomerInformation(checkoutData);
            payload.setCustomerInformation(customerInformation);

            // Set Organization Hierarchy
            Payload.OrganizationHierarchy organizationHierarchy = getOrganizationHierarchy();
            payload.setOrganizationHierarchy(organizationHierarchy);

            // Set Customer Category
            Payload.CustomerCategory customerCategory = getCustomerCategory(salesRepId);
            payload.setCustomerCategory(customerCategory);

            if (action != null) {

                if (action.equals(AppConstants.NetSuiteActions.ACTION_COMPLETE_ORDER_WITH_PAYMENT)) {

                    // Set Program Details
                    Payload.ProgramDetails programDetails = getProgramDetails();
                    payload.setProgramDetails(programDetails);

                    // Set Payment Information
                    Payload.PaymentInformation paymentInformation = getPaymentInformation(paymentData);
                    payload.setPaymentInformation(paymentInformation);
                }

                payload.setAction(action);
            }

            // Set Memo
            payload.setMemo("");

        } catch (Exception exception) {
            logger.logError(AppConstants.AppMessages.ERROR_PREPARING_PAYLOAD, exception);
        }
        return payload;
    }

    private Payload.ContactInformation getContactInformation(CheckoutData checkoutData) {
        Payload.ContactInformation contactInformation = new Payload.ContactInformation();
        contactInformation.setContactFirstName(checkoutData.getShippingFullName().split(" ", 2)[0]);
        contactInformation.setContactLastName(checkoutData.getShippingFullName().split(" ", 2)[1]);
        contactInformation.setContactPhone("+1 " + checkoutData.getPhone());
        contactInformation.setContactEmail(checkoutData.getEmail());
        contactInformation.setContactPrefContactMethod("Phone");
        return contactInformation;
    }

    private Payload.Shipping getShipping(CheckoutData checkoutData) {
        Payload.Shipping shipping = new Payload.Shipping();

        if (checkoutData == null) {
            return shipping;
        }

        shipping.setAttentionTo(checkoutData.getShippingFullName());
        shipping.setShippingAddr1(checkoutData.getShippingAddressLine1());
        shipping.setShippingAddr2(checkoutData.getShippingAddressLine2());
        shipping.setShippingCity(checkoutData.getShippingCity());
        shipping.setShippingState(checkoutData.getShippingState());
        shipping.setShippingCountry(checkoutData.getShippingCountry());
        shipping.setShippingZip(checkoutData.getShippingZipCode());
        shipping.setShippingPhone("+1 " + checkoutData.getShippingPhone());
        shipping.setShippingEmail(checkoutData.getEmail());

        return shipping;
    }

    private Payload.Billing getBilling(CheckoutData checkoutData) {
        Payload.Billing billing = new Payload.Billing();

        if (checkoutData == null) {
            return billing;
        }

        billing.setBillingFirstName(checkoutData.getBillingFullName().split(" ", 2)[0].trim());
        billing.setBillingLastName(checkoutData.getBillingFullName().split(" ", 2)[1].trim());
        billing.setBillingAddr1(checkoutData.getBillingAddressLine1());
        billing.setBillingAddr2(checkoutData.getBillingAddressLine2());
        billing.setBillingCity(checkoutData.getBillingCity());
        billing.setBillingState(checkoutData.getBillingState());
        billing.setBillingCountry(checkoutData.getBillingCountry());
        billing.setBillingZip(checkoutData.getBillingZipCode());
        billing.setBillingPhone("+1 " + checkoutData.getBillingPhone());
        billing.setBillingEmail(checkoutData.getEmail());

        return billing;
    }

    private Payload.CustomerInformation getCustomerInformation(CheckoutData checkoutData) {
        Payload.CustomerInformation customerInformation = new Payload.CustomerInformation();

        customerInformation.setCustomerName(checkoutData.getOrganizationName());
        customerInformation.setCustomerPhone("+1 " + checkoutData.getPhone());
        customerInformation.setCustomerEmail(checkoutData.getEmail());
        customerInformation.setCustomerPrefContactMethod("Phone");
        customerInformation.setCampaignStartDate(checkoutData.getCampaignStartDate());
        return customerInformation;
    }

    private Payload.OrganizationHierarchy getOrganizationHierarchy() {
        Payload.OrganizationHierarchy organizationHierarchy = new Payload.OrganizationHierarchy();

        organizationHierarchy.setParent(0);
        organizationHierarchy.setChild(0);
        organizationHierarchy.setGrandChild(0);

        return organizationHierarchy;
    }

    private Payload.CustomerCategory getCustomerCategory(String salesRepId) {
        Payload.CustomerCategory customerCategory = new Payload.CustomerCategory();
        boolean isSalesRepExist = isSalesRepIdExist(salesRepId);

        if (salesRepId != null && isSalesRepExist) {
            customerCategory.setCustomerType("Enterprise");
            customerCategory.setCustomerReferral(getSalesRepNameById(salesRepId));
        } else {
            customerCategory.setCustomerType("");
            customerCategory.setCustomerReferral("");
        }

        return customerCategory;
    }

    private Payload.ProgramDetails getProgramDetails() {
        Payload.ProgramDetails programDetails = new Payload.ProgramDetails();
        programDetails.setPartnerSponsoring("");
        programDetails.setSponsorshipBeneficiary("");
        programDetails.setProgramName("");
        programDetails.setProgramLeadName("");
        programDetails.setProgramLeadPhone("");
        programDetails.setProgramLeadEmail("");
        return programDetails;
    }

    private Payload.PaymentInformation getPaymentInformation(PaymentData paymentData) {
        Payload.PaymentInformation paymentInformation = new Payload.PaymentInformation();
        paymentInformation.setNameOnCard(paymentData.getNameOnCard().trim());
        paymentInformation.setCardNumber(paymentData.getCardNumber().replaceAll(" ", "").trim());
        paymentInformation.setExpirationDate(paymentData.getExpiration());
        paymentInformation.setCvv(paymentData.getCvv());
        return paymentInformation;
    }

    private List<BenevityCause> processMultipleCause(String response) {

        List<BenevityCause> benevityCauseList = new ArrayList<>();

        try {
            Map<String, Object> benevityMap = OBJECT_MAPPER.readValue(response, new TypeReference<>() {
            });

            List<Map<String, Object>> causes = (List<Map<String, Object>>) benevityMap.get("benevity_causes");

            if (causes != null) {
                for (Map<String, Object> map : causes) {
                    BenevityCause cause = new BenevityCause();

                    cause.setId(AppUtil.mapValueToString(map.get("id")));
                    Map<String, Object> attributes = (Map<String, Object>) map.get("attributes");

                    if (attributes != null && !attributes.isEmpty()) {
                        cause.setName(AppUtil.mapValueToString(attributes.get("name")));
                        cause.setLogoUrl(AppUtil.mapValueToString(attributes.get("logo")));
                    }

                    // add to list
                    benevityCauseList.add(cause);
                }
            }

            return benevityCauseList;

        } catch (Exception e) {
            logger.logError("Failed to parse multiple benevity causes", e);
        }
        return null;
    }

    private BenevityCause processSingleCause(String response) {
        BenevityCause cause = new BenevityCause();

        try {
            Map<String, Object> benevityMap = OBJECT_MAPPER.readValue(response, new TypeReference<>() {
            });

            Map<String, Object> map = (Map<String, Object>) benevityMap.get("benevity_cause");

            if (map != null) {
                cause.setId(map.get("id").toString());
                Map<String, Object> attributes = (Map<String, Object>) map.get("attributes");

                if (attributes != null && !attributes.isEmpty()) {
                    cause.setName(AppUtil.mapValueToString(attributes.get("name")));

                    String phone = AppUtil.normalizePhone(AppUtil.mapValueToString(attributes.get("phone")));
                    cause.setPhone(phone);

                    Map<String, Object> address = (Map<String, Object>) attributes.get("address");

                    if (address != null && !address.isEmpty()) {
                        BenevityCause.Address benevityCauseAddress = new BenevityCause.Address();

                        benevityCauseAddress.setCity(AppUtil.mapValueToString(address.get("city")));
                        benevityCauseAddress.setZip(AppUtil.mapValueToString(address.get("postcode")));
                        benevityCauseAddress.setStreet(AppUtil.mapValueToString(address.get("street")));

                        Map<String, Object> country = (Map<String, Object>) address.get("country");
                        Map<String, Object> region = (Map<String, Object>) address.get("region");

                        if (country != null && !country.isEmpty()) {
                            benevityCauseAddress.setCountry(AppUtil.mapValueToString(country.get("name")));
                        }

                        if (region != null && !region.isEmpty()) {
                            benevityCauseAddress.setState(AppUtil.mapValueToString(region.get("name")));
                        }

                        cause.setAddress(benevityCauseAddress);

                    }

                    List<Map<String, Object>> logos = (List<Map<String, Object>>) attributes.get("logos");

                    if (logos != null && !logos.isEmpty()) {
                        cause.setLogoUrl(AppUtil.mapValueToString(logos.get(0).get("url")));
                    }
                }
            }

            return cause;

        } catch (Exception e) {
            logger.logError("Failed to parse single benevity cause", e);
        }
        return null;
    }

    public BenevityCause getBenevityCause(String causeId) {
        logger.logInfo("Inside BenevityStoreService.getBenevityCause() > Cause ID: " + causeId);

        try {
            String encodedCauseId = URLEncoder.encode(causeId, StandardCharsets.UTF_8);
            String urlPath = "/" + app.getAthenaApiPrefix() + "/benevity/causes/" + encodedCauseId;

            Map<String, String> headers = Map.of(
                    HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
                    HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + app.getAthenaApiKey()
            );

            CustomHttpResponse customHttpResponse = customHttpService.getRequest(app.getBaseUrl() + urlPath, headers);
            return processSingleCause(customHttpResponse.getResponseBody());

        } catch (Exception e) {
            logger.logError("Exception while getting Benevity cause", e);
        }
        return null;
    }

    public List<BenevityCause> searchBenevityCausesToList(String searchTerm) {
        logger.logInfo("Inside BenevityStoreService.searchBenevityCauses() > Search Term: " + searchTerm);

        try {
            String encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
            String urlPath = "/" + app.getAthenaApiPrefix() + "/benevity/causes/search?search_term=" + encodedSearchTerm;

            Map<String, String> headers = Map.of(
                    HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
                    HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + app.getAthenaApiKey()
            );

            CustomHttpResponse customHttpResponse = customHttpService.getRequest(app.getBaseUrl() + urlPath, headers);

            return processMultipleCause(customHttpResponse.getResponseBody());

        } catch (Exception e) {
            logger.logError("Exception while getting Benevity causes", e);
        }
        return null;
    }

    public String searchBenevityCauses(String searchTerm) {
        logger.logInfo("Inside BenevityStoreService.searchBenevityCauses2() > Search Term: " + searchTerm);

        try {
            String encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
            String urlPath = "/" + app.getAthenaApiPrefix() + "/benevity/causes/search?search_term=" + encodedSearchTerm + "&include_children=false";

            Map<String, String> headers = Map.of(
                    HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
                    HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + app.getAthenaApiKey()
            );

            CustomHttpResponse customHttpResponse = customHttpService.getRequest(app.getBaseUrl() + urlPath, headers);
            return customHttpResponse.getResponseBody();

        } catch (Exception e) {
            logger.logError("Exception while getting Benevity causes", e);
            return null;
        }
    }

    public void writeMyAccountToken(HttpServletResponse response, MyAccountToken myAccountToken, String tokenIdentifier) {
        String myAccountTokenJsonString = DataUtils.convertToJsonString(myAccountToken);
        cookieService.writeCookie(response, COOKIE_MY_ACCOUNT_TOKEN + tokenIdentifier, myAccountTokenJsonString);
    }

    public MyAccountToken readMyAccountToken(HttpServletRequest request, HttpServletResponse response, String tokenIdentifier) {
        String myAccountTokenJsonString = cookieService.readCookie(request, COOKIE_MY_ACCOUNT_TOKEN + tokenIdentifier);
        MyAccountToken myAccountToken = DataUtils.convertToObject(myAccountTokenJsonString, MyAccountToken.class);

        if (myAccountToken != null) {
            boolean isValid = validateTokenExpiry(myAccountToken.getTokenExp());

            if (isValid) {
                return myAccountToken;
            }
        }

        deleteMyAccountToken(response, tokenIdentifier);
        return null;
    }

    public void deleteMyAccountToken(HttpServletResponse response, String tokenIdentifier) {
        cookieService.deleteCookie(response, COOKIE_MY_ACCOUNT_TOKEN + tokenIdentifier);
    }

    public void sendMagicLinkEmail(MyAccountToken myAccountToken) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put(TEMPLATE_KEY_SUBJECT, "tiptap account created");
        templateModel.put(TEMPLATE_KEY_MY_ACCOUNT_TOKEN, myAccountToken);

        emailService.sendMessageUsingThymeleafTemplate(templateModel);
    }

    public boolean validateTokenExpiry(long expiryTime) {
        return expiryTime > AppUtil.getCurrentTimeMillis();
    }

    public boolean validateMyAccountToken(MyAccountToken myAccountToken, HttpServletResponse response, String tokenIdentifier) {
        boolean isValid = myAccountToken != null
                && myAccountToken.getOrganizationId() != Integer.MIN_VALUE
                && validateTokenExpiry(myAccountToken.getTokenExp());

        if (!isValid) {
            deleteMyAccountToken(response , tokenIdentifier);
        }

        return isValid;
    }

}
