package com.tiptappay.store.app.model.membership;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MembershipsResponse {
    @JsonProperty("company_id")
    private int organizationId;

    @JsonProperty("memberships")
    private List<Membership> memberships;

    @JsonProperty("page_number")
    private int pageNumber;

    @JsonProperty("page_size")
    private int pageSize;

    @JsonProperty("total_entries")
    private int totalEntries;

    @JsonProperty("total_pages")
    private int totalPages;
}
