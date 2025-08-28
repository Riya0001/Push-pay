package com.tiptappay.store.app.controller.mosques.store;

import com.tiptappay.store.app.constant.StoreConstants;
import com.tiptappay.store.app.model.*;
import com.tiptappay.store.app.dto.mosques.store.MosquesStoreShoppingBag;
import com.tiptappay.store.app.service.StepperService;
import com.tiptappay.store.app.service.StoreService;
import com.tiptappay.store.app.service.mosques.MosquesStoreService;
import com.tiptappay.store.app.util.FormatCents;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.tiptappay.store.app.constant.AppConstants.Attributes.*;
import static com.tiptappay.store.app.constant.AppConstants.RedirectTemplates.*;
import static com.tiptappay.store.app.constant.AppConstants.ViewTemplates.*;

@Slf4j
@Controller
@RequestMapping("${stores.mosques.endpoint}")
@RequiredArgsConstructor
public class MosquesStoreController {

    private final MosquesStoreService mosquesStoreService;
    private final StoreService storeService;
    private final StepperService stepperService;

    @Value("${app.developer.insights.enabled}")
    private boolean developerInsightsEnabled;

    @GetMapping("/order")
    public String showMosquesStoreScreen1(HttpServletRequest request, HttpServletResponse response, Model model) {

        MosquesStoreShoppingBag mosquesStoreShoppingBag =
                (MosquesStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.MosquesStore.STORE_ID);
        if (mosquesStoreShoppingBag == null) {
            return REDIRECT_MOSQUES_ORDER;
        }

        Stepper stepper = stepperService.getStepperByScreen(1);
        List<Product> products = mosquesStoreService.getProductList();

        // Returning attribute from shopping bag
        String emptyBag = (String) model.getAttribute(ATTRIBUTE_EMPTY_BAG);
        if (emptyBag != null) {
            model.addAttribute(ATTRIBUTE_EMPTY_BAG, emptyBag);
        }

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, mosquesStoreShoppingBag);
        model.addAttribute(ATTRIBUTE_PRODUCTS, products);

