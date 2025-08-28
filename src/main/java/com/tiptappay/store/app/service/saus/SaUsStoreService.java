package com.tiptappay.store.app.service.saus;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiptappay.store.app.constant.AppConstants;
import com.tiptappay.store.app.constant.SaUsOrganizationMap;
import com.tiptappay.store.app.dto.legion.CheckoutData;
import com.tiptappay.store.app.dto.saus.store.SaUsStoreShoppingBag;
import com.tiptappay.store.app.model.*;
import com.tiptappay.store.app.model.payload.Payload;
import com.tiptappay.store.app.service.CustomHttpService;
import com.tiptappay.store.app.service.app.AppService;
import com.tiptappay.store.app.service.app.CustomLoggerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.tiptappay.store.app.constant.AppConstants.CampaignConstants.DEFAULT_STATUS;
import static com.tiptappay.store.app.constant.AppConstants.HttpConstants.*;
import static com.tiptappay.store.app.constant.AppConstants.HttpConstants.HTTP_HEADER_NAME_AUTHORIZATION;
import static com.tiptappay.store.app.constant.StoreConstants.SaUsStore.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaUsStoreService {
    private final CustomLoggerService logger;
    private final AppService appService;
    private final CustomHttpService customHttpService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public static Payload preparePayload(String storeId,
                                         Object shoppingBag,
                                         ContactAndShipping contactAndShipping,
                                         Payment payment,
                                         String action) {

        try {
            Payload payload = new Payload();

            // Header
            Payload.Header header = new Payload.Header();
            header.setTimestamp(new Date());
            header.setOrderFormType("Salvation Army USA Order Form");
            header.setNsSOFormInternalID("181");
            payload.setHeader(header);

            // Contact
            payload.setContactInformation(getContactInformation(contactAndShipping));

            // Order Details
            SaUsStoreShoppingBag saUsStoreShoppingBag = (SaUsStoreShoppingBag) shoppingBag;
            Payload.OrderDetails orderDetails = new Payload.OrderDetails();
            orderDetails.setNumberOf2DollarDevices("0");
            orderDetails.setNumberOfClips("0");
            orderDetails.setNumberOfKettleDisplay("0");
            orderDetails.setNumberOfFloorStands("0");
            orderDetails.setNumberOf5DollarDevices(String.valueOf(saUsStoreShoppingBag.getKettleDisplayQty()));
            orderDetails.setNumberOf10DollarDevices(String.valueOf(saUsStoreShoppingBag.getKettleDisplayQty()));
            orderDetails.setNumberOf20DollarDevices(String.valueOf(saUsStoreShoppingBag.getKettleDisplayQty()));
            orderDetails.setNumberOfBatteryForKettleDisplay("0");
            payload.setOrderDetails(orderDetails);
            Payload.CustomerCategory customerCategory = new Payload.CustomerCategory();
            customerCategory.setCustomerType("Enterprise");
            payload.setCustomerCategory(customerCategory);

            // Pricing
            Payload.Pricing pricing = new Payload.Pricing();
            pricing.setCurrency(CURRENCY);
            pricing.setSetupFeeAmount(0);
            pricing.setMonthlyDeviceRentalFeeAmount(0);
            pricing.setOverrideSetupFee(true);
            pricing.setOverrideMonthlyDeviceRentalFee(true);
            payload.setPricing(pricing);
            payload.setBundleOrders(List.of(
                    new Payload.BundleOrder("1996", String.valueOf(saUsStoreShoppingBag.getKettleDisplayQty()), "1", "260"),
                    new Payload.BundleOrder("1997", String.valueOf(saUsStoreShoppingBag.getKettleDisplayQty()*2), "1", "0")
            ));

            // Billing
            Payload.Billing billing = new Payload.Billing();
            billing.setBillingFirstName(contactAndShipping.getContactFirstName());
            billing.setBillingLastName(contactAndShipping.getContactLastName());
            billing.setBillingAddr1(payment.getBillingAddr1());
            billing.setBillingCity(payment.getBillingCity());
            billing.setBillingState(payment.getBillingState());
            billing.setBillingCountry(payment.getBillingCountry());
            billing.setBillingZip(payment.getBillingZip());
            payload.setBilling(billing);

            // Sanitize division input before using it
            String rawDivisionName = contactAndShipping.getCustomerGrandChild();
            String cleanedDivisionName = rawDivisionName == null ? "" : rawDivisionName.replaceAll("[,\\t]", "").trim();
            int divisionId = SaUsOrganizationMap.getDivisionIdByName(cleanedDivisionName);
            contactAndShipping.setCustomerGrandChild(String.valueOf(divisionId));

            // Shipping
            payload.setShipping(getShipping(contactAndShipping));

            // Customer Info
            payload.setCustomerInformation(getCustomerInformation(contactAndShipping, storeId));

            // Organization Hierarchy
            payload.setOrganizationHierarchy(getOrganizationHierarchy(storeId, contactAndShipping));

            if (AppConstants.NetSuiteActions.ACTION_COMPLETE_ORDER_WITH_PAYMENT.equals(action)) {
                payload.setAction(action);}

            // Set resolved cleaned division name
            payload.setDivision(cleanedDivisionName);

            // Additional fields
            payload.setMinistryName(contactAndShipping.getMinistryName());
            payload.setRespcNumber(contactAndShipping.getRespcNumber());
            payload.setMemo("");

            return payload;

        } catch (Exception e) {
            log.error("Error preparing SAUS payload: {}", e.getMessage(), e);
            return null;
        }
    }

    public final List<Product> products = List.of(
            new Product(PRODUCT_ID_KETTLE_DISPLAY, PRODUCT_NAME_KETTLE_DISPLAY, "", FEE_ONE_TIME_IN_CENTS, 0, 0, PRODUCT_IMAGES_KETTLE_DISPLAY),
            new Product(PRODUCT_ID_DEVICE_5_DOLLAR, PRODUCT_NAME_DEVICE_5_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, 0, PRODUCT_IMAGES_DEVICE_5_DOLLAR),
            new Product(PRODUCT_ID_DEVICE_10_DOLLAR, PRODUCT_NAME_DEVICE_10_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, 0, PRODUCT_IMAGES_DEVICE_10_DOLLAR),
            new Product(PRODUCT_ID_DEVICE_20_DOLLAR, PRODUCT_NAME_DEVICE_20_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, 0, PRODUCT_IMAGES_DEVICE_20_DOLLAR),
            new Product(PRODUCT_ID_BATTERY_FOR_KETTLE_DISPLAY, PRODUCT_NAME_BATTERY_FOR_KETTLE_DISPLAY, "", 0, FEE_RENTAL_IN_CENTS_2, 0, PRODUCT_IMAGES_BATTERY_FOR_KETTLE_DISPLAY)
    );

    public List<Product> getProductList() {
        return products;
    }

    public List<Product> getProductList(int kettleStandQty, int device5DollarQty, int device10DollarQty, int device20DollarQty, int batteryForKettleDisplayQty) {
        return List.of(
                new Product(PRODUCT_ID_KETTLE_DISPLAY, PRODUCT_NAME_KETTLE_DISPLAY, "", FEE_ONE_TIME_IN_CENTS, 0, kettleStandQty, PRODUCT_IMAGES_KETTLE_DISPLAY),
                new Product(PRODUCT_ID_DEVICE_5_DOLLAR, PRODUCT_NAME_DEVICE_5_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, device5DollarQty, PRODUCT_IMAGES_DEVICE_5_DOLLAR),
                new Product(PRODUCT_ID_DEVICE_10_DOLLAR, PRODUCT_NAME_DEVICE_10_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, device10DollarQty, PRODUCT_IMAGES_DEVICE_10_DOLLAR),
                new Product(PRODUCT_ID_DEVICE_20_DOLLAR, PRODUCT_NAME_DEVICE_20_DOLLAR, "", 0, FEE_RENTAL_IN_CENTS, device20DollarQty, PRODUCT_IMAGES_DEVICE_20_DOLLAR),
                new Product(PRODUCT_ID_BATTERY_FOR_KETTLE_DISPLAY, PRODUCT_NAME_BATTERY_FOR_KETTLE_DISPLAY, "", 0, FEE_RENTAL_IN_CENTS_2, batteryForKettleDisplayQty, PRODUCT_IMAGES_BATTERY_FOR_KETTLE_DISPLAY)
        );
    }

    private static Payload.ContactInformation getContactInformation(ContactAndShipping contactAndShipping) {
        Payload.ContactInformation contact = new Payload.ContactInformation();
        contact.setContactFirstName(contactAndShipping.getContactFirstName());
        contact.setContactLastName(contactAndShipping.getContactLastName());
        contact.setContactPhone((contactAndShipping.getContactPhoneCountryCode() != null ? contactAndShipping.getContactPhoneCountryCode() : "") +
                " " + (contactAndShipping.getContactPhone() != null ? contactAndShipping.getContactPhone() : ""));
        contact.setContactEmail(contactAndShipping.getContactEmail());
        contact.setContactPrefContactMethod(contactAndShipping.getContactPrefContactMethod());
        return contact;
    }

    private static Payload.Shipping getShipping(ContactAndShipping contactAndShipping) {
        Payload.Shipping shipping = new Payload.Shipping();
        shipping.setAttentionTo(contactAndShipping.getAttentionTo());
        shipping.setShippingAddr1(contactAndShipping.getShippingAddr1());
        shipping.setShippingAddr2(contactAndShipping.getShippingAddr2());
        shipping.setShippingCity(contactAndShipping.getShippingCity());
        shipping.setShippingState("canada".equalsIgnoreCase(contactAndShipping.getShippingCountry()) ?
                contactAndShipping.getShippingProvince() : contactAndShipping.getShippingState());
        shipping.setShippingCountry(contactAndShipping.getShippingCountry());
        shipping.setShippingZip("canada".equalsIgnoreCase(contactAndShipping.getShippingCountry()) ?
                contactAndShipping.getShippingPostalCode() : contactAndShipping.getShippingZip());
        shipping.setShippingPhone(contactAndShipping.getContactPhone());
        shipping.setShippingEmail(contactAndShipping.getContactEmail());
        return shipping;
    }

    private static Payload.CustomerInformation getCustomerInformation(ContactAndShipping contactAndShipping, String storeId) throws ParseException {
        Payload.CustomerInformation customer = new Payload.CustomerInformation();
        String corpsName = contactAndShipping.getCustomerNameFree();
        customer.setCustomerName((corpsName != null && !corpsName.trim().isBlank()) ? corpsName.replace(",", "").trim() : "Unknown Organization");
        customer.setCustomerPhone((contactAndShipping.getContactPhoneCountryCode() != null ? contactAndShipping.getContactPhoneCountryCode() : "") + " " + (contactAndShipping.getContactPhone() != null ? contactAndShipping.getContactPhone() : ""));
        customer.setCustomerEmail(contactAndShipping.getContactEmail());
        customer.setCustomerPrefContactMethod(contactAndShipping.getContactPrefContactMethod() != null ? contactAndShipping.getContactPrefContactMethod() : "Email");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date baseDate = sdf.parse("2025-11-02");

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(baseDate);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        customer.setCampaignStartDate(new java.sql.Date(calendar.getTimeInMillis()));
        return customer;
    }

    private static Payload.OrganizationHierarchy getOrganizationHierarchy(String storeId, ContactAndShipping contactAndShipping) {
        String grandChild = Arrays.stream(contactAndShipping.getCustomerGrandChild().split(","))
                .filter(s -> !s.isBlank())
                .findFirst().orElse("");

        Hierarchy hierarchy = SaUsOrganizationMap.getHierarchy(contactAndShipping.getCustomerChild(), grandChild);
        Payload.OrganizationHierarchy orgHierarchy = new Payload.OrganizationHierarchy();
        orgHierarchy.setParent(Integer.parseInt("5499"));

        if(contactAndShipping.isFreeCustomerNameEntryAllowed()) {
            orgHierarchy.setChild(Integer.parseInt(String.valueOf(contactAndShipping.getCustomerGrandChild())));
        }
        else {
            orgHierarchy.setChild(hierarchy.getChild());
            orgHierarchy.setGrandChild(Integer.parseInt(grandChild));}
        return orgHierarchy;
    }

    private static Payload.CustomerCategory getCustomerCategory(String storeId) {
        Payload.CustomerCategory category = new Payload.CustomerCategory();
        category.setCustomerType(CUSTOMER_TYPE);
        category.setCustomerReferral(CUSTOMER_REFERRAL);
        return category;
    }

    public Integer getOrCreateCampaign(String name, String startDate, String COMPANY_ID) {
        try {
            String getUrl = appService.getBaseUrl() + "/" + appService.getAthenaApiPrefix() +COMPANY_ID;

            Map<String, String> headers = Map.of(
                    HTTP_HEADER_NAME_ACCEPT, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
                    HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
                    HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + appService.getAthenaApiKey()
            );

            CustomHttpResponse getResponse = customHttpService.getRequest(getUrl, headers);
            if (getResponse == null) {
                return null;
            }

            Map<String, Object> bodyMap = objectMapper.readValue(getResponse.getResponseBody(), new TypeReference<>() {});
            List<Map<String, Object>> campaigns = (List<Map<String, Object>>) bodyMap.get("campaigns");

            for (Map<String, Object> campaign : campaigns) {
                String existingName = (String) campaign.get("name");
                if (existingName != null && existingName.equalsIgnoreCase(name)) {
                    Integer existingId = (Integer) campaign.get("id");
                    System.out.println("🎯 Found existing campaign with ID: " + existingId);
                    return existingId;
                }
            }

            String postUrl = getUrl;
            Map<String, Object> campaignPayload = Map.of(
                    "campaign", Map.of(
                            "name", name,
                            "start_date", startDate,
                            "status", DEFAULT_STATUS
                    )
            );

            String jsonBody = objectMapper.writeValueAsString(campaignPayload);
            CustomHttpResponse postResponse = customHttpService.postRequest(postUrl, headers, jsonBody);
            if (postResponse == null) {
                return null;
            }

            Map<String, Object> postBody = objectMapper.readValue(postResponse.getResponseBody(), new TypeReference<>() {});
            Map<String, Object> newCampaign = (Map<String, Object>) postBody.get("campaign");

            if (newCampaign != null && newCampaign.get("id") != null) {
                Integer newId = (Integer) newCampaign.get("id");
                return newId;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean createMembership(
            Integer companyId,
            Integer campaignId,
            String email,
            String familyName,
            String givenName,
            String password,
            String phoneNumber,
            String role,
            String status,
            String loginDestinationCanada) {

        System.out.println("🔐 Creating membership for email: " + email);

        try {
            String postUrl = appService.getBaseUrl() + "/" + appService.getAthenaApiPrefix()
                    + "/companies/" + companyId + "/memberships";

            Map<String, String> headers = Map.of(
                    HTTP_HEADER_NAME_ACCEPT, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
                    HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
                    HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + appService.getAthenaApiKey()
            );

            // Ensure phone number starts with +
            String cleanedPhone = phoneNumber != null && phoneNumber.trim().startsWith("+")
                    ? phoneNumber.trim()
                    : "+1" + phoneNumber.trim().replaceAll("[^0-9]", "");

            // Dynamic login destination based on campaign
            String loginDestination = "/companies/" + companyId + "/campaigns/" + campaignId;

            Map<String, Object> payload = Map.of(
                    "email", email,
                    "family_name", familyName,
                    "given_name", givenName,
                    "login_destination", loginDestination,
                    "password", password,
                    "phone_number", cleanedPhone,
                    "role", role,
                    "status", status,
                    "campaign_ids", List.of(campaignId)
            );

            String jsonBody = objectMapper.writeValueAsString(payload);

            System.out.println("📦 Membership payload: " + jsonBody);
            System.out.println("📤 Sending POST request to create membership at: " + postUrl);

            CustomHttpResponse postResponse = customHttpService.postRequest(postUrl, headers, jsonBody);
            if (postResponse == null || postResponse.getResponseCode() != 201) {
                System.out.println("❌ Membership creation failed. Status Code: " +
                        (postResponse != null ? postResponse.getResponseCode() : "null"));
                System.out.println("❌ Response Body: " +
                        (postResponse != null ? postResponse.getResponseBody() : "null"));
                return false;
            }

            System.out.println("✅ Membership created successfully. Response: " + postResponse.getResponseBody());
            return true;

        } catch (Exception e) {
            System.out.println("❌ Exception in createMembership: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public void campaignCall(ContactAndShipping contactAndShipping, String COMPANY_ID) {
        String contactEmail = contactAndShipping.getContactEmail();

        if (contactEmail != null && !contactEmail.trim().isEmpty()) {
            try {
                String givenName = (contactAndShipping.getContactFirstName() != null)
                        ? contactAndShipping.getContactFirstName().trim()
                        : "Canada";

                String familyName = (contactAndShipping.getContactLastName() != null)
                        ? contactAndShipping.getContactLastName().trim()
                        : "User";

                // Use corps name as campaign name if available
                String campaignName = "2025 " +
                        ((contactAndShipping.getCustomerNameFree() != null && !contactAndShipping.getCustomerNameFree().isBlank())
                                ? contactAndShipping.getCustomerNameFree().trim().replaceAll(",", "")
                                : "General Corps");

                String today = java.time.LocalDate.now().toString();
                Integer campaignId = getOrCreateCampaign(campaignName, today, COMPANY_ID);

                if (campaignId == null) {
                    logger.logError("❌ Failed to create or retrieve campaign for name: " + campaignName);
                    return;
                }

                boolean isCreated = createMembership(
                        Integer.parseInt(COMPANY_ID.replaceAll("[^0-9]", "")),
                        campaignId,
                        contactEmail.trim(),
                        familyName,
                        givenName,
                        AppConstants.MembershipConstants.DEFAULT_PASSWORD,
                        (contactAndShipping.getContactPhone() != null)
                                ? contactAndShipping.getContactPhone().trim()
                                : AppConstants.MembershipConstants.DEFAULT_PHONE_FALLBACK,
                        AppConstants.MembershipConstants.DEFAULT_ROLE,
                        AppConstants.MembershipConstants.DEFAULT_STATUS,
                        COMPANY_ID + "/" + campaignId
                );

                if (!isCreated) {
                    logger.logError("❌ Membership creation failed for email: " + contactEmail);
                }

            } catch (Exception e) {
                logger.logError("❌ Exception while calling campaign setup and membership creation", e);
            }
        }
    }

    public void AthenaTerritoryCampaign(ContactAndShipping contactAndShipping) {
        String territory = contactAndShipping.getCustomerChild();

        if ("The Salvation Army Central Territory".equalsIgnoreCase(territory)) {
            campaignCall(contactAndShipping, "/companies/350/campaigns");

        }
        if ("The Salvation Army Eastern Territory".equalsIgnoreCase(territory)) {
            campaignCall(contactAndShipping, "/companies/351/campaigns");

        }
        if ("The Salvation Army Southern Territory".equalsIgnoreCase(territory)) {
            campaignCall(contactAndShipping, "/companies/44/campaigns");

        }
        if ("The Salvation Army Western Territory".equalsIgnoreCase(territory)) {
            campaignCall(contactAndShipping, "/companies/352/campaigns");

        }
    }
}