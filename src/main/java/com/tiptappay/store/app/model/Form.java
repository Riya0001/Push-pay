package com.tiptappay.store.app.model;

import com.tiptappay.store.app.validation.PoBoxValidation;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
//@TotalDevicesValidation
public class Form implements Serializable {

    // Customer Information

    @Size(max = 200, message = "Too long.")
    private String customerName;

    @Size(max = 10, message = "Too long.")
    private String customerPhoneCountryCode;

    @Size(max = 14, message = "Too long.")
    private String customerPhone;

    @Size(max = 200, message = "Too long.")
    private String customerEmail;

    @Size(max = 100, message = "Too long.")
    private String customerPrefContactMethod;

    // Contact Information

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 100, message = "Too long.")
    private String contactFirstName;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 100, message = "Too long.")
    private String contactLastName;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 10, message = "Too long.")
    private String contactPhoneCountryCode;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 14, message = "Too long.")
    private String contactPhone;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 200, message = "Too long.")
    @Email(message = "Invalid email format")
    private String contactEmail;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 100, message = "Too long.")
    private String contactPrefContactMethod;

    @Size(max = 100, message = "Too long.")
    private String customerType;

    @Size(max = 100, message = "Too long.")
    private String customerReferral;

    // Netsuite Company Information

    @NotNull
    @Min(0)
    private int parent;

    @NotNull
    @Min(0)
    private int child;

    @NotNull
    @Min(0)
    private int grandChild;

    // Partnership Information

    @Size(max = 200, message = "Too long.")
    private String partnerSponsoring;

    @Size(max = 200, message = "Too long.")
    private String sponsorshipBeneficiary;

    @Size(max = 200, message = "Too long.")
    private String programName;

    @Size(max = 200, message = "Too long.")
    private String programLeadName;

    @Size(max = 10, message = "Too long.")
    private String programLeadPhoneCountryCode;

    @Size(max = 14, message = "Too long.")
    private String programLeadPhone;

    @Size(max = 255, message = "Too long.")
    private String programLeadEmail;


    // Shipping Information

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 200, message = "Too long.")
    private String attentionTo;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 200, message = "Too long.")
    @PoBoxValidation
    private String shippingAddr1;

    @Size(max = 200, message = "Too long.")
    @PoBoxValidation
    private String shippingAddr2;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 100, message = "Too long.")
    private String shippingCity;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 100, message = "Too long.")
    private String shippingStateUS;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 100, message = "Too long.")
    private String shippingStateCA;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 100, message = "Too long.")
    private String shippingCountry;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 5, message = "Too long.")
    @Pattern(regexp = "\\d{5}", message = "Format should be 99999")
    private String shippingZip;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 7, message = "Too long.")
    @Pattern(regexp = "[A-Za-z]\\d[A-Za-z] \\d[A-Za-z]\\d", message = "Format should be A9A 9A9")
    private String shippingPostalCode;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 10, message = "Too long.")
    private String shippingPhoneCountryCode;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 14, message = "Too long.")
    private String shippingPhone;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 200, message = "Too long.")
    @Email(message = "Invalid email format")
    private String shippingEmail;

    // Memo

    @Size(max = 250, message = "Too long.")
    private String memo;
}