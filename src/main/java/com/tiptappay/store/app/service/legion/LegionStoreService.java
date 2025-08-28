package com.tiptappay.store.app.service.legion;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiptappay.store.app.constant.AppConstants;
import com.tiptappay.store.app.dto.legion.CheckoutData;
import com.tiptappay.store.app.model.CustomHttpResponse;
import com.tiptappay.store.app.model.payload.Payload;
import com.tiptappay.store.app.service.CustomHttpService;
import com.tiptappay.store.app.service.app.AppService;
import com.tiptappay.store.app.service.app.CustomLoggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


import static com.tiptappay.store.app.constant.AppConstants.CampaignConstants.DEFAULT_STATUS;
import static com.tiptappay.store.app.constant.AppConstants.HttpConstants.*;
import static com.tiptappay.store.app.constant.AppConstants.HttpConstants.HTTP_HEADER_NAME_AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class LegionStoreService {

    private final CustomLoggerService logger;
    private final AppService appService;
    private final CustomHttpService customHttpService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String SANDBOX_SO_FORM_INTERNAL_ID = "185";
    private static final String PRODUCTION_SO_FORM_INTERNAL_ID = "184";

    private static final String COMPANY_ID = "/companies/3103/campaigns";


    public Payload preparePayload(CheckoutData checkoutData, String action) {
        Payload payload = new Payload();

        try {
            // Header
            Payload.Header header = new Payload.Header();
            header.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse("2025-05-28T15:13:30Z"));
            header.setOrderFormType("Legion Order Form");
            header.setNsSOFormInternalID(PRODUCTION_SO_FORM_INTERNAL_ID);
            payload.setHeader(header);

            // Contact Information
            Payload.ContactInformation contact = new Payload.ContactInformation();
            contact.setContactFirstName(checkoutData.getContactFirstName());
            contact.setContactLastName(checkoutData.getContactLastName());
            contact.setContactPhone(checkoutData.getContactPhoneNumber());
            contact.setContactEmail(checkoutData.getContactEmail());
            contact.setContactPrefContactMethod(null);
            payload.setContactInformation(contact);

            // Customer Information
            Payload.CustomerInformation customer = new Payload.CustomerInformation();
            customer.setCustomerName("Royal Canadian Legion - National HQ");
            customer.setCustomerPhone(checkoutData.getContactPhoneNumber());
            customer.setCustomerEmail(checkoutData.getContactEmail());
            customer.setCustomerPrefContactMethod("Email");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date baseDate = sdf.parse("2025-11-01");
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTime(baseDate);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            customer.setCampaignStartDate(calendar.getTime());

            payload.setCustomerInformation(customer);

            // Billing
            Payload.Billing billing = new Payload.Billing();
            billing.setBillingFirstName("Lia Taha");
            billing.setBillingLastName("Cheng");
            billing.setBillingAddr1("86 Aird Place");
            billing.setBillingAddr2("");
            billing.setBillingCity("Ottawa");
            billing.setBillingState("Ontario");
            billing.setBillingCountry("Canada");
            billing.setBillingZip("K2L 0A1");
            billing.setBillingPhone("613-591-3335 ext 305");
            billing.setBillingEmail("ltahacheng@legion.ca");
            billing.setBillingAttention("Director – Poppy & Remembrance Division");
            payload.setBilling(billing);

            // Shipping
            Payload.Shipping shipping = new Payload.Shipping();
            shipping.setAttentionTo(checkoutData.getShippingFullName());
            shipping.setShippingAddr1(checkoutData.getShippingAddressLine1());
            shipping.setShippingAddr2(checkoutData.getShippingAddressLine2());
            shipping.setShippingCity(checkoutData.getShippingCity());
            shipping.setShippingState(checkoutData.getShippingState());
            shipping.setShippingCountry("Canada");
            shipping.setShippingZip(checkoutData.getShippingZipCode());
            shipping.setShippingPhone(checkoutData.getShippingPhone());
            shipping.setShippingEmail(checkoutData.getContactEmail());
            payload.setShipping(shipping);

            // Org Hierarchy
            Payload.OrganizationHierarchy hierarchy = new Payload.OrganizationHierarchy();
            hierarchy.setParent(0);
            hierarchy.setChild(116021);
            hierarchy.setGrandChild(1);
            payload.setOrganizationHierarchy(hierarchy);

            // Customer Category
            Payload.CustomerCategory category = new Payload.CustomerCategory();
            category.setCustomerType("Enterprise");
            category.setCustomerReferral("");
            payload.setCustomerCategory(category);

            // Order Details
            Payload.OrderDetails orderDetails = new Payload.OrderDetails();
            orderDetails.setNumberOfClips("0");
            orderDetails.setNumberOf2DollarDevices("0");
            orderDetails.setNumberOf5DollarDevices(String.valueOf(checkoutData.getTwoPackPoppyDisplay()));
            orderDetails.setNumberOf10DollarDevices("0");
            orderDetails.setNumberOf20DollarDevices("0");
            orderDetails.setNumberOf50DollarDevices(null);
            orderDetails.setNumberOfFloorStands("0");
            orderDetails.setNumberOfKettleDisplay("0");
            orderDetails.setNumberOfBatteryForKettleDisplay("0");
            orderDetails.setThreeDeviceDisplayEnglishQuantity(null);
            orderDetails.setThreeDeviceDisplayFrenchQuantity(null);
            orderDetails.setThreeDeviceDisplayBilingualQuantity(null);
            orderDetails.setSingleCurveEnglishQuantity(null);
            orderDetails.setSingleCurveFrenchQuantity(null);
            orderDetails.setSingleCurveBilingualQuantity(null);
            payload.setOrderDetails(orderDetails);

            // Bundle Orders
            payload.setBundleOrders(List.of(
                    new Payload.BundleOrder("2163", String.valueOf(checkoutData.getTwoPackPoppyDisplay()), "1", "57.50")
            ));

            // Pricing
            Payload.Pricing pricing = new Payload.Pricing();
            pricing.setCurrency("CAD");
            pricing.setOverrideSetupFee(true);
            pricing.setSetupFeeAmount(0);
            pricing.setOverrideMonthlyDeviceRentalFee(true);
            pricing.setMonthlyDeviceRentalFeeAmount(0);
            payload.setPricing(pricing);

            // Division, Ministry, RESPC
            payload.setDivision(checkoutData.getProvincialCommand());
            payload.setMinistryName(checkoutData.getBranchNumber());
            payload.setRespcNumber(null);

            // Action and Memo
            payload.setAction(action);
            payload.setMemo(checkoutData.getEstimatedPopulation());

            // ✅ Membership creation from checkoutData
            String contactEmail = checkoutData.getContactEmail();


        } catch (Exception e) {
            logger.logError("❌ Error while preparing Legion Store payload", e);
        }

        return payload;
    }


    public Integer getOrCreateCampaign(String name, String startDate) {
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
    public void campaignCall(CheckoutData checkoutData) {
        String contactEmail = checkoutData.getContactEmail();

        if (contactEmail != null && !contactEmail.trim().isEmpty()) {
            try {
                String givenName = (checkoutData.getContactFirstName() != null)
                        ? checkoutData.getContactFirstName().trim()
                        : "Canada";

                String familyName = (checkoutData.getContactLastName() != null)
                        ? checkoutData.getContactLastName().trim()
                        : "User";

                String campaignName = "2025 " + checkoutData.getBranchNumber() + " " + checkoutData.getProvincialCommand();
                Integer campaignId = getOrCreateCampaign(campaignName, "2025-10-31");

                if (campaignId == null) {
                    logger.logError("❌ Failed to create or retrieve campaign for name: " + campaignName);
                    return;
                }

                boolean isCreated = createMembership(
                        3103,
                        campaignId,
                        contactEmail.trim(),
                        familyName,
                        givenName,
                        AppConstants.MembershipConstants.DEFAULT_PASSWORD,
                        (checkoutData.getContactPhoneNumber() != null)
                                ? checkoutData.getContactPhoneNumber().trim()
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
}
