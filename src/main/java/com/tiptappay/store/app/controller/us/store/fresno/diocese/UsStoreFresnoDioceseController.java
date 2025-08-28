package com.tiptappay.store.app.controller.us.store.fresno.diocese;

import com.tiptappay.store.app.constant.StoreConstants;
import com.tiptappay.store.app.model.*;
import com.tiptappay.store.app.dto.us.store.UsStoreShoppingBag;
import com.tiptappay.store.app.service.StepperService;
import com.tiptappay.store.app.service.StoreService;
import com.tiptappay.store.app.service.us.store.UsStoreService;
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
@RequestMapping("${stores.us-catholic.fresno-diocese.endpoint}")
@RequiredArgsConstructor
public class UsStoreFresnoDioceseController {

    private final UsStoreService usStoreService;
    private final StoreService storeService;
    private final StepperService stepperService;

    @Value("${app.developer.insights.enabled}")
    private boolean developerInsightsEnabled;

    @GetMapping("order")
    public String showUsStoreScreen1(HttpServletRequest request, HttpServletResponse response, Model model) {

        UsStoreShoppingBag usStoreShoppingBag =
                (UsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.USStore.STORE_ID);
        if (usStoreShoppingBag == null) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ORDER;
        }

        Stepper stepper = stepperService.getStepperByScreen(1);
        List<Product> products = usStoreService.getProductList();

