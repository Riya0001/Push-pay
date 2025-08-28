package com.tiptappay.store.app.model.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class Payload {


    @Setter
    @Getter
    public static class Header {
        @JsonProperty("timestamp")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        private Date timestamp;

        @JsonProperty("orderFormType")
        private String orderFormType;

        @JsonProperty("nsSOFormInternalID")
        private String nsSOFormInternalID;
    }

    @Setter
    @Getter
    public static class CustomerInformation {
        @JsonProperty("customerName")
        private String customerName;

        @JsonProperty("customerPhone")
        private String customerPhone;

        @JsonProperty("customerEmail")
        private String customerEmail;

        @JsonProperty("customerPrefContactMethod")
        private String customerPrefContactMethod;

        @JsonProperty("campaignStartDate")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private Date campaignStartDate;
    }

    @Setter
    @Getter
    public static class ContactInformation {

        @JsonProperty("contactFirstName")
        private String contactFirstName;

        @JsonProperty("contactLastName")
        private String contactLastName;

        @JsonProperty("contactPhone")
        private String contactPhone;

        @JsonProperty("contactEmail")
        private String contactEmail;

        @JsonProperty("contactPrefContactMethod")
        private String contactPrefContactMethod;
    }

    @Setter
    @Getter
    public static class CustomerCategory {
        @JsonProperty("customerType")
        private String customerType;

        @JsonProperty("customerReferral")
        private String customerReferral;
    }

    @Setter
    @Getter
    public static class OrganizationHierarchy {
        @JsonProperty("parent")
        private int parent;

        @JsonProperty("child")
        private int child;

        @JsonProperty("grandChild")
        private int grandChild;
    }

    @Setter
    @Getter
    public static class ProgramDetails {
        @JsonProperty("partnerSponsoring")
        private String partnerSponsoring;

        @JsonProperty("sponsorshipBeneficiary")
        private String sponsorshipBeneficiary;

        @JsonProperty("programName")
        private String programName;

        @JsonProperty("programLeadName")
        private String programLeadName;

        @JsonProperty("programLeadPhone")
        private String programLeadPhone;

        @JsonProperty("programLeadEmail")
        private String programLeadEmail;
    }

    @Setter
    @Getter
    public static class Billing {
        @JsonProperty("billingFirstName")
        private String billingFirstName;

        @JsonProperty("billingLastName")
        private String billingLastName;

        @JsonProperty("billingAddr1")
        private String billingAddr1;

        @JsonProperty("billingAddr2")
        private String billingAddr2;

        @JsonProperty("billingCity")
        private String billingCity;

        @JsonProperty("billingState")
        private String billingState;

        @JsonProperty("billingCountry")
        private String billingCountry;

        @JsonProperty("billingZip")
        private String billingZip;

        @JsonProperty("billingPhone")
        private String billingPhone;

        @JsonProperty("billingEmail")
        private String billingEmail;

        @JsonProperty("billingAttention") // ✅ Add this line
        private String billingAttention;  // ✅ Add this field


    }

    @Setter
    @Getter
    public static class Shipping {
        @JsonProperty("attentionTo")
        private String attentionTo;

        @JsonProperty("shippingAddr1")
        private String shippingAddr1;

        @JsonProperty("shippingAddr2")
        private String shippingAddr2;

        @JsonProperty("shippingCity")
        private String shippingCity;

        @JsonProperty("shippingState")
        private String shippingState;

        @JsonProperty("shippingCountry")
        private String shippingCountry;

        @JsonProperty("shippingZip")
        private String shippingZip;

        @JsonProperty("shippingPhone")
        private String shippingPhone;

        @JsonProperty("shippingEmail")
        private String shippingEmail;
    }

    @Setter
    @Getter
    @ToString
    public static class OrderDetails {

        @JsonProperty("numberOfClips")
        private String numberOfClips;

        @JsonProperty("numberOf2DollarDevices")
        private String numberOf2DollarDevices;

        @JsonProperty("numberOf5DollarDevices")
        private String numberOf5DollarDevices;

        @JsonProperty("numberOf10DollarDevices")
        private String numberOf10DollarDevices;

        @JsonProperty("numberOf20DollarDevices")
        private String numberOf20DollarDevices;

        @JsonProperty("numberOf50DollarDevices")
        private String numberOf50DollarDevices;

        @JsonProperty("numberOfFloorStands")
        private String numberOfFloorStands;

        @JsonProperty("numberOfKettleDisplay")
        private String numberOfKettleDisplay;

        @JsonProperty("numberOfBatteryForKettleDisplay")
        private String numberOfBatteryForKettleDisplay;

        // New Canada Store fields
        @JsonProperty("threeDeviceDisplayEnglishQuantity")
        private String threeDeviceDisplayEnglishQuantity;

        @JsonProperty("threeDeviceDisplayFrenchQuantity")
        private String threeDeviceDisplayFrenchQuantity;

        @JsonProperty("threeDeviceDisplayBilingualQuantity")
        private String threeDeviceDisplayBilingualQuantity;

        @JsonProperty("singleCurveEnglishQuantity")
        private String singleCurveEnglishQuantity;

        @JsonProperty("singleCurveFrenchQuantity")
        private String singleCurveFrenchQuantity;

        @JsonProperty("singleCurveBilingualQuantity")
        private String singleCurveBilingualQuantity;

    }

    @Setter
    @Getter
    public static class PartnerInformation {

        @JsonProperty("provinceForProvincialCommand")
        private String provinceForProvincialCommand;

        @JsonProperty("provinceForHsbcBranch")
        private String provinceForHsbcBranch;

        @JsonProperty("provincialCommandSelect")
        private String provincialCommandSelect;

        @JsonProperty("selectedLanguage")
        private String selectedLanguage;

        @JsonProperty("selectedPartner")
        private String selectedPartner;

        @JsonProperty("legionProvincialCommand")
        private String legionProvincialCommand;

        @JsonProperty("legionBranch")
        private String legionBranch;

        @JsonProperty("legionEstimatedPopulationOfBranch")
        private Integer legionEstimatedPopulationOfBranch;

        @JsonProperty("hsbcBranch")
        private String hsbcBranch;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class BundleOrder {

        @JsonProperty("id")
        private String id;

        @JsonProperty("quantity")
        private String quantity;

        @JsonProperty("priceLevel")
        private String priceLevel;

        @JsonProperty("rate")
        private String rate;
    }

    @Setter
    @Getter
    public static class Pricing {

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("overrideSetupFee")
        private boolean overrideSetupFee;

        @JsonProperty("setupFeeAmount")
        private int setupFeeAmount;

        @JsonProperty("overrideMonthlyDeviceRentalFee")
        private boolean overrideMonthlyDeviceRentalFee;

        @JsonProperty("monthlyDeviceRentalFeeAmount")
        private int monthlyDeviceRentalFeeAmount;
    }

    @Setter
    @Getter
    public static class PaymentInformation {
        @JsonProperty("nameOnCard")
        private String nameOnCard;

        @JsonProperty("cardNumber")
        private String cardNumber;

        @JsonProperty("expirationDate")
        private String expirationDate;

        @JsonProperty("cvv")
        private String cvv;
    }

    @JsonProperty("header")
    private Header header;

    @JsonProperty("customerInformation")
    private CustomerInformation customerInformation;

    @JsonProperty("contactInformation")
    private ContactInformation contactInformation;

    @JsonProperty("customerCategory")
    private CustomerCategory customerCategory;

    @JsonProperty("organizationHierarchy")
    private OrganizationHierarchy organizationHierarchy;

    @JsonProperty("programDetails")
    private ProgramDetails programDetails;

    @JsonProperty("orderDetails")
    private OrderDetails orderDetails;

    @JsonProperty("bundleOrders")
    private List<BundleOrder> bundleOrders;

    @JsonProperty("partnerInformation")
    private PartnerInformation partnerInformation;

    @JsonProperty("pricing")
    private Pricing pricing;

    @JsonProperty("paymentInformation")
    private PaymentInformation paymentInformation;

    @JsonProperty("billing")
    private Billing billing;

    @JsonProperty("shipping")
    private Shipping shipping;

    @JsonProperty("memo")
    private String memo;

    @JsonProperty("action")
    private String action;

    // ✅ Add these three:
    @JsonProperty("division")
    private String division;

    @JsonProperty("ministryName")
    private String ministryName;

    @JsonProperty("respcNumber")
    private String respcNumber;

}