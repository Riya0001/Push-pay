package com.tiptappay.store.app.controller.bundles.store;

import com.tiptappay.store.app.constant.AppConstants;
import com.tiptappay.store.app.dto.bundles.store.CartData;
import com.tiptappay.store.app.dto.bundles.store.CheckoutData;
import com.tiptappay.store.app.dto.bundles.store.PaymentData;
import com.tiptappay.store.app.model.*;
import com.tiptappay.store.app.model.organization.OrganizationResponse;
import com.tiptappay.store.app.model.payload.Payload;
import com.tiptappay.store.app.service.CookieService;
import com.tiptappay.store.app.service.DataProcessorService;
import com.tiptappay.store.app.service.GeoLocationService;
import com.tiptappay.store.app.service.OauthService;
import com.tiptappay.store.app.service.app.CustomLoggerService;
import com.tiptappay.store.app.service.bundles.BundlesStoreService;
import com.tiptappay.store.app.util.DataUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static com.tiptappay.store.app.constant.AppConstants.Attributes.ATTRIBUTE_ERROR_CODE;
import static com.tiptappay.store.app.constant.AppConstants.Attributes.ATTRIBUTE_ERROR_MESSAGE;
import static com.tiptappay.store.app.constant.AppConstants.RedirectTemplates.*;
import static com.tiptappay.store.app.constant.AppConstants.ViewTemplates.*;

@Slf4j
@Controller
@RequestMapping("${stores.bundles.endpoint}")
@RequiredArgsConstructor
public class BundlesStoreController {

    private final BundlesStoreService bundlesStoreService;
    private final DataProcessorService dataProcessorService;
    private final OauthService oauthService;
    private final CookieService cookieService;
    private final CustomLoggerService logger;
    private final GeoLocationService geoLocationService;

    @GetMapping("/order")
    public String showTTPBundlesStoreScreen1() {
        return VIEW_TTP_BUNDLES_STORE_SCREEN_1;
    }

    @PostMapping("/order")
    public String showTTPBundlesStoreScreen1Post() {
        return REDIRECT_TTP_BUNDLES_ORDER_CUSTOMIZE;
    }

    @GetMapping("/order/customize")
    public String showTTPBundlesStoreScreen2() {
        return VIEW_TTP_BUNDLES_STORE_SCREEN_2;
    }

    @PostMapping("/order/customize")
    public String showTTPBundlesStoreScreen2Post() {
        return REDIRECT_TTP_BUNDLES_ORDER_CART;
    }

    @GetMapping("/order/cart")
    public String showTTPBundlesStoreScreen3() {
        return VIEW_TTP_BUNDLES_STORE_SCREEN_3;
    }

    @PostMapping("/order/cart")
    public String showTTPBundlesStoreScreen3Post() {
        return REDIRECT_TTP_BUNDLES_ORDER_CHECKOUT_SHIPPING;
    }

    @GetMapping("/order/checkout/shipping")
    public String showTTPBundlesStoreScreen4(HttpServletRequest request, Model model) {

        // Handle flash attributes
        if (model.containsAttribute("errorMessage")) {
            String errorMessage = (String) model.asMap().get("errorMessage");
            Object errorCodeObj = model.asMap().get("errorCode");
            String errorCode = errorCodeObj != null ? errorCodeObj.toString() : null;
            logger.logWarn(errorMessage);

            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("errorCode", errorCode);
        }

        // Geo IP-based country detection
        String countryCode = geoLocationService.resolveCountryCode(request);
        String countryName = switch (countryCode) {
            case "CA" -> "Canada";
            case "US" -> "USA";
            case "IN" -> "India";
            default   -> "Unknown";
        };
        model.addAttribute("userCountry", countryName);
        return VIEW_TTP_BUNDLES_STORE_SCREEN_4;
    }