        // Returning attribute from shopping bag
        String emptyBag = (String) model.getAttribute(ATTRIBUTE_EMPTY_BAG);
        if (emptyBag != null) {
            model.addAttribute(ATTRIBUTE_EMPTY_BAG, emptyBag);
        }

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, usStoreShoppingBag);
        model.addAttribute(ATTRIBUTE_PRODUCTS, products);

        return VIEW_US_STORE_FRESNO_SCREEN_1;
    }

    @PostMapping("/order")
    public String showUsStoreScreen1Post(HttpServletRequest request, HttpServletResponse response) {

        UsStoreShoppingBag usStoreShoppingBag =
                (UsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.USStore.STORE_ID);
        if (usStoreShoppingBag == null || usStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ORDER;
        }

        return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_CONTACT_AND_SHIPPING;
    }

    @GetMapping("/contact-and-shipping")
    public String showUsStoreScreen2(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Model model) {

        UsStoreShoppingBag usStoreShoppingBag =
                (UsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.USStore.STORE_ID);
        if (usStoreShoppingBag == null || usStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ORDER;
        }

        ContactAndShipping contactAndShipping = storeService.handleCookieCANDS(request, response, StoreConstants.USStore.STORE_ID);
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
        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, usStoreShoppingBag);
        model.addAttribute(ATTRIBUTE_CONTACT_AND_SHIPPING, contactAndShipping);

        return VIEW_US_STORE_FRESNO_SCREEN_2;
    }

    @PostMapping("/contact-and-shipping")
    public String showUsStoreScreen2Post(ContactAndShipping contactAndShipping,
                                         HttpServletRequest request,
                                         HttpServletResponse response,
                                         RedirectAttributes redirectAttributes) {

        UsStoreShoppingBag usStoreShoppingBag =
                (UsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.USStore.STORE_ID);
        if (usStoreShoppingBag == null || usStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ORDER;
        }

        if (contactAndShipping == null) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_CONTACT_AND_SHIPPING;
        }

        storeService.setCookieCANDS(response, contactAndShipping, StoreConstants.USStore.STORE_ID);

        ResponseQuoteCookie responseQuoteCookie =
                storeService.handleCookieTSQ(request, response, usStoreShoppingBag, contactAndShipping, StoreConstants.USStore.STORE_ID);
        if (responseQuoteCookie == null) {
            log.error("UsStoreController.showUsStoreScreen2Post > {}", "Tax and Shipping Quote is null");
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, "Unexpected Error Occurred : SS");
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_CONTACT_AND_SHIPPING;
        }

        if (!responseQuoteCookie.getQuote().isSuccess()) {
            log.error("UsStoreController.showUsStoreScreen2Post > {}", Arrays.toString(responseQuoteCookie.getQuote().getErrors()));
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, responseQuoteCookie.getQuote().getErrors());
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_CONTACT_AND_SHIPPING;
        }

        storeService.setCookieTSQ(response, responseQuoteCookie, StoreConstants.USStore.STORE_ID);

        return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ORDER_REVIEW;
    }

    @GetMapping("/order-review")
    public String showUsStoreScreen4(HttpServletRequest request,
                                     HttpServletResponse response,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {

        UsStoreShoppingBag usStoreShoppingBag =
                (UsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.USStore.STORE_ID);
        if (usStoreShoppingBag == null || usStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ORDER;
        }

        ContactAndShipping contactAndShipping =
                storeService.handleCookieCANDS(request, response, StoreConstants.USStore.STORE_ID);
        if (contactAndShipping == null) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_CONTACT_AND_SHIPPING;
        }

        ResponseQuoteCookie responseQuoteCookie =
                storeService.handleCookieTSQ(request, response, usStoreShoppingBag, contactAndShipping, StoreConstants.USStore.STORE_ID);
        if (responseQuoteCookie == null) {
            log.error("UsStoreController.showUsStoreScreen4 > {}", "Tax and Shipping Quote is null");
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, "Unexpected Error Occurred : SS");
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_CONTACT_AND_SHIPPING;
        }

        if (!responseQuoteCookie.getQuote().isSuccess()) {
            log.error("UsStoreController.showUsStoreScreen4 > {}", Arrays.toString(responseQuoteCookie.getQuote().getErrors()));
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, responseQuoteCookie.getQuote());
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_CONTACT_AND_SHIPPING;
        }

        storeService.setCookieTSQ(response, responseQuoteCookie, StoreConstants.USStore.STORE_ID);

        // Set Quote Values
        usStoreShoppingBag.setShippingAmountInCents((int) (responseQuoteCookie.getQuote().getShippingAmount() * 100));
        usStoreShoppingBag.setTaxAmountInCents((int) (responseQuoteCookie.getQuote().getTaxAmount() * 100));

        Stepper stepper = stepperService.getStepperByScreen(4);

        List<Product> products = usStoreService.getProductList(
                usStoreShoppingBag.getMiniClipKitQty(),
                usStoreShoppingBag.getDevice5DollarQty(),
                usStoreShoppingBag.getDevice10DollarQty(),
                usStoreShoppingBag.getDevice20DollarQty()
        );

        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, usStoreShoppingBag);
        model.addAttribute(ATTRIBUTE_PRODUCTS, products);
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_FORMAT_CENTS, new FormatCents());

        return VIEW_US_STORE_FRESNO_SCREEN_4;
    }

    @PostMapping("/order-review")
    public String showUsStoreScreen4Post(HttpServletRequest request,
                                         HttpServletResponse response,
                                         RedirectAttributes redirectAttributes) {

        UsStoreShoppingBag usStoreShoppingBag =
                (UsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.USStore.STORE_ID);
        if (usStoreShoppingBag == null || usStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ORDER;
        }

        ContactAndShipping contactAndShipping =
                storeService.handleCookieCANDS(request, response, StoreConstants.USStore.STORE_ID);
        if (contactAndShipping == null) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_CONTACT_AND_SHIPPING;
        }

        ResponseQuoteCookie responseQuoteCookie =
                storeService.handleCookieTSQ(request, response, usStoreShoppingBag, contactAndShipping, StoreConstants.USStore.STORE_ID);
        if (responseQuoteCookie == null) {
            log.error("UsStoreController.showUsStoreScreen4Post > {}", "Tax and Shipping Quote is null");
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, "Unexpected Error Occurred : SS");
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_CONTACT_AND_SHIPPING;
        }

        if (!responseQuoteCookie.getQuote().isSuccess()) {
            log.error("UsStoreController.showUsStoreScreen4Post > {}", Arrays.toString(responseQuoteCookie.getQuote().getErrors()));
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, responseQuoteCookie.getQuote());
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_CONTACT_AND_SHIPPING;
        }

        storeService.setCookieTSQ(response, responseQuoteCookie, StoreConstants.USStore.STORE_ID);

        return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_PAYMENT;
    }

    @GetMapping("/payment")
    public String showUsStoreScreen5(HttpServletRequest request,
                                     HttpServletResponse response,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {

        UsStoreShoppingBag usStoreShoppingBag =
                (UsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.USStore.STORE_ID);
        if (usStoreShoppingBag == null || usStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ORDER;
        }

        ContactAndShipping contactAndShipping =
                storeService.handleCookieCANDS(request, response, StoreConstants.USStore.STORE_ID);
        if (contactAndShipping == null) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_CONTACT_AND_SHIPPING;
        }

        ResponseQuoteCookie responseQuoteCookie =
                storeService.handleCookieTSQ(request, response, usStoreShoppingBag, contactAndShipping, StoreConstants.USStore.STORE_ID);
        if (responseQuoteCookie == null) {
            log.error("UsStoreController.showUsStoreScreen5 > {}", "Tax and Shipping Quote is null");
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, "Unexpected Error Occurred : SS");
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_CONTACT_AND_SHIPPING;
        }

        if (!responseQuoteCookie.getQuote().isSuccess()) {
            log.error("UsStoreController.showUsStoreScreen5 > {}", Arrays.toString(responseQuoteCookie.getQuote().getErrors()));
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, responseQuoteCookie.getQuote());
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_CONTACT_AND_SHIPPING;
        }

        storeService.setCookieTSQ(response, responseQuoteCookie, StoreConstants.USStore.STORE_ID);

        // Set Quote Values
        usStoreShoppingBag.setShippingAmountInCents((int) (responseQuoteCookie.getQuote().getShippingAmount() * 100));
        usStoreShoppingBag.setTaxAmountInCents((int) (responseQuoteCookie.getQuote().getTaxAmount() * 100));

        Stepper stepper = stepperService.getStepperByScreen(5);

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, usStoreShoppingBag);
        model.addAttribute(ATTRIBUTE_CONTACT_AND_SHIPPING, contactAndShipping);
        model.addAttribute(ATTRIBUTE_PAYMENT, new Payment());
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);

        return VIEW_US_STORE_FRESNO_SCREEN_5;
    }

    @PostMapping("/payment")
    public String showUsStoreScreen5Post(Payment payment,
                                         HttpServletRequest request,
                                         HttpServletResponse response,
                                         RedirectAttributes redirectAttributes) {


        UsStoreShoppingBag usStoreShoppingBag =
                (UsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.USStore.STORE_ID);
        if (usStoreShoppingBag == null || usStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ORDER;
        }

        ContactAndShipping contactAndShipping =
                storeService.handleCookieCANDS(request, response, StoreConstants.USStore.STORE_ID);
        if (contactAndShipping == null) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_CONTACT_AND_SHIPPING;
        }

        ResponseQuoteCookie responseQuoteCookie =
                storeService.handleCookieTSQ(request, response, usStoreShoppingBag, contactAndShipping, StoreConstants.USStore.STORE_ID);
        if (responseQuoteCookie == null) {
            log.error("UsStoreController.showUsStoreScreen5 > {}", "Tax and Shipping Quote is null");
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, "Unexpected Error Occurred : SS");
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_CONTACT_AND_SHIPPING;
        }

        if (!responseQuoteCookie.getQuote().isSuccess()) {
            log.error("UsStoreController.showUsStoreScreen5 > {}", Arrays.toString(responseQuoteCookie.getQuote().getErrors()));
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, responseQuoteCookie.getQuote());
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_CONTACT_AND_SHIPPING;
        }

        storeService.setCookieTSQ(response, responseQuoteCookie, StoreConstants.USStore.STORE_ID);

        // Set Quote Values
        usStoreShoppingBag.setShippingAmountInCents((int) (responseQuoteCookie.getQuote().getShippingAmount() * 100));
        usStoreShoppingBag.setTaxAmountInCents((int) (responseQuoteCookie.getQuote().getTaxAmount() * 100));

        // Send Request to NetSuite
        PaymentReqRes paymentReqRes =
                storeService.sendRequestToNetSuite(response, usStoreShoppingBag, contactAndShipping, payment, StoreConstants.USStore.STORE_ID);

        redirectAttributes.addFlashAttribute(ATTRIBUTE_RESPONSE_PAYMENT, paymentReqRes.getResponse());

        if (paymentReqRes.getResponse() != null && paymentReqRes.getResponse().isCcApproved()) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_THANKS;
        }

        return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_FAILED;
    }

    @GetMapping("/thanks")
    public String showUsStoreScreen6(Model model, HttpServletRequest request, HttpServletResponse response) {

        boolean allCookiesAvailable =
                storeService.isAllCookiesAvailable(request, StoreConstants.USStore.STORE_ID);
        if (!allCookiesAvailable) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ORDER;
        }

        ResponsePayment responsePayment = (ResponsePayment) model.getAttribute(ATTRIBUTE_RESPONSE_PAYMENT);

        if (responsePayment == null) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ERROR;
        }

        Stepper stepper = stepperService.getStepperByScreen(6);

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_RESPONSE_PAYMENT, responsePayment);
        model.addAttribute(ATTRIBUTE_DEVELOPER_INSIGHTS_ENABLED, developerInsightsEnabled);

        if (developerInsightsEnabled) {

            QuoteReqRes quoteReqRes = storeService.handleCookieNCQ(request, response, StoreConstants.USStore.STORE_ID);
            PaymentReqRes paymentReqRes = storeService.handleCookieNCP(request, response, StoreConstants.USStore.STORE_ID);

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

        storeService.deleteAllCookiesByStoreId(response, StoreConstants.USStore.STORE_ID);

        return VIEW_US_STORE_FRESNO_SCREEN_6;
    }

    @GetMapping("/failed")
    public String showUsStoreScreen7(Model model, HttpServletRequest request, HttpServletResponse response) {

        boolean allCookiesAvailable = storeService.isAllCookiesAvailable(request, StoreConstants.USStore.STORE_ID);
        if (!allCookiesAvailable) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ORDER;
        }

        ResponsePayment responsePayment = (ResponsePayment) model.getAttribute(ATTRIBUTE_RESPONSE_PAYMENT);

        if (responsePayment == null) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ERROR;
        }

        Stepper stepper = stepperService.getStepperByScreen(7);

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_RESPONSE_PAYMENT, responsePayment);
        model.addAttribute(ATTRIBUTE_DEVELOPER_INSIGHTS_ENABLED, developerInsightsEnabled);

        if (developerInsightsEnabled) {

            QuoteReqRes quoteReqRes = storeService.handleCookieNCQ(request, response, StoreConstants.USStore.STORE_ID);
            PaymentReqRes paymentReqRes = storeService.handleCookieNCP(request, response, StoreConstants.USStore.STORE_ID);

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

        storeService.deleteAllCookiesByStoreId(response, StoreConstants.USStore.STORE_ID);

        return VIEW_US_STORE_FRESNO_SCREEN_7;
    }

    @GetMapping("/error")
    public String showUsStoreScreen8(Model model, HttpServletRequest request, HttpServletResponse response) {

        boolean allCookiesAvailable = storeService.isAllCookiesAvailable(request, StoreConstants.USStore.STORE_ID);
        if (!allCookiesAvailable) {
            return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ORDER;
        }

        Stepper stepper = stepperService.getStepperByScreen(7);

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_DEVELOPER_INSIGHTS_ENABLED, developerInsightsEnabled);

        storeService.deleteAllCookiesByStoreId(response, StoreConstants.USStore.STORE_ID);

        return VIEW_US_STORE_FRESNO_SCREEN_8;
    }


    @GetMapping("/calculating-shipping-and-taxes")
    public String showUsStoreScreen3(Model model) {

        Stepper stepper = stepperService.getStepperByScreen(3);

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_CURRENT_YEAR, LocalDateTime.now().getYear());

        return VIEW_US_STORE_FRESNO_SCREEN_3;
    }
}
