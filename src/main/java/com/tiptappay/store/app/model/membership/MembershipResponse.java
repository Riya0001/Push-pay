package com.tiptappay.store.app.model.membership;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MembershipResponse {
    @JsonProperty("authentication_token")
    private String authenticationToken;

    @JsonProperty("login_url")
    private String loginUrl;

    @JsonProperty("membership")
    private Membership membership;

    @JsonProperty("user_status")
    private String userStatus;
}
