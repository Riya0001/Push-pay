package com.tiptappay.store.app.model.organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationRequest {
    @JsonProperty("company")
    private Organization organization;

    @JsonProperty("create_default_campaign")
    private boolean createDefaultCampaign;

    @JsonProperty("create_default_payment_page")
    private boolean createDefaultPaymentPage;

    @JsonProperty("create_default_campaign_id")
    private boolean createDefaultCampaignId;

    @JsonProperty("create_default_payment_page_id")
    private boolean createDefaultPaymentPageId;
}
