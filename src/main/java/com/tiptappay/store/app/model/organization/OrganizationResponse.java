package com.tiptappay.store.app.model.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrganizationResponse {
    @JsonProperty("company")
    private Organization organization;

    @JsonProperty("create_default_campaign_id")
    private String defaultCampaignId;

    @JsonProperty("create_default_payment_page_id")
    private String defaultPaymentPageId;
}