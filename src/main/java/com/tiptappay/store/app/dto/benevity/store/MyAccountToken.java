package com.tiptappay.store.app.dto.benevity.store;

import lombok.Data;

@Data
public class MyAccountToken {

    // Organization Information
    private int organizationId = Integer.MIN_VALUE;
    private String organizationName;
    private boolean isNewOrganization;

    // User Information
    private String givenName;
    private String familyName;
    private String phoneNumber;
    private String email;
    private String role;
    private String status;

    // Campaign and Payment Information
    private String campaignId;
    private String paymentPageId;

    // Token and Authentication
    private String loginURL;
    private long tokenExp;
    private int numOfResent;
    private long resendCounter;
    private boolean isPrepSuccess;
}