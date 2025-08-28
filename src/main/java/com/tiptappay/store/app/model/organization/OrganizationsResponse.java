package com.tiptappay.store.app.model.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrganizationsResponse {
    @JsonProperty("companies")
    private List<Organization> organizations;

    @JsonProperty("page_number")
    private int pageNumber;

    @JsonProperty("page_size")
    private int pageSize;

    @JsonProperty("total_entries")
    private int totalEntries;

    @JsonProperty("total_pages")
    private int totalPages;

}