package com.tiptappay.store.app.controller.benevity.education;

import com.tiptappay.store.app.constant.AppConstants;
import com.tiptappay.store.app.dto.benevity.store.BenevityCause;
import com.tiptappay.store.app.dto.benevity.store.MyAccountToken;
import com.tiptappay.store.app.dto.bundles.store.CartData;
import com.tiptappay.store.app.dto.bundles.store.CheckoutData;
import com.tiptappay.store.app.dto.bundles.store.PaymentData;
import com.tiptappay.store.app.model.*;
import com.tiptappay.store.app.model.membership.Membership;
import com.tiptappay.store.app.model.organization.OrganizationRequest;
import com.tiptappay.store.app.model.organization.OrganizationResponse;
import com.tiptappay.store.app.model.payload.Payload;
import com.tiptappay.store.app.service.CookieService;
import com.tiptappay.store.app.service.DataProcessorService;
import com.tiptappay.store.app.service.OauthService;
import com.tiptappay.store.app.service.app.CustomLoggerService;
import com.tiptappay.store.app.service.benevity.BenevityStoreService;
import com.tiptappay.store.app.service.membership.MembershipService;
import com.tiptappay.store.app.service.organization.OrganizationService;
import com.tiptappay.store.app.util.AppUtil;
import com.tiptappay.store.app.util.DataUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.tiptappay.store.app.constant.AppConstants.Attributes.ATTRIBUTE_ERROR_CODE;
import static com.tiptappay.store.app.constant.AppConstants.Attributes.ATTRIBUTE_ERROR_MESSAGE;
import static com.tiptappay.store.app.constant.AppConstants.CookiesConstants.*;
import static com.tiptappay.store.app.constant.AppConstants.MembershipRoles.MEMBERSHIP_ROLE_ADMIN;
import static com.tiptappay.store.app.constant.AppConstants.MembershipRoles.MEMBERSHIP_ROLE_MANAGER;
import static com.tiptappay.store.app.constant.AppConstants.MembershipStatus.MEMBERSHIP_STATUS_ACTIVE;
import static com.tiptappay.store.app.constant.AppConstants.MembershipStatus.MEMBERSHIP_STATUS_PENDING_APPROVAL;
import static com.tiptappay.store.app.constant.AppConstants.NetSuiteActions.ACTION_COMPLETE_ORDER_WITH_PAYMENT;
import static com.tiptappay.store.app.constant.AppConstants.NetSuiteActions.ACTION_QUOTE_TAX_AND_SHIPPING;
import static com.tiptappay.store.app.constant.AppConstants.OrderFormTypes.FORM_TYPE_BENEVITY_EDUCATION;
import static com.tiptappay.store.app.constant.AppConstants.RedirectTemplates.*;
import static com.tiptappay.store.app.constant.AppConstants.ViewTemplates.*;

@Slf4j
@Controller
@RequestMapping("${stores.benevity.education.endpoint}")
@RequiredArgsConstructor
public class BenevityEducationStoreController {

    private final BenevityStoreService benevityStoreService;
    private final OrganizationService organizationService;
    private final MembershipService membershipService;
    private final DataProcessorService dataProcessorService;
    private final OauthService oauthService;
    private final CookieService cookieService;
    private final CustomLoggerService logger;

    @Value("${stores.benevity.education.endpoint}")
    private String endpoint;

    @Value("${stores.benevity.my-account.token.expiry}")
    private int myAccountTokenExpiry;

    @GetMapping("/order")
    public String showBenevityStorePricing(HttpServletResponse response) {
        benevityStoreService.deleteMyAccountToken(response, COOKIE_ID_BENEVITY_EDUCATION);
        return VIEW_BENEVITY_EDUCATION_STORE_PRICING;
    }

    @PostMapping("/order")
    public String postBenevityStorePricing() {
        return REDIRECT_BENEVITY_EDUCATION_ORDER_CUSTOMIZE;
    }

    @GetMapping("/order/customize")
    public String showBenevityStoreCustomize(HttpServletResponse response) {
        benevityStoreService.deleteMyAccountToken(response, COOKIE_ID_BENEVITY_EDUCATION);
        return VIEW_BENEVITY_EDUCATION_STORE_CUSTOMIZE;
    }

    @PostMapping("/order/customize")
    public String postBenevityStoreCustomize() {
        return REDIRECT_BENEVITY_EDUCATION_ORDER_CART;
    }

    @GetMapping("/order/cart")
    public String showBenevityStoreCart() {
        return VIEW_BENEVITY_EDUCATION_STORE_CART;
    }

    @PostMapping("/order/cart")
    public String postBenevityStoreCart() {
        return REDIRECT_BENEVITY_EDUCATION_ORDER_CHECKOUT_ORGANIZATION_DETAILS;
    }

