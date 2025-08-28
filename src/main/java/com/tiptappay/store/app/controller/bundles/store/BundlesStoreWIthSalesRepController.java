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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static com.tiptappay.store.app.constant.AppConstants.ViewTemplates.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BundlesStoreWIthSalesRepController {

    private final BundlesStoreService bundlesStoreService;
    private final DataProcessorService dataProcessorService;
    private final OauthService oauthService;
    private final CookieService cookieService;
    private final CustomLoggerService logger;

    @GetMapping("/{salesRepId}/${stores.bundles.endpoint}/order")
    public String showTTPBundlesStoreScreenWithSalesRep(@PathVariable String salesRepId, Model model) {
        model.addAttribute("salesRepId", salesRepId);
        return VIEW_TTP_BUNDLES_STORE_SCREEN_1;
    }

    @PostMapping("/{salesRepId}/${stores.bundles.endpoint}/order")
    public String showTTPBundlesStoreScreen1Post(@PathVariable String salesRepId, Model model) {
        model.addAttribute("salesRepId", salesRepId);
        return String.format("redirect:/%s/bundles/order/customize", salesRepId);
    }

    @GetMapping("/{salesRepId}/${stores.bundles.endpoint}/order/customize")
    public String showTTPBundlesStoreScreen2(@PathVariable String salesRepId, Model model) {
        model.addAttribute("salesRepId", salesRepId);
        return VIEW_TTP_BUNDLES_STORE_SCREEN_2;
    }

    @PostMapping("/{salesRepId}/${stores.bundles.endpoint}/order/customize")
    public String showTTPBundlesStoreScreen2Post(@PathVariable String salesRepId, Model model) {
        model.addAttribute("salesRepId", salesRepId);
        return String.format("redirect:/%s/bundles/order/cart", salesRepId);
    }

    @GetMapping("/{salesRepId}/${stores.bundles.endpoint}/order/cart")
    public String showTTPBundlesStoreScreen3(@PathVariable String salesRepId, Model model) {
        model.addAttribute("salesRepId", salesRepId);
        return VIEW_TTP_BUNDLES_STORE_SCREEN_3;
    }

    @PostMapping("/{salesRepId}/${stores.bundles.endpoint}/order/cart")
    public String showTTPBundlesStoreScreen3Post(@PathVariable String salesRepId, Model model) {
        model.addAttribute("salesRepId", salesRepId);
        return String.format("redirect:/%s/bundles/order/checkout/shipping", salesRepId);
    }

    @GetMapping("/{salesRepId}/${stores.bundles.endpoint}/order/checkout/shipping")
    public String showTTPBundlesStoreScreen4(@PathVariable String salesRepId, Model model) {
        model.addAttribute("salesRepId", salesRepId);
        model.addAttribute("salesRepContactEmail", bundlesStoreService.getSalesRepEmailById(salesRepId));

        // Check for flash attribute
        if (model.containsAttribute("errorMessage")) {
            String errorMessage = (String) model.asMap().get("errorMessage");
            String errorCode = (String) model.asMap().get("errorCode");
            logger.logWarn(errorMessage);

            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("errorCode", errorCode);
        }

        return VIEW_TTP_BUNDLES_STORE_SCREEN_4;
    }

    @PostMapping("/{salesRepId}/${stores.bundles.endpoint}/order/checkout/shipping")
    public String showTTPBundlesStoreScreen4Post(@RequestParam("cart") String cartJson,
                                                 @RequestParam("checkout") String checkoutJson,
                                                 HttpServletResponse response,
                                                 RedirectAttributes redirectAttributes,
                                                 @PathVariable String salesRepId, Model model) {
        model.addAttribute("salesRepId", salesRepId);

        List<CartData> cartData = DataUtils.convertToObjectList(cartJson, CartData.class);
        CheckoutData checkoutData = DataUtils.convertToObject(checkoutJson, CheckoutData.class);

        assert checkoutData != null;
        CampaignDateValidation campaignDateValidation = bundlesStoreService.isCampaignStartDateValid(checkoutData.getCampaignStartDate(), checkoutData.getToday());

        if (!campaignDateValidation.isDateValid()) {
            // Add flash attribute for error message
            redirectAttributes.addFlashAttribute("errorMessage",
                    "'For Program Start Date' you’ve entered "
                            + campaignDateValidation.getDaysBetween()
                            + " days that is less than the required 4 weeks needed to guarantee the delivery on time. " +
                            "We cannot ensure your order will be ready and shipping in time for your desired delivery date. " +
                            "Please choose either a date that is 4 weeks from now or click on the 'more info' link below the date field for other options.");
            redirectAttributes.addFlashAttribute("errorCode", "104"); // Error Code for Invalid Campaign Start Date
            return String.format("redirect:/%s/bundles/order/checkout/shipping", salesRepId);
        }

        // REQUEST ===============
        QuoteReqRes quoteReqRes = new QuoteReqRes();

        Payload payload = bundlesStoreService.preparePayload(salesRepId, checkoutData, cartData, null, "quoteTaxAndShipping");
        quoteReqRes.setRequest(payload);
        String jsonPayload = DataUtils.convertToJsonString(payload);

        logger.logInfo("Request for quoteTaxAndShipping is sending...");
        logger.logDebug("quoteTaxAndShipping : " + jsonPayload);

        try {
            CustomHttpResponse customHttpResponse = oauthService.runApiRequest(jsonPayload);
            cookieService.writeCookie(response, "responseQuoteBody", customHttpResponse.getResponseBody());

        } catch (Exception exception) {
            logger.logError(AppConstants.AppMessages.ERROR_SENDING_PAYLOAD, exception);
            // Add flash attribute for error message
            redirectAttributes.addFlashAttribute("errorMessage", "It looks like there’s a problem with your address. Please ensure it’s correct.");
            redirectAttributes.addFlashAttribute("errorCode", "101"); // Error Code for Exception
            return String.format("redirect:/%s/bundles/order/checkout/shipping", salesRepId);
        }
        // ========================

        return String.format("redirect:/%s/bundles/order/checkout/payment", salesRepId);
    }

    @GetMapping("/{salesRepId}/${stores.bundles.endpoint}/order/checkout/payment")
    public String showTTPBundlesStoreScreen5(HttpServletRequest request,
                                             RedirectAttributes redirectAttributes,
                                             @PathVariable String salesRepId,
                                             Model model) {
        model.addAttribute("salesRepId", salesRepId);

        String responseBody = cookieService.readCookie(request, "responseQuoteBody");
        ResponseQuote responseQuote = dataProcessorService.handleQuoteResponse(responseBody);

        if (!responseQuote.isSuccess() || responseQuote.getShippingAmount() == 0) {
            // Add flash attribute for error message
            redirectAttributes.addFlashAttribute("errorMessage", "It looks like there’s a problem with your address. Please ensure it’s correct.");
            redirectAttributes.addFlashAttribute("errorCode", "102"); // Error Code for Invalid Address
            return String.format("redirect:/%s/bundles/order/checkout/shipping", salesRepId);
        }

        model.addAttribute("responseQuote", responseQuote);

        return VIEW_TTP_BUNDLES_STORE_SCREEN_5;
    }

    @PostMapping("/{salesRepId}/${stores.bundles.endpoint}/order/checkout/payment")
    public String showTTPBundlesStoreScreen5Post(@RequestParam("cart") String cartJson,
                                                 @RequestParam("checkout") String checkoutJson,
                                                 @RequestParam("payment") String paymentJson,
                                                 HttpServletResponse response,
                                                 @PathVariable String salesRepId,
                                                 Model model) {
        model.addAttribute("salesRepId", salesRepId);

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
        // ========================
        return String.format("redirect:/%s/bundles/order/complete", salesRepId);
    }

    @GetMapping("/{salesRepId}/${stores.bundles.endpoint}/order/complete")
    public String showTTPBundlesStoreScreen6(HttpServletRequest request,
                                             HttpServletResponse response,
                                             @PathVariable String salesRepId,
                                             Model model) {
        model.addAttribute("salesRepId", salesRepId);

        // Read cookies
        String responseBody = cookieService.readCookie(request, "responsePaymentBody");
        String checkoutJson = cookieService.readCookie(request, "checkoutData");
        String cartJson = cookieService.readCookie(request, "cartData");

        // Deserialize
        ResponsePayment responsePayment = dataProcessorService.handleResponse(responseBody);
        CheckoutData checkoutData = DataUtils.convertToObject(checkoutJson, CheckoutData.class);
        List<CartData> cartDataList = DataUtils.convertToObjectList(cartJson, CartData.class);

        // Cleanup early
        cookieService.deleteCookie(response, "responsePaymentBody");

        model.addAttribute("responsePayment", responsePayment);

        // If payment successful
        if (responsePayment != null && responsePayment.isSuccess()
                && responsePayment.getSoNumber() != null && responsePayment.isCcApproved()) {

            // Create organization & membership
            if (checkoutData != null) {
                OrganizationResponse orgResponse = bundlesStoreService.createOrganization(checkoutData, responsePayment.getSoNumber());

                if (orgResponse != null && orgResponse.getOrganization() != null && orgResponse.getOrganization().getId() > 0) {
                    bundlesStoreService.createMembership(orgResponse.getOrganization().getId(), checkoutData);
                }
            }

            // Filter custom signage bundles
            List<CartData> customizedBundles = cartDataList.stream()
                    .filter(CartData::isCustomizedSignage)
                    .toList();

            if (!customizedBundles.isEmpty()) {
                model.addAttribute("customizedSignageBundles", customizedBundles);

                // Get sales rep email
                String salesRepEmail = bundlesStoreService.getSalesRepEmailById(salesRepId);
                String salesRepName = bundlesStoreService.getSalesRepNameById(salesRepId);


                if (salesRepEmail != null && !salesRepEmail.isEmpty()) {
                    String emailBody = String.format("""
                            <p>Dear %s,</p>
                            <p>The following order includes a request for customized signage:</p>
                            <p><strong>Sales Order Number:</strong> %s</p>
                            <p>Please follow up as needed to coordinate the signage details with the customer.</p>
                            <p>Best regards,<br/>Tiptap Onboarding System</p>
                            """,
                            salesRepName != null ? salesRepName : "Sales Team", // fallback
                            responsePayment.getSoNumber());

                    String emailJson = String.format("""
                            {
                              "body": %s,
                              "from": "onboarding@tiptappay.com",
                              "subject": "Custom Signage Request - %s",
                              "to": "%s"
                            }
                            """,
                            DataUtils.escapeJsonString(emailBody),
                            responsePayment.getSoNumber(),
                            salesRepEmail);


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
                        log.info("Custom signage email sent to {}. Response: {}", salesRepEmail, emailResponse.body());

                    } catch (Exception e) {
                        log.error("Failed to send custom signage email to {}", salesRepEmail, e);
                    }
                } else {
                    log.warn("No valid email found for salesRepId: {}. Skipping signage email.", salesRepId);
                }
            }

            // Final cookie cleanup
            cookieService.deleteCookie(response, "responseQuoteBody");
            cookieService.deleteCookie(response, "checkoutData");
            cookieService.deleteCookie(response, "cartData");

            return VIEW_TTP_BUNDLES_STORE_SCREEN_6;
        }

        return VIEW_TTP_BUNDLES_STORE_SCREEN_7;
    }

}

