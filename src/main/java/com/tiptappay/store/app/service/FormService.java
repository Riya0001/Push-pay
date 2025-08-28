package com.tiptappay.store.app.service;

import com.tiptappay.store.app.model.*;
import com.tiptappay.store.app.model.payload.Payload;
import com.tiptappay.store.app.service.OauthService;
import com.tiptappay.store.app.util.DataUtils;
import com.tiptappay.store.app.constant.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class FormService {

    private final OauthService oauthService;
    private final DataProcessorService dataProcessorService;

    public Form setFormDefaults() {
        Form form = new Form();
        form.setCustomerName("Tiptap Store Corp.");
        form.setCustomerType("Enterprise");
        form.setCustomerReferral("");

        form.setParent(35891);
        form.setChild(35892);
        form.setGrandChild(1);

        form.setPartnerSponsoring("");
        form.setSponsorshipBeneficiary("");
        form.setProgramName("");
        form.setProgramLeadName("");
        form.setProgramLeadPhoneCountryCode("");
        form.setProgramLeadPhone("");
        form.setProgramLeadEmail("");

        return form;
    }

    public Pricing setPricingDefaults() {
        return new Pricing(AppConstants.PricingConstants.OVERRIDE_SETUP_FEE, AppConstants.PricingConstants.SETUP_FEE_AMOUNT_IN_CENTS, AppConstants.PricingConstants.ONE_TIME_FEE_AMOUNT_IN_CENTS, AppConstants.PricingConstants.MONTHLY_FEE_AMOUNT_IN_CENTS, AppConstants.PricingConstants.DEFAULT_CURRENCY);
    }

    private static Payload preparePayload(Form form, CheckoutForm checkoutForm, Bag bag, Pricing storePricing, String action) {
        Payload payload = new Payload();
        try {
            // Set Header
            Payload.Header header = new Payload.Header();
            header.setTimestamp(new Date());
            payload.setHeader(header);

            // Set Customer Information
            Payload.CustomerInformation customerInformation = getCustomerInformation(form);
            payload.setCustomerInformation(customerInformation);

            // Set Contact Information
            Payload.ContactInformation contactInformation = getContactInformation(form);
            payload.setContactInformation(contactInformation);

            // Set Customer Category
            Payload.CustomerCategory customerCategory = getCustomerCategory(form);
            payload.setCustomerCategory(customerCategory);

            // Set Organization Hierarchy
            Payload.OrganizationHierarchy organizationHierarchy = getOrganizationHierarchy(form);
            payload.setOrganizationHierarchy(organizationHierarchy);

            // Set Program Details
            Payload.ProgramDetails programDetails = getProgramDetails(form);
            payload.setProgramDetails(programDetails);

            // Set Payment Information
            Payload.PaymentInformation paymentInformation = getPaymentInformation(checkoutForm);
            payload.setPaymentInformation(paymentInformation);

            // Set Billing
            Payload.Billing billing = getBilling(checkoutForm);
            payload.setBilling(billing);

            // Set Shipping
            Payload.Shipping shipping = getShipping(form);
            payload.setShipping(shipping);

            // Set Order Details
            Payload.OrderDetails orderDetails = getOrderDetails(bag);
            payload.setOrderDetails(orderDetails);

            // Set Pricing
            Payload.Pricing pricing = getPricing(storePricing);
            payload.setPricing(pricing);

            // Set Memo
            payload.setMemo(form.getMemo());

            if(action != null) {
                payload.setAction(action);
            }

        } catch (Exception exception) {
            log.error(AppConstants.AppMessages.ERROR_PREPARING_PAYLOAD + " : {}", exception.getMessage());
        }
        return payload;
    }

    private static Payload.CustomerInformation getCustomerInformation(Form form) {
        Payload.CustomerInformation customerInformation = new Payload.CustomerInformation();
        customerInformation.setCustomerName(form.getCustomerName());
        customerInformation.setCustomerPhone(form.getContactPhoneCountryCode() + " " + form.getContactPhone());
        customerInformation.setCustomerEmail(form.getContactEmail());
        customerInformation.setCustomerPrefContactMethod(form.getContactPrefContactMethod());
        return customerInformation;
    }

    private static Payload.ContactInformation getContactInformation(Form form) {
        Payload.ContactInformation contactInformation = new Payload.ContactInformation();
        contactInformation.setContactFirstName(form.getContactFirstName());
        contactInformation.setContactLastName(form.getContactLastName());
        contactInformation.setContactPhone(form.getContactPhoneCountryCode() + " " + form.getContactPhone());
        contactInformation.setContactEmail(form.getContactEmail());
        contactInformation.setContactPrefContactMethod(form.getContactPrefContactMethod());
        return contactInformation;
    }

    private static Payload.CustomerCategory getCustomerCategory(Form form) {
        Payload.CustomerCategory customerCategory = new Payload.CustomerCategory();
        customerCategory.setCustomerType(form.getCustomerType());
        customerCategory.setCustomerReferral(form.getCustomerReferral());
        return customerCategory;
    }

    private static Payload.OrganizationHierarchy getOrganizationHierarchy(Form form) {
        Payload.OrganizationHierarchy organizationHierarchy = new Payload.OrganizationHierarchy();
        organizationHierarchy.setParent(form.getParent());
        organizationHierarchy.setChild(form.getChild());
        organizationHierarchy.setGrandChild(form.getGrandChild());
        return organizationHierarchy;
    }

    private static Payload.ProgramDetails getProgramDetails(Form form) {
        Payload.ProgramDetails programDetails = new Payload.ProgramDetails();
        programDetails.setPartnerSponsoring("");
        programDetails.setSponsorshipBeneficiary("");
        programDetails.setProgramName("");
        programDetails.setProgramLeadName("");
        programDetails.setProgramLeadPhone("");
        programDetails.setProgramLeadEmail("");
        return programDetails;
    }

    private static Payload.PaymentInformation getPaymentInformation(CheckoutForm checkoutForm) {
        Payload.PaymentInformation paymentInformation = new Payload.PaymentInformation();
        paymentInformation.setCardNumber(checkoutForm == null ? "" : checkoutForm.getCardNumber());
        paymentInformation.setExpirationDate(checkoutForm == null ? "" : checkoutForm.getExpirationMonth() + "/" + checkoutForm.getExpirationYear());
        paymentInformation.setCvv(checkoutForm == null ? "" : checkoutForm.getCvv());
        return paymentInformation;
    }

    private static Payload.Billing getBilling(CheckoutForm checkoutForm) {
        Payload.Billing billing = new Payload.Billing();
        billing.setBillingFirstName(checkoutForm == null ? "" : checkoutForm.getBillingFirstName());
        billing.setBillingLastName(checkoutForm == null ? "" : checkoutForm.getBillingLastName());
        billing.setBillingAddr1(checkoutForm == null ? "" : checkoutForm.getBillingAddr1());
        billing.setBillingAddr2(checkoutForm == null ? "" : checkoutForm.getBillingAddr2());
        billing.setBillingCity(checkoutForm == null ? "" : checkoutForm.getBillingCity());

        billing.setBillingState(checkoutForm == null ? "" :
                checkoutForm.getBillingCountry().equalsIgnoreCase("canada") ?
                        checkoutForm.getBillingStateCA() : checkoutForm.getBillingStateUS());

        billing.setBillingCountry(checkoutForm == null ? "" : checkoutForm.getBillingCountry());
        billing.setBillingZip(checkoutForm == null ? "" : checkoutForm.getBillingPostalCode());
        return billing;
    }

    private static Payload.Shipping getShipping(Form form) {
        Payload.Shipping shipping = new Payload.Shipping();
        shipping.setAttentionTo(form.getAttentionTo());
        shipping.setShippingAddr1(form.getShippingAddr1());
        shipping.setShippingAddr2(form.getShippingAddr2());
        shipping.setShippingCity(form.getShippingCity());

        shipping.setShippingState(form.getShippingCountry().equalsIgnoreCase("canada") ? form.getShippingStateCA() : form.getShippingStateUS());

        shipping.setShippingCountry(form.getShippingCountry());
        shipping.setShippingZip(form.getShippingPostalCode());
        shipping.setShippingPhone(form.getShippingPhoneCountryCode() + " " + form.getShippingPhone());
        shipping.setShippingEmail(form.getShippingEmail());
        return shipping;
    }

    private static Payload.Pricing getPricing(Pricing pricing) {
        Payload.Pricing payloadPricing = new Payload.Pricing();
        payloadPricing.setOverrideSetupFee(pricing.isOverrideSetupFee());
        payloadPricing.setSetupFeeAmount(99); // TODO : Change later
        return payloadPricing;
    }

    private static Payload.OrderDetails getOrderDetails(Bag bag) {
        Payload.OrderDetails orderDetails = new Payload.OrderDetails();
        orderDetails.setNumberOfClips(String.valueOf(bag.getMiniClip2Dollar() + bag.getMiniClip5Dollar() + bag.getMiniClip10Dollar() + bag.getMiniClip20Dollar()));
        orderDetails.setNumberOf2DollarDevices(String.valueOf(bag.getMiniClip2Dollar()));
        orderDetails.setNumberOf5DollarDevices(String.valueOf(bag.getMiniClip5Dollar()));
        orderDetails.setNumberOf10DollarDevices(String.valueOf(bag.getMiniClip10Dollar()));
        orderDetails.setNumberOf20DollarDevices(String.valueOf(bag.getMiniClip20Dollar()));
        return orderDetails;
    }

    public ResponsePayment sendRequestToNetSuite(Form form, CheckoutForm checkoutForm, Bag bag, Pricing pricing) {
        Payload payload = preparePayload(form, checkoutForm, bag, pricing, AppConstants.NetSuiteActions.ACTION_COMPLETE_ORDER_WITH_PAYMENT);
        String jsonPayload = DataUtils.convertToJsonString(payload);

        ResponsePayment responsePayment = null;
        try {
            CustomHttpResponse customHttpResponse = oauthService.runApiRequest(jsonPayload);
            responsePayment = dataProcessorService.handleResponse(customHttpResponse.getResponseBody());
        } catch (Exception exception) {
            log.error(AppConstants.AppMessages.ERROR_SENDING_PAYLOAD + " : {}", exception.getMessage());
        }
        return responsePayment;
    }

    public ResponseQuote sendQuoteRequestToNetSuite(Form form, CheckoutForm checkoutForm, Bag bag, Pricing pricing) {
        Payload payload = preparePayload(form, checkoutForm, bag, pricing, AppConstants.NetSuiteActions.ACTION_QUOTE_TAX_AND_SHIPPING);
        String jsonPayload = DataUtils.convertToJsonString(payload);

        ResponseQuote responseQuote = null;
        try {
            CustomHttpResponse customHttpResponse = oauthService.runApiRequest(jsonPayload);
            responseQuote = dataProcessorService.handleQuoteResponse(customHttpResponse.getResponseBody());
        } catch (Exception exception) {
            log.error(AppConstants.AppMessages.ERROR_SENDING_PAYLOAD + " : {}", exception.getMessage());
        }

        return responseQuote;
    }

    public String generateTokenString(Form form, Bag bag) {
        return form.getShippingAddr1().trim() + "," + form.getShippingAddr2().trim() + "," + form.getShippingCity().trim() + ","
                + form.getShippingStateUS().trim() + "," + form.getShippingStateCA().trim() + "," + form.getShippingCountry().trim()
                + "," + form.getShippingZip().trim() + "," + form.getShippingPostalCode().trim() + "," + bag.toString();
    }

    // TODO : Temporary Solution
    public boolean isFormValid(Form form) {
        return !form.getContactFirstName().isEmpty() && !form.getContactLastName().isEmpty() &&
                !form.getContactPhone().isEmpty() && !form.getContactEmail().isEmpty() &&
                !form.getContactPrefContactMethod().isEmpty() && !form.getAttentionTo().isEmpty() &&
                !form.getShippingAddr1().isEmpty() && !form.getShippingCity().isEmpty() &&
                !form.getShippingPostalCode().isEmpty() && !form.getShippingZip().isEmpty() &&
                !form.getShippingPhone().isEmpty() && !form.getShippingEmail().isEmpty();
    }

    public static String formatCardNumber(String cardNumber) {
        if(cardNumber == null || cardNumber.isEmpty()){
            return "";
        }

        cardNumber = cardNumber.replaceAll("\\s+", "");

        StringBuilder formattedStr = new StringBuilder();

        for(int i = 0; i < cardNumber.length(); i++){
            if(i > 0 && i % 4 == 0){
                formattedStr.append(" ");
            }
            formattedStr.append(cardNumber.charAt(i));
        }
        return formattedStr.toString();
    }
}
