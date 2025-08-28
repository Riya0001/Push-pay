package com.tiptappay.store.app.model.membership;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MembershipRequest {
    @JsonProperty("email")
    private String email;

    @JsonProperty("family_name")
    private String familyName;

    @JsonProperty("given_name")
    private String givenName;

    @JsonProperty("login_destination")
    private String loginDestination;

    @JsonProperty("password")
    private String password;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("role")
    private String role;

    @JsonProperty("campaign_ids")
    private List<Integer> campaignIds;

    @JsonProperty("status")
    private String status;
}