    @GetMapping("/order/checkout/organization-details")
    public String showBenevityStoreOrganizationDetails() {
        return VIEW_BENEVITY_EDUCATION_STORE_ORGANIZATION_DETAILS;
    }

    @PostMapping("/order/checkout/organization-details")
    public String postBenevityStoreOrganizationDetails(@RequestParam("cart") String cartJson,
                                                       @RequestParam("benevityCause") String benevityCauseJson) {

        logger.logDebug("Cart (JSON): " + cartJson);
        logger.logDebug("Benevity Cause (JSON): " + benevityCauseJson);

        return REDIRECT_BENEVITY_EDUCATION_ORDER_CHECKOUT_SHIPPING;
    }

    @GetMapping("/order/checkout/shipping")
    public String showBenevityStoreShipping(Model model) {

        if (model.containsAttribute(ATTRIBUTE_ERROR_MESSAGE)) {
            String errorMessage = (String) model.asMap().get(ATTRIBUTE_ERROR_MESSAGE);
            String errorCode = (String) model.asMap().get(ATTRIBUTE_ERROR_CODE);
            logger.logWarn(errorMessage);

            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            model.addAttribute(ATTRIBUTE_ERROR_CODE, errorCode);
        }

        return VIEW_BENEVITY_EDUCATION_STORE_SHIPPING;
    }

    @PostMapping("/order/checkout/shipping")
    public String postBenevityStoreShipping(@RequestParam("cart") String cartJson,
                                            @RequestParam("checkout") String checkoutJson,
                                            @RequestParam("benevityCause") String benevityCauseJson,
                                            HttpServletResponse response, RedirectAttributes redirectAttributes) {

        logger.logDebug("Cart (JSON): " + cartJson);
        logger.logDebug("Benevity Cause (JSON): " + benevityCauseJson);
        logger.logDebug("Checkout (JSON): " + checkoutJson);

        List<CartData> cartData = DataUtils.convertToObjectList(cartJson, CartData.class);
        CheckoutData checkoutData = DataUtils.convertToObject(checkoutJson, CheckoutData.class);

        QuoteReqRes quoteReqRes = new QuoteReqRes();

        Payload payload = benevityStoreService.preparePayload(FORM_TYPE_BENEVITY_EDUCATION, null, checkoutData, cartData, null, ACTION_QUOTE_TAX_AND_SHIPPING);
        quoteReqRes.setRequest(payload);
        String jsonPayload = DataUtils.convertToJsonString(payload);

        logger.logInfo("Request for quoteTaxAndShipping is sending...");
        logger.logDebug("quoteTaxAndShipping : " + jsonPayload);

        try {
            CustomHttpResponse customHttpResponse = oauthService.runApiRequest(jsonPayload);
            cookieService.writeCookie(response, COOKIE_RESPONSE_QUOTE_BODY + COOKIE_ID_BENEVITY_EDUCATION, customHttpResponse.getResponseBody());

        } catch (Exception exception) {
            logger.logError(ErrorCode.EXCEPTION_NETSUITE_QUOTE_REQUEST.getServerMessage(), exception);
            // Add flash attribute for error message
            redirectAttributes.addFlashAttribute(ATTRIBUTE_ERROR_MESSAGE,
                    ErrorCode.EXCEPTION_NETSUITE_QUOTE_REQUEST.getUserMessage());

            redirectAttributes.addFlashAttribute(ATTRIBUTE_ERROR_CODE,
                    ErrorCode.EXCEPTION_NETSUITE_QUOTE_REQUEST.getCode());

            return REDIRECT_BENEVITY_EDUCATION_ORDER_CHECKOUT_SHIPPING;
        }
        // ========================

        return REDIRECT_BENEVITY_EDUCATION_ORDER_CHECKOUT_PAYMENT;
    }

    @GetMapping("/order/checkout/payment")
    public String showBenevityStorePayment(HttpServletRequest request,
                                           Model model,
                                           RedirectAttributes redirectAttributes) {

        String responseBody = cookieService.readCookie(request, COOKIE_RESPONSE_QUOTE_BODY + COOKIE_ID_BENEVITY_EDUCATION);
        ResponseQuote responseQuote = dataProcessorService.handleQuoteResponse(responseBody);

        if (!responseQuote.isSuccess() || responseQuote.getShippingAmount() == 0) {

            if (responseBody.contains("Invalid Field Value")) {
                redirectAttributes.addFlashAttribute(ATTRIBUTE_ERROR_CODE,
                        ErrorCode.NETSUITE_INVALID_FIELD_ISSUE.getCode());

                redirectAttributes.addFlashAttribute(ATTRIBUTE_ERROR_MESSAGE, ErrorCode.NETSUITE_INVALID_FIELD_ISSUE.getUserMessage());

                logger.logError("", new Exception(ErrorCode.NETSUITE_INVALID_FIELD_ISSUE.getServerMessage() + responseBody));
            } else {
                redirectAttributes.addFlashAttribute(ATTRIBUTE_ERROR_CODE,
                        ErrorCode.INVALID_ADDRESS_ENTRY.getCode());

                redirectAttributes.addFlashAttribute(ATTRIBUTE_ERROR_MESSAGE, ErrorCode.INVALID_ADDRESS_ENTRY.getUserMessage());

                logger.logError("", new Exception(ErrorCode.INVALID_ADDRESS_ENTRY.getServerMessage() + responseBody));
            }


            return REDIRECT_BENEVITY_EDUCATION_ORDER_CHECKOUT_SHIPPING;
        }

        model.addAttribute("responseQuote", responseQuote);

        return VIEW_BENEVITY_EDUCATION_STORE_PAYMENT;
    }

