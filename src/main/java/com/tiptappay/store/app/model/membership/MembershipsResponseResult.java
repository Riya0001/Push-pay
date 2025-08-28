package com.tiptappay.store.app.model.membership;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MembershipsResponseResult {
    @JsonProperty("existing_member")
    private boolean isExistingMember;

    @JsonProperty("membership")
    private Membership membership;
}