    @PostMapping("/order/checkout/shipping")
    public String showTTPBundlesStoreScreen4Post(@RequestParam("cart") String cartJson,
                                                 @RequestParam("checkout") String checkoutJson,
                                                 HttpServletResponse response, RedirectAttributes redirectAttributes) {

        List<CartData> cartData = DataUtils.convertToObjectList(cartJson, CartData.class);
        CheckoutData checkoutData = DataUtils.convertToObject(checkoutJson, CheckoutData.class);

        assert checkoutData != null;
        CampaignDateValidation campaignDateValidation = bundlesStoreService.isCampaignStartDateValid(checkoutData.getCampaignStartDate(), checkoutData.getToday());

        if (!campaignDateValidation.isDateValid()) {
            // Add flash attribute for error message
            redirectAttributes.addFlashAttribute(ATTRIBUTE_ERROR_MESSAGE,
                    String.format(ErrorCode.INVALID_CAMPAIGN_START_DATE.getUserMessage(), campaignDateValidation.getDaysBetween()));

            redirectAttributes.addFlashAttribute(ATTRIBUTE_ERROR_CODE, ErrorCode.INVALID_CAMPAIGN_START_DATE.getCode());

            logger.logInfo(ErrorCode.INVALID_CAMPAIGN_START_DATE.getServerMessage());

            return REDIRECT_TTP_BUNDLES_ORDER_CHECKOUT_SHIPPING;
        }

        // REQUEST ===============
        QuoteReqRes quoteReqRes = new QuoteReqRes();

        Payload payload = bundlesStoreService.preparePayload(null, checkoutData, cartData, null, "quoteTaxAndShipping");
        quoteReqRes.setRequest(payload);
        String jsonPayload = DataUtils.convertToJsonString(payload);

        logger.logInfo("Request for quoteTaxAndShipping is sending...");
        logger.logDebug("quoteTaxAndShipping : " + jsonPayload);

        try {
            CustomHttpResponse customHttpResponse = oauthService.runApiRequest(jsonPayload);
            cookieService.writeCookie(response, "responseQuoteBody", customHttpResponse.getResponseBody());

        } catch (Exception exception) {
            logger.logError(ErrorCode.EXCEPTION_NETSUITE_QUOTE_REQUEST.getServerMessage(), exception);
            // Add flash attribute for error message
            redirectAttributes.addFlashAttribute(ATTRIBUTE_ERROR_MESSAGE,
                    ErrorCode.EXCEPTION_NETSUITE_QUOTE_REQUEST.getUserMessage());

            redirectAttributes.addFlashAttribute(ATTRIBUTE_ERROR_CODE,
                    ErrorCode.EXCEPTION_NETSUITE_QUOTE_REQUEST.getCode());

            return REDIRECT_TTP_BUNDLES_ORDER_CHECKOUT_SHIPPING;
        }
        return REDIRECT_TTP_BUNDLES_ORDER_CHECKOUT_PAYMENT;
    }

    @GetMapping("/order/checkout/payment")
    public String showTTPBundlesStoreScreen5(HttpServletRequest request,
                                             Model model,
                                             RedirectAttributes redirectAttributes) {

        String responseBody = cookieService.readCookie(request, "responseQuoteBody");
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

            return REDIRECT_TTP_BUNDLES_ORDER_CHECKOUT_SHIPPING;
        }

        model.addAttribute("responseQuote", responseQuote);

