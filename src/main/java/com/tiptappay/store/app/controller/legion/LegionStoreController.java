package com.tiptappay.store.app.controller.legion;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiptappay.store.app.constant.AppConstants;
import com.tiptappay.store.app.constant.StoreConstants;
import com.tiptappay.store.app.dto.legion.CheckoutData;
import com.tiptappay.store.app.model.ContactAndShipping;
import com.tiptappay.store.app.model.CustomHttpResponse;
import com.tiptappay.store.app.model.PaymentReqRes;
import com.tiptappay.store.app.model.ResponsePayment;
import com.tiptappay.store.app.model.payload.Payload;
import com.tiptappay.store.app.service.*;
import com.tiptappay.store.app.service.legion.LegionStoreService;
import com.tiptappay.store.app.util.DataUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.tiptappay.store.app.constant.AppConstants.RedirectTemplates.*;
import static com.tiptappay.store.app.constant.AppConstants.ViewTemplates.*;

@Slf4j
@Controller
@RequestMapping("/legion")
@RequiredArgsConstructor
public class LegionStoreController {
    private final LegionStoreService legionStoreService;
    private final DataProcessorService dataProcessorService;
    private final OauthService oauthService;
    private final CookieService cookieService;

    @GetMapping("/fr/order")
    public String showOrderPagefr(Model model, HttpServletRequest request) {

        // Try loading order data from cookie
        String checkoutJson = cookieService.readCookie(request, "checkoutData");
        CheckoutData order;

        if (checkoutJson != null) {
            order = DataUtils.convertToObject(checkoutJson, CheckoutData.class);
        } else {
            order = new CheckoutData();
            order.setProvince(null);
        }
        model.addAttribute("order", order);
        return VIEW_LEGION_STORE_SCREEN_1_FR;
    }



    @PostMapping("/fr/order")
    public String submitOrderPagefr(@ModelAttribute("order") CheckoutData order,
                                  RedirectAttributes redirectAttributes,
                                  HttpServletResponse response) {
        String checkoutJson = DataUtils.convertToJsonString(order);
        cookieService.writeCookie(response, "checkoutData", checkoutJson);
        redirectAttributes.addFlashAttribute("order", order);
        return REDIRECT_LEGION_ORDER_CONFIRM_FR;
    }

    @GetMapping("/fr/order-confirm")
    public String showOrderConfirmationfr(Model model,
                                        HttpServletRequest request,
                                        RedirectAttributes redirectAttributes,
                                        @ModelAttribute("order") CheckoutData flashOrder) {
        CheckoutData order = null;
        String checkoutJson = cookieService.readCookie(request, "checkoutData");
        if (checkoutJson != null) {
            order = DataUtils.convertToObject(checkoutJson, CheckoutData.class);
        }

        if ((order == null || order.getContactFirstName() == null) && flashOrder != null) {
            order = flashOrder;
        }

        if (order == null || order.getContactFirstName() == null) {
            return REDIRECT_LEGION_ORDER_FR;
        }

        model.addAttribute("order", order);
        model.addAttribute("checkoutJson", DataUtils.convertToJsonString(order));
        return VIEW_LEGION_STORE_SCREEN_2_FR;
    }

    @PostMapping("/fr/order-confirm")
    public String confirmOrderfr(@ModelAttribute("order") CheckoutData order,
                               RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("order", order);
        return REDIRECT_CANADA_ORDER_SUBMIT_FR;
    }

    @PostMapping("/fr/order-submit")
    public String submitOrderfr(HttpServletRequest request,
                              HttpServletResponse response) {
        log.info("POST /legion/en/order-submit - Starting final order submission");
        try {
            String checkoutJson = cookieService.readCookie(request, "checkoutData");
            CheckoutData checkoutData = DataUtils.convertToObject(checkoutJson, CheckoutData.class);

            Payload payload = legionStoreService.preparePayload(checkoutData, "completeOrderWithPayment");

            PaymentReqRes paymentReqRes = new PaymentReqRes();
            paymentReqRes.setRequest(payload);

            String jsonPayload = DataUtils.convertToJsonString(payload);
            log.info("Prepared JSON Payload: {}", jsonPayload);

            CustomHttpResponse customHttpResponse = oauthService.runApiRequest(jsonPayload);
            String responseBody = customHttpResponse.getResponseBody();

            // ✅ Check if soNumber exists
            if (!responseBody.contains("\"soNumber\"") || responseBody.contains("\"soNumber\":null") || responseBody.contains("\"soNumber\":\"\"")) {
                log.warn("SO number not generated. Redirecting to order page.");
                return "redirect:/legion/en/failed";  // adjust if your order form URL is different
            }

            cookieService.writeCookie(response, "responsePaymentBody", responseBody);

        } catch (Exception e) {
            log.error("Failed to submit order payload: {}", e.getMessage(), e);
            return "redirect:/legion/en/order";
        }

        return REDIRECT_CANADA_ORDER_SUBMIT_FR;
    }


    @GetMapping("/fr/order-submit")
    public String showThanksPagefr(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Model model) {
        log.info("GET /legion/en/order-submit - Displaying thank you page");

        String responseBody = cookieService.readCookie(request, "responsePaymentBody");
        ResponsePayment responsePayment = dataProcessorService.handleResponse(responseBody);
        model.addAttribute("responsePayment", responsePayment);

        String checkoutJson = cookieService.readCookie(request, "checkoutData");
        CheckoutData checkoutData = DataUtils.convertToObject(checkoutJson, CheckoutData.class);
        model.addAttribute("checkoutData", checkoutData);
        cookieService.deleteCookie(response, "responsePaymentBody");
        cookieService.deleteCookie(response, "checkoutData");
        assert checkoutData != null;
        legionStoreService.campaignCall(checkoutData);
        return VIEW_LEGION_STORE_SCREEN_3_FR;
    }

