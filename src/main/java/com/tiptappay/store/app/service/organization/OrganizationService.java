package com.tiptappay.store.app.service.organization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiptappay.store.app.dto.benevity.store.BenevityCause;
import com.tiptappay.store.app.model.CustomHttpResponse;
import com.tiptappay.store.app.model.ResponsePayment;
import com.tiptappay.store.app.model.organization.Organization;
import com.tiptappay.store.app.model.organization.OrganizationRequest;
import com.tiptappay.store.app.model.organization.OrganizationResponse;
import com.tiptappay.store.app.model.organization.OrganizationsResponse;
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
public class OrganizationService {
    private final AppService app;
    private final CustomLoggerService logger;
    private final CustomHttpService customHttpService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public List<Organization> fetchOrganizations() {
        logger.logInfo("Inside OrganizationService.fetchOrganizations()");

        String urlPath = "/" + app.getAthenaApiPrefix() + "/companies";

        Map<String, String> headers = Map.of(
                HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
                HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + app.getAthenaApiKey()
        );

        CustomHttpResponse response = customHttpService.getRequest(app.getBaseUrl() + urlPath, headers);

        try {
            OrganizationsResponse organizations = DataUtils.convertToObject(response.getResponseBody(), OrganizationsResponse.class);

            if (organizations != null) {
                logger.logInfo("Organizations fetched successfully");
                return organizations.getOrganizations();
            }
        } catch (Exception e) {
            logger.logError("Exception while fetching organizations");
        }
        return null;
    }

    public Organization fetchOrganizationById(String id) {
        logger.logInfo("Inside OrganizationService.fetchOrganizationById()");

        String urlPath = "/" + app.getAthenaApiPrefix() + "/companies/" + id;

        Map<String, String> headers = Map.of(
                HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
                HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + app.getAthenaApiKey()
        );

        CustomHttpResponse response = customHttpService.getRequest(app.getBaseUrl() + urlPath, headers);

        try {
            OrganizationResponse organization = DataUtils.convertToObject(response.getResponseBody(), OrganizationResponse.class);

            if (organization != null) {
                logger.logInfo("Organization fetched successfully");
                return organization.getOrganization();
            }
        } catch (Exception e) {
            logger.logError(String.format("Exception while fetching organization with id: %s", id));
        }
        return null;
    }

    public OrganizationResponse postOrganization(OrganizationRequest organizationRequest) {
        logger.logInfo("Inside OrganizationService.postOrganization()");

        String urlPath = "/" + app.getAthenaApiPrefix() + "/companies";

        Map<String, String> headers = Map.of(
                HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
                HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + app.getAthenaApiKey()
        );

        try {
            String requestBody = DataUtils.convertToJsonString(organizationRequest);
            logger.logInfo("Organization Request: " + requestBody);
            CustomHttpResponse customHttpResponse = customHttpService.postRequest(app.getBaseUrl() + urlPath, headers, requestBody);

            if (customHttpResponse != null) {

                // Organization newly created
                if (customHttpResponse.getResponseCode() == HTTP_RESPONSE_CREATED) {
                    OrganizationResponse createdOrganization =
                            DataUtils.convertToObject(customHttpResponse.getResponseBody(), OrganizationResponse.class);
                    if (createdOrganization != null) {
                        return createdOrganization;
                    }
                // Organization exist
                } else if (customHttpResponse.getResponseCode() == HTTP_RESPONSE_UNPROCESSABLE_ENTITY) {
                    Map<String, Object> organizationFailedMap = OBJECT_MAPPER.readValue(customHttpResponse.getResponseBody(), new TypeReference<>() {
                    });
                    Map<String, Object> errors = (Map<String, Object>) organizationFailedMap.get("errors");

                    String key = "existing_record_id:name";
                    if (errors.containsKey(key)) {
                        List<String> values = (List<String>) errors.get(key);
                        if (!values.isEmpty()) {
                            int existingRecordId = Integer.parseInt(values.get(0));
                            // Set Organization ID for reference
                            organizationRequest.getOrganization().setId(existingRecordId);
                            return null;
                        }
                    }
                } else {
                    organizationRequest.getOrganization().setId(Integer.MIN_VALUE);
                    logger.logError("");
                    return null;
                }

            }

        } catch (Exception e) {
            logger.logError("Exception while creating organizations");
        }
        return null;
    }

    public OrganizationRequest generateNewOrganizationRequest(BenevityCause benevityCause, ResponsePayment payment) {
        logger.logInfo("Inside OrganizationService.generateNewOrganization()");

        OrganizationRequest organizationRequest = new OrganizationRequest();
        Organization organization = new Organization();

        organization.setBasicReportingOnly(false);
        organization.setBenevityCauseId(benevityCause.getId());
        organization.setCountryCode(benevityCause.getAddress()
                .getCountry()
                .equalsIgnoreCase("Canada") ? "CA" : "US"
        );
        organization.setCurrency(organization.getCountryCode().equalsIgnoreCase("CA") ? "CAD" : "USD");
        organization.setDefaultTimezone("America/New_York");
        organization.setName(benevityCause.getName());
        organization.setReferenceCode(String.valueOf(payment.getCustomerId()));
        organization.setNotificationsSmsEnabled(false);
        organization.setNotificationsSmsRecipients(benevityCause.getPhone());
        organization.setNotificationsSmsTemplate("A transaction for {COMPANY_NAME} of {TRANSACTION_AMOUNT} {TRANSACTION_CURRENCY} was {TRANSACTION_STATUS} on {TRANSACTION_DATETIME} from device {DEVICE_SERIAL_NUMBER}");
        organization.setPaymentPagesEnabled("true");
        organization.setRequireTermsAndConditionsAgreement(false);
        organization.setStatus("active");

        Organization.Subscription subscription = new Organization.Subscription();

        Organization.Notifications notifications = new Organization.Notifications();
        Organization.RequestForTaxReceipt requestForTaxReceipt = new Organization.RequestForTaxReceipt();

        requestForTaxReceipt.setEnabled(false);
        requestForTaxReceipt.setObfuscateEmailAddress(false);
        notifications.setRequestForTaxReceipt(requestForTaxReceipt);

        subscription.setNotifications(notifications);

        Organization.Tools tools = new Organization.Tools();
        Organization.QrCodeGenerator qrCodeGenerator = new Organization.QrCodeGenerator();

        qrCodeGenerator.setEnabled(false);
        tools.setQrCodeGenerator(qrCodeGenerator);

        subscription.setTools(tools);

        organization.setSubscription(subscription);

        organizationRequest.setOrganization(organization);
        organizationRequest.setCreateDefaultCampaign(true);
        organizationRequest.setCreateDefaultPaymentPage(false);

        return organizationRequest;
    }

}