        return VIEW_TTP_BUNDLES_STORE_SCREEN_5;
    }

    @PostMapping("/order/checkout/payment")
    public String showTTPBundlesStoreScreen5Post(@RequestParam("cart") String cartJson,
                                                 @RequestParam("checkout") String checkoutJson,
                                                 @RequestParam("payment") String paymentJson,
                                                 HttpServletResponse response) {
        // Convert JSON to objects
        List<CartData> cartData = DataUtils.convertToObjectList(cartJson, CartData.class);
        CheckoutData checkoutData = DataUtils.convertToObject(checkoutJson, CheckoutData.class);
        PaymentData paymentData = DataUtils.convertToObject(paymentJson, PaymentData.class);

        // Save checkout data in cookie for later use in order completion
        cookieService.writeCookie(response, "checkoutData", checkoutJson);
        cookieService.writeCookie(response, "cartData", cartJson);

        // REQUEST ===============
        PaymentReqRes paymentReqRes = new PaymentReqRes();
        Payload payload = bundlesStoreService.preparePayload(null, checkoutData, cartData, paymentData, "completeOrderWithPayment");
        paymentReqRes.setRequest(payload);

        String jsonPayload = DataUtils.convertToJsonString(payload);
        logger.logInfo("Request for completeOrderWithPayment is sending...");
        logger.logInfo("Contact: "
                + payload.getContactInformation().getContactFirstName().trim() + " " + payload.getContactInformation().getContactLastName().trim()
                + ", Order Details: " + payload.getOrderDetails().toString()
                + ", Bundle Orders: " + payload.getBundleOrders().toString());
//        logger.logDebug("completeOrderWithPayment : " + jsonPayload); // Better never log this!

        try {
            CustomHttpResponse customHttpResponse = oauthService.runApiRequest(jsonPayload);
            cookieService.writeCookie(response, "responsePaymentBody", customHttpResponse.getResponseBody());
        } catch (Exception exception) {
            log.error(AppConstants.AppMessages.ERROR_SENDING_PAYLOAD + " : {}", exception.getMessage());
        }

        return REDIRECT_TTP_BUNDLES_ORDER_COMPLETE;
    }

    @GetMapping("/order/complete")
    public String showTTPBundlesStoreScreen6(HttpServletRequest request,
                                             HttpServletResponse response,
                                             Model model) {

        // Read response and checkout data
        String responseBody = cookieService.readCookie(request, "responsePaymentBody");
        ResponsePayment responsePayment = dataProcessorService.handleResponse(responseBody);
        model.addAttribute("responsePayment", responsePayment);

        String checkoutJson = cookieService.readCookie(request, "checkoutData");
        CheckoutData checkoutData = DataUtils.convertToObject(checkoutJson, CheckoutData.class);

        String cartJson = cookieService.readCookie(request, "cartData");
        List<CartData> cartDataList = DataUtils.convertToObjectList(cartJson, CartData.class);

        // Clean up payment response cookie early
        cookieService.deleteCookie(response, "responsePaymentBody");

        // Handle successful payment
        if (responsePayment != null && responsePayment.isSuccess()
                && responsePayment.getSoNumber() != null && responsePayment.isCcApproved()) {

            if (checkoutData != null) {
                // Create organization and membership
                OrganizationResponse organizationResponse = bundlesStoreService.createOrganization(checkoutData, responsePayment.getSoNumber());

                if (organizationResponse != null &&
                        organizationResponse.getOrganization() != null &&
                        organizationResponse.getOrganization().getId() > 0) {

                    int organizationId = organizationResponse.getOrganization().getId();
                    bundlesStoreService.createMembership(organizationId, checkoutData);
                }
            }

            // Filter for custom signage bundles
            List<CartData> customizedBundles = cartDataList.stream()
                    .filter(CartData::isCustomizedSignage)
                    .toList();

            if (!customizedBundles.isEmpty()) {
                model.addAttribute("customizedSignageBundles", customizedBundles);

                // Send email to sales associate
                String emailBody = String.format("""
                    <p>Dear Sales Team,</p>
                    <p>The following order includes a request for customized signage:</p>
                    <p><strong>Sales Order Number:</strong> %s</p>
                    <p>Please follow up as needed to coordinate the signage details with the customer.</p>
                    <p>Best regards,<br/>Tiptap Onboarding System</p>
                    """,
                        responsePayment.getSoNumber());

                String emailJson = String.format("""
                    {
                      "body": %s,
                      "from": "onboarding@tiptappay.com",
                      "subject": "Custom Signage Request - %s",
                      "to": "riyay2k2000@gmail.com"
                    }
                    """,
                DataUtils.escapeJsonString(emailBody),
                responsePayment.getSoNumber()
                );


                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest emailRequest = HttpRequest.newBuilder()
                            .uri(URI.create("https://app.tiptappay.ca/api/athena/v1/emails/send"))
                            .header("accept", "application/json")
                            .header("Authorization", "Api-Key 6w/oLW0kn7pEPFG2MyPZdLAhKNr73jF3o4f4BpO1wdRCmz5UegKLiSNzzgVz2l2P")
                            .header("content-type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(emailJson))
                            .build();

                    HttpResponse<String> emailResponse = client.send(emailRequest, HttpResponse.BodyHandlers.ofString());
                    log.info("Custom signage email sent. Response: {}", emailResponse.body());

                } catch (Exception e) {
                    log.error("Failed to send custom signage email", e);
                }
            }

            // Final cleanup
            cookieService.deleteCookie(response, "responseQuoteBody");
            cookieService.deleteCookie(response, "checkoutData");
            cookieService.deleteCookie(response, "cartData");

            return VIEW_TTP_BUNDLES_STORE_SCREEN_6;
        }

        return VIEW_TTP_BUNDLES_STORE_SCREEN_7;
    }

}