    @PostMapping("/order/checkout/payment")
    public String postBenevityStorePayment(@RequestParam("cart") String cartJson,
                                           @RequestParam("checkout") String checkoutJson,
                                           @RequestParam("benevityCause") String benevityCauseJson,
                                           @RequestParam("payment") String paymentJson,
                                           HttpServletResponse response) {
        logger.logDebug("Cart (JSON): " + cartJson);
        logger.logDebug("Benevity Cause (JSON): " + benevityCauseJson);
        logger.logDebug("Checkout (JSON): " + checkoutJson);

        List<CartData> cartData = DataUtils.convertToObjectList(cartJson, CartData.class);
        CheckoutData checkoutData = DataUtils.convertToObject(checkoutJson, CheckoutData.class);
        PaymentData paymentData = DataUtils.convertToObject(paymentJson, PaymentData.class);
        BenevityCause benevityCause = DataUtils.convertToObject(benevityCauseJson, BenevityCause.class);

        // REQUEST ===============
        PaymentReqRes paymentReqRes = new PaymentReqRes();

        Payload payload = benevityStoreService.preparePayload(FORM_TYPE_BENEVITY_EDUCATION, null, checkoutData, cartData, paymentData, ACTION_COMPLETE_ORDER_WITH_PAYMENT);
        paymentReqRes.setRequest(payload);

        String jsonPayload = DataUtils.convertToJsonString(payload);
        logger.logInfo("Request for completeOrderWithPayment is sending...");
        logger.logInfo("Contact: "
                + payload.getContactInformation().getContactFirstName().trim() + " " + payload.getContactInformation().getContactLastName().trim()
                + ", Order Details: " + payload.getOrderDetails().toString()
                + ", Bundle Orders: " + payload.getBundleOrders().toString());
//        logger.logDebug("completeOrderWithPayment : " + jsonPayload); // Better never log this!

        // Watch whether token setup completed as expected
        boolean isMyAccountPrepSuccess = true;

        // Delete token if exist, will be created newly
        benevityStoreService.deleteMyAccountToken(response, COOKIE_ID_BENEVITY_EDUCATION);

        // Create My Account Token
        MyAccountToken myAccountToken = new MyAccountToken();

        try {
            CustomHttpResponse customHttpResponse = oauthService.runApiRequest(jsonPayload);
            cookieService.writeCookie(response, COOKIE_RESPONSE_PAYMENT_BODY + COOKIE_ID_BENEVITY_EDUCATION, customHttpResponse.getResponseBody());
            ResponsePayment responsePayment = dataProcessorService.handleResponse(customHttpResponse.getResponseBody());


            if (benevityStoreService.isValidPayment(responsePayment)) {
                if (benevityCause != null) {

                    // Prepare Request for Organization Create
                    OrganizationRequest organizationRequest =
                            organizationService.generateNewOrganizationRequest(benevityCause, responsePayment);
                    // POST organization
                    OrganizationResponse organizationResponse = organizationService.postOrganization(organizationRequest);

                    if (organizationResponse != null) { // Organization Newly Created
                        // Add tiptap dashboard id
                        logger.logInfo("Organization created successfully : " + organizationResponse.getOrganization().getId());
                        logger.logDebug("Organization: " + organizationResponse.getOrganization().toString());

                        // Set Info for Admin Process
                        myAccountToken.setRole(MEMBERSHIP_ROLE_ADMIN);
                        myAccountToken.setStatus(MEMBERSHIP_STATUS_ACTIVE);

                        // Set Organization Info
                        myAccountToken.setOrganizationId(organizationResponse.getOrganization().getId());
                        myAccountToken.setOrganizationName(organizationResponse.getOrganization().getName());
                        myAccountToken.setNewOrganization(true);

                        // Set Additional Info
                        myAccountToken.setCampaignId(organizationResponse.getDefaultCampaignId());
                        myAccountToken.setPaymentPageId(organizationResponse.getDefaultPaymentPageId());

                    } else {
                        // Organization Exist
                        if(organizationRequest.getOrganization().getId() != Integer.MIN_VALUE) {
                            logger.logInfo("Organization retrieved successfully : " + organizationRequest.getOrganization().getId());

                            // Fetch Memberships
                            List<Membership> memberships =
                                    membershipService.fetchMembershipsByOrganizationId(organizationRequest.getOrganization().getId());

                            // Make first member admin
                            if (memberships.isEmpty()) {
                                // Set Info for Admin Process
                                myAccountToken.setRole(MEMBERSHIP_ROLE_ADMIN);
                                myAccountToken.setStatus(MEMBERSHIP_STATUS_ACTIVE);

                            // Make other members manager
                            } else {
                                if (organizationRequest.getOrganization().getId() != 0) {
                                    // Set Info for Manager Process
                                    myAccountToken.setRole(MEMBERSHIP_ROLE_MANAGER);
                                    myAccountToken.setStatus(MEMBERSHIP_STATUS_PENDING_APPROVAL);
                                } else {
                                    isMyAccountPrepSuccess = false;
                                }
                            }

                            // Set Organization Info
                            myAccountToken.setOrganizationId(organizationRequest.getOrganization().getId());
                            myAccountToken.setOrganizationName(organizationRequest.getOrganization().getName());
                            myAccountToken.setNewOrganization(false);
                        } else {
                            return VIEW_BENEVITY_EDUCATION_STORE_COMPLETE_FAIL;
                        }
                    }

                } else {
                    logger.logInfo("Benevity Cause is null, company cannot be created!");
                }
            } else {
                return VIEW_BENEVITY_EDUCATION_STORE_COMPLETE_FAIL;
            }

        } catch (Exception exception) {
            log.error(AppConstants.AppMessages.ERROR_SENDING_PAYLOAD + " : {}", exception.getMessage());
            return VIEW_BENEVITY_EDUCATION_STORE_COMPLETE_FAIL;
        }

        // Add Watcher
        myAccountToken.setPrepSuccess(isMyAccountPrepSuccess);

        // Add Account Details
        myAccountToken.setGivenName(payload.getContactInformation().getContactFirstName());
        myAccountToken.setFamilyName(payload.getContactInformation().getContactLastName());
        // Update phone format for API call : from +1 (123) 456-7890 to +11234567890
        myAccountToken.setPhoneNumber("+" + AppUtil.extractNumericDigits(payload.getContactInformation().getContactPhone()));

        // Set Token Expiry
        logger.logInfo("My Account Setup Token Expiry : " + myAccountTokenExpiry + " minutes");
        myAccountToken.setTokenExp(AppUtil.getCurrentTimeMillisPlus(TimeUnit.MINUTES, myAccountTokenExpiry));

        // Write completed token
        benevityStoreService.writeMyAccountToken(response, myAccountToken, COOKIE_ID_BENEVITY_EDUCATION);

        return REDIRECT_BENEVITY_EDUCATION_ORDER_COMPLETE;
    }