    @GetMapping("/fr/failed")
    public String showFailedSubmissionPagefr(Model model) {
        model.addAttribute("message", "There was an issue submitting your order. Please try again or contact support.");
        return "legion/legion_screen_4";
    }

    //English Endpoints
    @GetMapping("/en/order")
    public String showOrderPage(Model model, HttpServletRequest request) {
        log.info("GET /legion/en/order - Displaying order page");

        // Try loading order data from cookie
        String checkoutJson = cookieService.readCookie(request, "checkoutData");
        CheckoutData order;

        if (checkoutJson != null) {
            order = DataUtils.convertToObject(checkoutJson, CheckoutData.class);
        } else {
            order = new CheckoutData();
            order.setProvince(null);
        }

        model.addAttribute("order", order);
        return VIEW_LEGION_STORE_SCREEN_1;
    }



    @PostMapping("/en/order")
    public String submitOrderPage(@ModelAttribute("order") CheckoutData order,
                                  RedirectAttributes redirectAttributes,
                                  HttpServletResponse response) {
        log.info("POST /legion/en/order - Submitting order form");
        String checkoutJson = DataUtils.convertToJsonString(order);
        cookieService.writeCookie(response, "checkoutData", checkoutJson);
        redirectAttributes.addFlashAttribute("order", order);
        return REDIRECT_LEGION_ORDER_CONFIRM;
    }

    @GetMapping("/en/order-confirm")
    public String showOrderConfirmation(Model model,
                                        HttpServletRequest request,
                                        RedirectAttributes redirectAttributes,
                                        @ModelAttribute("order") CheckoutData flashOrder) {
        log.info("GET /legion/en/order-confirm - Displaying confirmation page");

        CheckoutData order = null;
        String checkoutJson = cookieService.readCookie(request, "checkoutData");
        if (checkoutJson != null) {
            order = DataUtils.convertToObject(checkoutJson, CheckoutData.class);
        }

        if ((order == null || order.getContactFirstName() == null) && flashOrder != null) {
            order = flashOrder;
        }

        if (order == null || order.getContactFirstName() == null) {
            return REDIRECT_LEGION_ORDER;
        }

        model.addAttribute("order", order);
        model.addAttribute("checkoutJson", DataUtils.convertToJsonString(order));
        return VIEW_LEGION_STORE_SCREEN_2;
    }

    @PostMapping("/en/order-confirm")
    public String confirmOrder(@ModelAttribute("order") CheckoutData order,
                               RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("order", order);
        return REDIRECT_CANADA_ORDER_SUBMIT;
    }

    @PostMapping("/en/order-submit")
    public String submitOrder(HttpServletRequest request,
                              HttpServletResponse response) {

        log.info("POST /legion/en/order-submit - Starting final order submission");
        try {
            String checkoutJson = cookieService.readCookie(request, "checkoutData");
            CheckoutData checkoutData = DataUtils.convertToObject(checkoutJson, CheckoutData.class);

            Payload payload = legionStoreService.preparePayload(checkoutData, "completeOrderWithPayment");

            PaymentReqRes paymentReqRes = new PaymentReqRes();
            paymentReqRes.setRequest(payload);

            String jsonPayload = DataUtils.convertToJsonString(payload);
            log.info("Prepared JSON Payload: {}", jsonPayload);

            CustomHttpResponse customHttpResponse = oauthService.runApiRequest(jsonPayload);
            String responseBody = customHttpResponse.getResponseBody();

            JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
            boolean isSuccess = jsonNode.path("isSuccess").asBoolean(false);

            if (!isSuccess) {
                log.warn("Order submission failed (isSuccess is false). Redirecting to failed page.");
                return "redirect:/legion/en/failed";
            }

            // Success
            cookieService.writeCookie(response, "responsePaymentBody", responseBody);

        } catch (Exception e) {
            log.error("Failed to submit order payload: {}", e.getMessage(), e);
            return "redirect:/legion/en/order";
        }

        return REDIRECT_CANADA_ORDER_SUBMIT;
    }

    @GetMapping("/en/order-submit")
    public String showThanksPage(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Model model) {
        log.info("GET /legion/en/order-submit - Displaying thank you page");

        String responseBody = cookieService.readCookie(request, "responsePaymentBody");
        ResponsePayment responsePayment = dataProcessorService.handleResponse(responseBody);
        model.addAttribute("responsePayment", responsePayment);

        String checkoutJson = cookieService.readCookie(request, "checkoutData");
        CheckoutData checkoutData = DataUtils.convertToObject(checkoutJson, CheckoutData.class);
        model.addAttribute("checkoutData", checkoutData);
        cookieService.deleteCookie(response, "responsePaymentBody");
        cookieService.deleteCookie(response, "checkoutData");
        assert checkoutData != null;
        legionStoreService.campaignCall(checkoutData);
        return VIEW_LEGION_STORE_SCREEN_3;
    }

    @GetMapping("/en/failed")
    public String showFailedSubmissionPage(Model model) {
        model.addAttribute("message", "There was an issue submitting your order. Please try again or contact support.");
        return "legion/legion_screen_4";
    }

}
