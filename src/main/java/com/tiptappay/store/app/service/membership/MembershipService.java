package com.tiptappay.store.app.service.membership;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiptappay.store.app.dto.benevity.store.MyAccountToken;
import com.tiptappay.store.app.model.CustomHttpResponse;
import com.tiptappay.store.app.model.membership.*;
import com.tiptappay.store.app.service.CustomHttpService;
import com.tiptappay.store.app.service.app.AppService;
import com.tiptappay.store.app.service.app.CustomLoggerService;
import com.tiptappay.store.app.util.DataUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.tiptappay.store.app.constant.AppConstants.HttpConstants.*;

@Service
@RequiredArgsConstructor
public class MembershipService {
    private final AppService app;
    private final CustomLoggerService logger;
    private final CustomHttpService customHttpService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public List<Membership> fetchMembershipsByOrganizationId(int organizationId) {
        logger.logInfo("Inside MembershipService.fetchMembershipsByOrganizationId()");

        String urlPath = "/" + app.getAthenaApiPrefix() + "/companies/" + organizationId + "/memberships";

        Map<String, String> headers = Map.of(
                HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
                HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + app.getAthenaApiKey()
        );

        CustomHttpResponse customHttpResponse = customHttpService.getRequest(app.getBaseUrl() + urlPath, headers);

        try {
            MembershipsResponse memberships = DataUtils.convertToObject(customHttpResponse.getResponseBody(), MembershipsResponse.class);

            if (memberships != null) {
                logger.logInfo("Memberships fetched successfully");
                return memberships.getMemberships();
            }
        } catch (Exception e) {
            logger.logError("Exception while fetching memberships");
        }
        return null;
    }

    public CustomHttpResponse postMembership(int organizationId, MembershipRequest membershipRequest) {
        logger.logInfo("Inside MembershipService.postMembership()");

        String urlPath = "/" + app.getAthenaApiPrefix() + "/companies/" + organizationId + "/memberships";
        String fullUrl = app.getBaseUrl() + urlPath;

        Map<String, String> headers = Map.of(
                HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
                HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + app.getAthenaApiKey()
        );

        try {
            String requestBody = DataUtils.convertToJsonString(membershipRequest);

            logger.logInfo("POST URL: " + fullUrl);
            logger.logInfo("Headers: " + headers.toString());
            logger.logInfo("Request Body: " + requestBody);

            CustomHttpResponse response = customHttpService.postRequest(fullUrl, headers, requestBody);

            logger.logInfo("Response Code: " + response.getResponseCode());
            logger.logInfo("Response Body: " + response.getResponseBody());

            return response;
        } catch (Exception e) {
            logger.logError("Exception while creating memberships: " + e.getMessage());
            return null;
        }
    }



    public MembershipsResponseResult handleMembershipResponse(CustomHttpResponse customHttpResponse, MyAccountToken myAccountToken) {

        if (customHttpResponse != null) {

            if (customHttpResponse.getResponseCode() == 201) {
                MembershipResponse createdMembership = DataUtils.convertToObject(customHttpResponse.getResponseBody(), MembershipResponse.class);

                if (createdMembership != null) {
                    logger.logInfo("Membership created successfully");

                    MembershipsResponseResult responseResult = new MembershipsResponseResult();
                    responseResult.setMembership(createdMembership.getMembership());
                    responseResult.setExistingMember(false);
                    return responseResult;
                }
            }

            if(customHttpResponse.getResponseCode() == 422) {
                List<Membership> memberships = fetchMembershipsByOrganizationId(myAccountToken.getOrganizationId());

                for (Membership membership : memberships) {
                    if (membership.getUser().getEmail().equals(myAccountToken.getEmail())) {
                        MembershipsResponseResult responseResult = new MembershipsResponseResult();
                        responseResult.setMembership(membership);
                        responseResult.setExistingMember(true);
                        return responseResult;
                    }
                }
            }

        }

        return null;
    }

    public MembershipRequest generateNewMembershipRequest(MyAccountToken myAccountToken, String password) {
        logger.logInfo("Inside OrganizationService.generateNewMembershipRequest()");

        MembershipRequest membershipRequest = new MembershipRequest();
        membershipRequest.setEmail(myAccountToken.getEmail());
        membershipRequest.setFamilyName(myAccountToken.getFamilyName());
        membershipRequest.setGivenName(myAccountToken.getGivenName());
        membershipRequest
                .setLoginDestination(
                        String.format("/companies/%s/setup/my-first-payment-page", myAccountToken.getOrganizationId())
                );
        membershipRequest.setPassword(password);
        membershipRequest.setPhoneNumber(myAccountToken.getPhoneNumber());
        membershipRequest.setRole(myAccountToken.getRole());
        membershipRequest.setStatus(myAccountToken.getStatus());

        return membershipRequest;
    }

}