    @GetMapping("/order/complete")
    public String showBenevityStoreComplete(HttpServletRequest request,
                                            HttpServletResponse response,
                                            Model model) {

        String responseBody = cookieService.readCookie(request, COOKIE_RESPONSE_PAYMENT_BODY + COOKIE_ID_BENEVITY_EDUCATION);
        ResponsePayment responsePayment = dataProcessorService.handleResponse(responseBody);

        MyAccountToken myAccountToken = benevityStoreService.readMyAccountToken(request, response, COOKIE_ID_BENEVITY_EDUCATION);
        if (!benevityStoreService.validateMyAccountToken(myAccountToken, response, COOKIE_ID_BENEVITY_EDUCATION)) {
            return String.format(REDIRECT_STRING_FORMAT_BENEVITY_SESSION_EXPIRED, endpoint);
        }

        model.addAttribute("responsePayment", responsePayment);

        cookieService.deleteCookie(response, COOKIE_RESPONSE_PAYMENT_BODY + COOKIE_ID_BENEVITY_EDUCATION);

        if (benevityStoreService.isValidPayment(responsePayment)) {
            cookieService.deleteCookie(response, COOKIE_RESPONSE_QUOTE_BODY + COOKIE_ID_BENEVITY_EDUCATION);

            if (myAccountToken.isPrepSuccess()) {
                return myAccountToken.isNewOrganization()
                        ? VIEW_BENEVITY_EDUCATION_STORE_COMPLETE_START_COLLECTING
                        : VIEW_BENEVITY_EDUCATION_STORE_COMPLETE_KEEP_COLLECTING;
            }

            return VIEW_BENEVITY_EDUCATION_STORE_ORDER_COMPLETE_MY_ACCOUNT_PREP_FAILED;
        }

        return VIEW_BENEVITY_EDUCATION_STORE_COMPLETE_FAIL;
    }

}