        return VIEW_MOSQUES_STORE_SCREEN_1;
    }

    @PostMapping("/order")
    public String showMosquesStoreScreen1Post(HttpServletRequest request, HttpServletResponse response) {

        MosquesStoreShoppingBag mosquesStoreShoppingBag =
                (MosquesStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.MosquesStore.STORE_ID);
        if (mosquesStoreShoppingBag == null || mosquesStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_MOSQUES_ORDER;
        }

        return REDIRECT_MOSQUES_CONTACT_AND_SHIPPING;
    }

    @GetMapping("/contact-and-shipping")
    public String showMosquesStoreScreen2(HttpServletRequest request,
                                          HttpServletResponse response,
                                          Model model) {

        MosquesStoreShoppingBag mosquesStoreShoppingBag =
                (MosquesStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.MosquesStore.STORE_ID);
        if (mosquesStoreShoppingBag == null || mosquesStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_MOSQUES_ORDER;
        }

        ContactAndShipping contactAndShipping = storeService.handleCookieCANDS(request, response, StoreConstants.MosquesStore.STORE_ID);
        if (contactAndShipping == null) {
            contactAndShipping = new ContactAndShipping();
        }

        Stepper stepper = stepperService.getStepperByScreen(2);

        ResponseQuote quoteResp = (ResponseQuote) model.getAttribute(ATTRIBUTE_QUOTE_FAILED);
        if (quoteResp != null) {
            // TODO: Currently defined as invalid address, however we do not know exact reason of failure.
            //  To understand it added as attribute, but this is not a good approach.
            //  User should see some custom messages not exact error message
            model.addAttribute("invalidAddress", Arrays.toString(quoteResp.getErrors()));
        }

        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, mosquesStoreShoppingBag);
        model.addAttribute(ATTRIBUTE_CONTACT_AND_SHIPPING, contactAndShipping);

        return VIEW_MOSQUES_STORE_SCREEN_2;
    }

    @PostMapping("/contact-and-shipping")
    public String showMosquesStoreScreen2Post(ContactAndShipping contactAndShipping,
                                              HttpServletRequest request,
                                              HttpServletResponse response,
                                              RedirectAttributes redirectAttributes) {

        MosquesStoreShoppingBag mosquesStoreShoppingBag =
                (MosquesStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.MosquesStore.STORE_ID);
        if (mosquesStoreShoppingBag == null || mosquesStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_MOSQUES_ORDER;
        }

        if (contactAndShipping == null) {
            return REDIRECT_MOSQUES_CONTACT_AND_SHIPPING;
        }

        storeService.setCookieCANDS(response, contactAndShipping, StoreConstants.MosquesStore.STORE_ID);

        ResponseQuoteCookie responseQuoteCookie =
                storeService.handleCookieTSQ(request, response, mosquesStoreShoppingBag, contactAndShipping, StoreConstants.MosquesStore.STORE_ID);
        if (responseQuoteCookie == null) {
            log.error("MosquesStoreController.showMosquesStoreScreen2Post > {}", "Tax and Shipping Quote is null");
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, "Unexpected Error Occurred : SS");
            return REDIRECT_MOSQUES_CONTACT_AND_SHIPPING;
        }

        if (!responseQuoteCookie.getQuote().isSuccess()) {
            log.error("MosquesStoreController.showMosquesStoreScreen2Post > {}", Arrays.toString(responseQuoteCookie.getQuote().getErrors()));
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, responseQuoteCookie.getQuote().getErrors());
            return REDIRECT_MOSQUES_CONTACT_AND_SHIPPING;
        }

        storeService.setCookieTSQ(response, responseQuoteCookie, StoreConstants.MosquesStore.STORE_ID);

        return REDIRECT_MOSQUES_ORDER_REVIEW;
    }

    @GetMapping("/order-review")
    public String showMosquesStoreScreen4(HttpServletRequest request,
                                          HttpServletResponse response,
                                          RedirectAttributes redirectAttributes,
                                          Model model) {

        MosquesStoreShoppingBag mosquesStoreShoppingBag =
                (MosquesStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.MosquesStore.STORE_ID);
        if (mosquesStoreShoppingBag == null || mosquesStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_MOSQUES_ORDER;
        }

        ContactAndShipping contactAndShipping =
                storeService.handleCookieCANDS(request, response, StoreConstants.MosquesStore.STORE_ID);
        if (contactAndShipping == null) {
            return REDIRECT_MOSQUES_CONTACT_AND_SHIPPING;
        }

        ResponseQuoteCookie responseQuoteCookie =
                storeService.handleCookieTSQ(request, response, mosquesStoreShoppingBag, contactAndShipping, StoreConstants.MosquesStore.STORE_ID);
        if (responseQuoteCookie == null) {
            log.error("MosquesStoreController.showMosquesStoreScreen4 > {}", "Tax and Shipping Quote is null");
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, "Unexpected Error Occurred : SS");
            return REDIRECT_MOSQUES_CONTACT_AND_SHIPPING;
        }

        if (!responseQuoteCookie.getQuote().isSuccess()) {
            log.error("MosquesStoreController.showMosquesStoreScreen4 > {}", Arrays.toString(responseQuoteCookie.getQuote().getErrors()));
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, responseQuoteCookie.getQuote());
            return REDIRECT_MOSQUES_CONTACT_AND_SHIPPING;
        }

        storeService.setCookieTSQ(response, responseQuoteCookie, StoreConstants.MosquesStore.STORE_ID);

        // Set Quote Values
        mosquesStoreShoppingBag.setShippingAmountInCents((int) (responseQuoteCookie.getQuote().getShippingAmount() * 100));
        mosquesStoreShoppingBag.setTaxAmountInCents((int) (responseQuoteCookie.getQuote().getTaxAmount() * 100));

        Stepper stepper = stepperService.getStepperByScreen(4);

        List<Product> products = mosquesStoreService.getProductList(
                mosquesStoreShoppingBag.getFloorStandQty(),
                mosquesStoreShoppingBag.getDevice5DollarQty(),
                mosquesStoreShoppingBag.getDevice10DollarQty(),
                mosquesStoreShoppingBag.getDevice20DollarQty()
        );

        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, mosquesStoreShoppingBag);
        model.addAttribute(ATTRIBUTE_PRODUCTS, products);
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_FORMAT_CENTS, new FormatCents());

        return VIEW_MOSQUES_STORE_SCREEN_4;
    }

    @PostMapping("/order-review")
    public String showMosquesStoreScreen4Post(HttpServletRequest request,
                                              HttpServletResponse response,
                                              RedirectAttributes redirectAttributes) {

        MosquesStoreShoppingBag mosquesStoreShoppingBag =
                (MosquesStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.MosquesStore.STORE_ID);
        if (mosquesStoreShoppingBag == null || mosquesStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_MOSQUES_ORDER;
        }

        ContactAndShipping contactAndShipping =
                storeService.handleCookieCANDS(request, response, StoreConstants.MosquesStore.STORE_ID);
        if (contactAndShipping == null) {
            return REDIRECT_MOSQUES_CONTACT_AND_SHIPPING;
        }

        ResponseQuoteCookie responseQuoteCookie =
                storeService.handleCookieTSQ(request, response, mosquesStoreShoppingBag, contactAndShipping, StoreConstants.MosquesStore.STORE_ID);
        if (responseQuoteCookie == null) {
            log.error("MosquesStoreController.showMosquesStoreScreen4Post > {}", "Tax and Shipping Quote is null");
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, "Unexpected Error Occurred : SS");
            return REDIRECT_MOSQUES_CONTACT_AND_SHIPPING;
        }

        if (!responseQuoteCookie.getQuote().isSuccess()) {
            log.error("MosquesStoreController.showMosquesStoreScreen4Post > {}", Arrays.toString(responseQuoteCookie.getQuote().getErrors()));
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, responseQuoteCookie.getQuote());
            return REDIRECT_MOSQUES_CONTACT_AND_SHIPPING;
        }

        storeService.setCookieTSQ(response, responseQuoteCookie, StoreConstants.MosquesStore.STORE_ID);

        return REDIRECT_MOSQUES_PAYMENT;
    }

    @GetMapping("/payment")
    public String showMosquesStoreScreen5(HttpServletRequest request,
                                          HttpServletResponse response,
                                          RedirectAttributes redirectAttributes,
                                          Model model) {

        MosquesStoreShoppingBag mosquesStoreShoppingBag =
                (MosquesStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.MosquesStore.STORE_ID);
        if (mosquesStoreShoppingBag == null || mosquesStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_MOSQUES_ORDER;
        }

        ContactAndShipping contactAndShipping =
                storeService.handleCookieCANDS(request, response, StoreConstants.MosquesStore.STORE_ID);
        if (contactAndShipping == null) {
            return REDIRECT_MOSQUES_CONTACT_AND_SHIPPING;
        }

        ResponseQuoteCookie responseQuoteCookie =
                storeService.handleCookieTSQ(request, response, mosquesStoreShoppingBag, contactAndShipping, StoreConstants.MosquesStore.STORE_ID);
        if (responseQuoteCookie == null) {
            log.error("MosquesStoreController.showMosquesStoreScreen5 > {}", "Tax and Shipping Quote is null");
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, "Unexpected Error Occurred : SS");
            return REDIRECT_MOSQUES_CONTACT_AND_SHIPPING;
        }

        if (!responseQuoteCookie.getQuote().isSuccess()) {
            log.error("MosquesStoreController.showMosquesStoreScreen5 > {}", Arrays.toString(responseQuoteCookie.getQuote().getErrors()));
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, responseQuoteCookie.getQuote());
            return REDIRECT_MOSQUES_CONTACT_AND_SHIPPING;
        }

        storeService.setCookieTSQ(response, responseQuoteCookie, StoreConstants.MosquesStore.STORE_ID);

        // Set Quote Values
        mosquesStoreShoppingBag.setShippingAmountInCents((int) (responseQuoteCookie.getQuote().getShippingAmount() * 100));
        mosquesStoreShoppingBag.setTaxAmountInCents((int) (responseQuoteCookie.getQuote().getTaxAmount() * 100));

        Stepper stepper = stepperService.getStepperByScreen(5);

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, mosquesStoreShoppingBag);
        model.addAttribute(ATTRIBUTE_CONTACT_AND_SHIPPING, contactAndShipping);
        model.addAttribute(ATTRIBUTE_PAYMENT, new Payment());
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);

        return VIEW_MOSQUES_STORE_SCREEN_5;
    }

    @PostMapping("/payment")
    public String showMosquesStoreScreen5Post(Payment payment,
                                              HttpServletRequest request,
                                              HttpServletResponse response,
                                              RedirectAttributes redirectAttributes) {


        MosquesStoreShoppingBag mosquesStoreShoppingBag =
                (MosquesStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.MosquesStore.STORE_ID);
        if (mosquesStoreShoppingBag == null || mosquesStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_MOSQUES_ORDER;
        }

        ContactAndShipping contactAndShipping =
                storeService.handleCookieCANDS(request, response, StoreConstants.MosquesStore.STORE_ID);
        if (contactAndShipping == null) {
            return REDIRECT_MOSQUES_CONTACT_AND_SHIPPING;
        }

        ResponseQuoteCookie responseQuoteCookie =
                storeService.handleCookieTSQ(request, response, mosquesStoreShoppingBag, contactAndShipping, StoreConstants.MosquesStore.STORE_ID);
        if (responseQuoteCookie == null) {
            log.error("MosquesStoreController.showMosquesStoreScreen5 > {}", "Tax and Shipping Quote is null");
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, "Unexpected Error Occurred : SS");
            return REDIRECT_MOSQUES_CONTACT_AND_SHIPPING;
        }

        if (!responseQuoteCookie.getQuote().isSuccess()) {
            log.error("MosquesStoreController.showMosquesStoreScreen5 > {}", Arrays.toString(responseQuoteCookie.getQuote().getErrors()));
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, responseQuoteCookie.getQuote());
            return REDIRECT_MOSQUES_CONTACT_AND_SHIPPING;
        }

        storeService.setCookieTSQ(response, responseQuoteCookie, StoreConstants.MosquesStore.STORE_ID);

        // Set Quote Values
        mosquesStoreShoppingBag.setShippingAmountInCents((int) (responseQuoteCookie.getQuote().getShippingAmount() * 100));
        mosquesStoreShoppingBag.setTaxAmountInCents((int) (responseQuoteCookie.getQuote().getTaxAmount() * 100));

        // Send Request to NetSuite
        PaymentReqRes paymentReqRes =
                storeService.sendRequestToNetSuite(response, mosquesStoreShoppingBag, contactAndShipping, payment, StoreConstants.MosquesStore.STORE_ID);

        redirectAttributes.addFlashAttribute(ATTRIBUTE_RESPONSE_PAYMENT, paymentReqRes.getResponse());

        if (paymentReqRes.getResponse() != null && paymentReqRes.getResponse().isCcApproved()) {
            return REDIRECT_MOSQUES_THANKS;
        }

        return REDIRECT_MOSQUES_FAILED;
    }

    @GetMapping("/thanks")
    public String showMosquesStoreScreen6(Model model, HttpServletRequest request, HttpServletResponse response) {

        boolean allCookiesAvailable =
                storeService.isAllCookiesAvailable(request, StoreConstants.MosquesStore.STORE_ID);
        if (!allCookiesAvailable) {
            return REDIRECT_MOSQUES_ORDER;
        }

        ResponsePayment responsePayment = (ResponsePayment) model.getAttribute(ATTRIBUTE_RESPONSE_PAYMENT);

        if (responsePayment == null) {
            return REDIRECT_MOSQUES_ERROR;
        }

        Stepper stepper = stepperService.getStepperByScreen(6);

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_RESPONSE_PAYMENT, responsePayment);
        model.addAttribute(ATTRIBUTE_DEVELOPER_INSIGHTS_ENABLED, developerInsightsEnabled);

        if (developerInsightsEnabled) {

            QuoteReqRes quoteReqRes = storeService.handleCookieNCQ(request, response, StoreConstants.MosquesStore.STORE_ID);
            PaymentReqRes paymentReqRes = storeService.handleCookieNCP(request, response, StoreConstants.MosquesStore.STORE_ID);

            if (quoteReqRes != null) {
                String prettyJsonQuoteRequest = storeService.generatePrettyJson(quoteReqRes.getRequest());
                model.addAttribute(ATTRIBUTE_PRETTY_JSON_QUOTE_REQUEST, prettyJsonQuoteRequest);

                String prettyJsonQuoteResponse = storeService.generatePrettyJson(quoteReqRes.getResponse());
                model.addAttribute(ATTRIBUTE_PRETTY_JSON_QUOTE_RESPONSE, prettyJsonQuoteResponse);
            }

            if (paymentReqRes != null) {
                String prettyJsonPaymentRequest = storeService.generatePrettyJson(paymentReqRes.getRequest());
                model.addAttribute(ATTRIBUTE_PRETTY_JSON_PAYMENT_REQUEST, prettyJsonPaymentRequest);

                String prettyJsonPaymentResponse = storeService.generatePrettyJson(paymentReqRes.getResponse());
                model.addAttribute(ATTRIBUTE_PRETTY_JSON_PAYMENT_RESPONSE, prettyJsonPaymentResponse);
            }
        }

        storeService.deleteAllCookiesByStoreId(response, StoreConstants.MosquesStore.STORE_ID);

        return VIEW_MOSQUES_STORE_SCREEN_6;
    }

    @GetMapping("/failed")
    public String showMosquesStoreScreen7(Model model, HttpServletRequest request, HttpServletResponse response) {

        boolean allCookiesAvailable = storeService.isAllCookiesAvailable(request, StoreConstants.MosquesStore.STORE_ID);
        if (!allCookiesAvailable) {
            return REDIRECT_MOSQUES_ORDER;
        }

        ResponsePayment responsePayment = (ResponsePayment) model.getAttribute(ATTRIBUTE_RESPONSE_PAYMENT);

        if (responsePayment == null) {
            return REDIRECT_MOSQUES_ERROR;
        }

        Stepper stepper = stepperService.getStepperByScreen(7);

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_RESPONSE_PAYMENT, responsePayment);
        model.addAttribute(ATTRIBUTE_DEVELOPER_INSIGHTS_ENABLED, developerInsightsEnabled);

        if (developerInsightsEnabled) {

            QuoteReqRes quoteReqRes = storeService.handleCookieNCQ(request, response, StoreConstants.MosquesStore.STORE_ID);
            PaymentReqRes paymentReqRes = storeService.handleCookieNCP(request, response, StoreConstants.MosquesStore.STORE_ID);

            if (quoteReqRes != null) {
                String prettyJsonQuoteRequest = storeService.generatePrettyJson(quoteReqRes.getRequest());
                model.addAttribute(ATTRIBUTE_PRETTY_JSON_QUOTE_REQUEST, prettyJsonQuoteRequest);

                String prettyJsonQuoteResponse = storeService.generatePrettyJson(quoteReqRes.getResponse());
                model.addAttribute(ATTRIBUTE_PRETTY_JSON_QUOTE_RESPONSE, prettyJsonQuoteResponse);
            }

            if (paymentReqRes != null) {
                String prettyJsonPaymentRequest = storeService.generatePrettyJson(paymentReqRes.getRequest());
                model.addAttribute(ATTRIBUTE_PRETTY_JSON_PAYMENT_REQUEST, prettyJsonPaymentRequest);

                String prettyJsonPaymentResponse = storeService.generatePrettyJson(paymentReqRes.getResponse());
                model.addAttribute(ATTRIBUTE_PRETTY_JSON_PAYMENT_RESPONSE, prettyJsonPaymentResponse);
            }
        }

        storeService.deleteAllCookiesByStoreId(response, StoreConstants.MosquesStore.STORE_ID);

        return VIEW_MOSQUES_STORE_SCREEN_7;
    }

    @GetMapping("/error")
    public String showMosquesStoreScreen8(Model model, HttpServletRequest request, HttpServletResponse response) {

        boolean allCookiesAvailable = storeService.isAllCookiesAvailable(request, StoreConstants.MosquesStore.STORE_ID);
        if (!allCookiesAvailable) {
            return REDIRECT_MOSQUES_ORDER;
        }

        Stepper stepper = stepperService.getStepperByScreen(7);

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_DEVELOPER_INSIGHTS_ENABLED, developerInsightsEnabled);

        storeService.deleteAllCookiesByStoreId(response, StoreConstants.MosquesStore.STORE_ID);

        return VIEW_MOSQUES_STORE_SCREEN_8;
    }


    @GetMapping("/calculating-shipping-and-taxes")
    public String showMosquesStoreScreen3(Model model) {

        Stepper stepper = stepperService.getStepperByScreen(3);

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_CURRENT_YEAR, LocalDateTime.now().getYear());

        return VIEW_MOSQUES_STORE_SCREEN_3;
    }

}
