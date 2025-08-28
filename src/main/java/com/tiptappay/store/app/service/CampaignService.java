package com.tiptappay.store.app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiptappay.store.app.model.CustomHttpResponse;
import com.tiptappay.store.app.service.app.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.tiptappay.store.app.constant.AppConstants.CampaignConstants.CAMPAIGNS_ENDPOINT;
import static com.tiptappay.store.app.constant.AppConstants.HttpConstants.*;
import static com.tiptappay.store.app.constant.AppConstants.CampaignConstants.DEFAULT_STATUS;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final AppService appService;
    private final CustomHttpService customHttpService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Integer getOrCreateCampaign(String name, String startDate) {
        try {
            String getUrl = appService.getBaseUrl() + "/" + appService.getAthenaApiPrefix() + CAMPAIGNS_ENDPOINT;

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


}
