package com.tiptappay.store.app.model.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organization {
    @JsonProperty("basic_reporting_only")
    private boolean basicReportingOnly;

    @JsonProperty("benevity_cause_id")
    private String benevityCauseId;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("default_timezone")
    private String defaultTimezone;

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("notifications_sms_enabled")
    private boolean notificationsSmsEnabled;

    @JsonProperty("notifications_sms_recipients")
    private String notificationsSmsRecipients;

    @JsonProperty("notifications_sms_template")
    private String notificationsSmsTemplate;

    @JsonProperty("payment_pages_enabled")
    private String paymentPagesEnabled;

    @JsonProperty("reference_code")
    private String referenceCode;

    @JsonProperty("require_terms_and_conditions_agreement")
    private boolean requireTermsAndConditionsAgreement;

    @JsonProperty("status")
    private String status;

    @JsonProperty("subscription")
    private Subscription subscription;

    @Data
    public static class Subscription {
        @JsonProperty("notifications")
        private Notifications notifications;

        @JsonProperty("tools")
        private Tools tools;
    }

    @Data
    public static class Notifications {
        @JsonProperty("request_for_tax_receipt")
        private RequestForTaxReceipt requestForTaxReceipt;
    }

    @Data
    public static class RequestForTaxReceipt {
        @JsonProperty("enabled")
        private boolean enabled;

        @JsonProperty("obfuscate_email_address")
        private boolean obfuscateEmailAddress;
    }

    @Data
    public static class Tools {
        @JsonProperty("qr_code_generator")
        private QrCodeGenerator qrCodeGenerator;
    }

    @Data
    public static class QrCodeGenerator {
        @JsonProperty("enabled")
        private boolean enabled;
    }
}