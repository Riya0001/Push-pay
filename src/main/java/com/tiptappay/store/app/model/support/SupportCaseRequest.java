package com.tiptappay.store.app.model.support;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SupportCaseRequest {
    @JsonProperty("config")
    private Config config;

    @JsonProperty("primaryInformation")
    private PrimaryInformation primaryInformation;

    @JsonProperty("returnLabelRequest")
    private ReturnLabelRequest returnLabelRequest;

    @JsonProperty("existingOrderInquiry")
    private ExistingOrderInquiry existingOrderInquiry;

    @JsonProperty("technicalIssue")
    private TechnicalIssue technicalIssue;

    @JsonProperty("message")
    private String message;

    // Parent Objects

    @Data
    public static class Config {
        @JsonProperty("origin")
        private String origin;

        @JsonProperty("company")
        private ConfigCompany company;

        @JsonProperty("contact")
        private ConfigContact contact;
    }

    @Data
    public static class PrimaryInformation {

        @JsonProperty("ticketName")
        private String ticketName;

        @JsonProperty("caseIssue")
        private String caseIssue;

        @JsonProperty("companyName")
        private String companyName;

        @JsonProperty("contact")
        private PrimaryInformationContact contact;
    }

    @Data
    public static class ReturnLabelRequest {
        @JsonProperty("senderInformation")
        private ReturnLabelRequestSenderInformation senderInformation;

        @JsonProperty("numOfDeviceReturned")
        private String numOfDeviceReturned;
    }

    @Data
    public static class ExistingOrderInquiry {
        @JsonProperty("orderNumbers")
        private String orderNumbers;
    }

    @Data
    public static class TechnicalIssue {
        @JsonProperty("productType")
        private String productType;

        @JsonProperty("device")
        private TechnicalIssueDevice technicalIssueDevice;

        @JsonProperty("battery")
        private TechnicalIssueBattery technicalIssueBattery;

        @JsonProperty("cable")
        private TechnicalIssueCable technicalIssueCable;

        @JsonProperty("wallPlug")
        private TechnicalIssueWallPlug technicalIssueWallPlug;

        @JsonProperty("another")
        private TechnicalIssueAnother technicalIssueAnother;
    }

    // Child Objects

    @Data
    public static class ConfigCompany {
        @JsonProperty("enableSearch")
        private boolean enableSearch;
    }

    @Data
    public static class ConfigContact {
        @JsonProperty("enableSearch")
        private boolean enableSearch;

        @JsonProperty("enableCreate")
        private boolean enableCreate;
    }

    @Data
    public static class PrimaryInformationContact {
        @JsonProperty("firstName")
        private String firstName;

        @JsonProperty("lastName")
        private String lastName;

        @JsonProperty("email")
        private String email;

        @JsonProperty("phone")
        private String phone;
    }

    @Data
    public static class ReturnLabelRequestSenderInformation {
        @JsonProperty("name")
        private String name;

        @JsonProperty("email")
        private String email;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("address")
        private String address;

        @JsonProperty("organization")
        private String organization;
    }

    @Data
    public static class TechnicalIssueDevice {
        @JsonProperty("ledSequence")
        private String ledSequence;

        @JsonProperty("poweredMin4Hr")
        private String poweredMin4Hr;

        @JsonProperty("firstBeep")
        private String firstBeep;

        @JsonProperty("secondBeep")
        private String secondBeep;

        @JsonProperty("serialNumbers")
        private String serialNumbers;
    }

    @Data
    public static class TechnicalIssueBattery {
        @JsonProperty("numOfBatteries")
        private String numOfBatteries;

        @JsonProperty("type")
        private String type;

        @JsonProperty("issueType")
        private String issueType;
    }

    @Data
    public static class TechnicalIssueCable {
        @JsonProperty("numOfCable")
        private String numOfCable;

        @JsonProperty("type")
        private String type;

        @JsonProperty("issueType")
        private String issueType;
    }

    @Data
    public static class TechnicalIssueWallPlug {
        @JsonProperty("numberOfWallPlugs")
        private String numberOfWallPlugs;

        @JsonProperty("issueType")
        private String issueType;
    }

    @Data
    public static class TechnicalIssueAnother {
        @JsonProperty("issueType")
        private String issueType;
    }
}
