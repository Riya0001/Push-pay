package com.tiptappay.store.app.model.support;

import com.tiptappay.store.app.validation.ValidSupportCaseForm;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ValidSupportCaseForm
@ToString
public class SupportCaseForm {
    @NotBlank(message = "Required")
    private String ticketName;

    private String caseIssue;

    @NotBlank(message = "Required")
    private String companyName;

    @NotBlank(message = "Required")
    private String contactFirstName;

    @NotBlank(message = "Required")
    private String contactLastName;

    @NotBlank(message = "Required")
    private String contactPhone;

    @NotBlank(message = "Required")
    private String contactPhoneCountryCode;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Required")
    private String contactEmail;

    private String senderName;

    @Email(message = "Invalid email address")
    private String senderEmail;
    private String senderPhone;

    private String senderPhoneCountryCode;

    private String senderAddress;
    private String senderOrganization;
    private String numOfDeviceReturned;
//    private String orderNumbers;
    private String orderNumbersManualEntry;
    private String technicalIssueProductType;

    private String technicalIssueDeviceLedSequence;
    private String technicalIssueDevicePoweredMin4Hr;
    private String technicalIssueDeviceFirstBeep;
    private String technicalIssueDeviceSecondBeep;

//    private String technicalIssueDeviceSerialNumbers;

    private String technicalIssueDeviceSerialNumbersManualEntry;

    private String technicalIssueBatteryNumOfBatteries;
    private String technicalIssueBatteryType;
    private String technicalIssueBatteryIssueType;

    private String technicalIssueCableNumOfCable;
    private String technicalIssueCableType;
    private String technicalIssueCableIssueType;

    private String technicalIssueWallPlugNumberOfWallPlugs;
    private String technicalIssueWallPlugIssueType;

    private String technicalIssueAnotherIssueType;

    private List<String> selectedDevices;
    private List<String> selectedOrders;

    @NotBlank(message = "Required")
    private String message;

    // Helper
    private boolean manualDeviceEntryEnabled;
    private boolean manualOrderNumbersEntryEnabled;
}
