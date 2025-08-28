package com.tiptappay.store.app.model.membership;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Membership {
    @JsonProperty("company_id")
    private boolean organizationId;

    @JsonProperty("id")
    private int id;

    @JsonProperty("role")
    private String role;

    @JsonProperty("status")
    private String status;

    @JsonProperty("user")
    private User user;

    @Data
    public static class User {
        @JsonProperty("id")
        private UUID id;

        @JsonProperty("email")
        private String email;

        @JsonProperty("family_name")
        private String familyName;

        @JsonProperty("given_name")
        private String givenName;

        @JsonProperty("group")
        private String group;

        @JsonProperty("status")
        private String status;
    }
}