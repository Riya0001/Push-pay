package com.tiptappay.store.app.controller.saus.store;

import com.tiptappay.store.app.constant.SaUsOrganizationMap;
import com.tiptappay.store.app.constant.StoreConstants;
import com.tiptappay.store.app.model.*;
import com.tiptappay.store.app.dto.saus.store.SaUsStoreShoppingBag;
import com.tiptappay.store.app.service.StepperService;
import com.tiptappay.store.app.service.StoreService;
import com.tiptappay.store.app.service.saus.SaUsStoreService;
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
import java.util.Set;

import static com.tiptappay.store.app.constant.AppConstants.Attributes.*;
import static com.tiptappay.store.app.constant.AppConstants.RedirectTemplates.*;
import static com.tiptappay.store.app.constant.AppConstants.ViewTemplates.*;

@Slf4j
@Controller
@RequestMapping("${stores.salvation-army-us.endpoint}")
@RequiredArgsConstructor
public class SaUsStoreController {

    private final SaUsStoreService saUsStoreService;
    private final StoreService storeService;
    private final StepperService stepperService;

    @Value("${app.developer.insights.enabled}")
    private boolean developerInsightsEnabled;

    @GetMapping("/order")
    public String showSaUsStoreScreen1(HttpServletRequest request, HttpServletResponse response, Model model) {

        SaUsStoreShoppingBag saUsStoreShoppingBag =
                (SaUsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.SaUsStore.STORE_ID);
        if (saUsStoreShoppingBag == null) {
            return REDIRECT_SAUS_ORDER;
        }

        Stepper stepper = stepperService.getStepperByScreen(1);
        List<Product> products = saUsStoreService.getProductList();

        // Returning attribute from shopping bag
        String emptyBag = (String) model.getAttribute(ATTRIBUTE_EMPTY_BAG);
        if (emptyBag != null) {
            model.addAttribute(ATTRIBUTE_EMPTY_BAG, emptyBag);
        }

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, saUsStoreShoppingBag);
        model.addAttribute(ATTRIBUTE_PRODUCTS, products);

        return VIEW_SAUS_STORE_SCREEN_1;
    }

    @PostMapping("/order")
    public String showSaUsStoreScreen1Post(HttpServletRequest request, HttpServletResponse response) {

        SaUsStoreShoppingBag saUsStoreShoppingBag =
                (SaUsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.SaUsStore.STORE_ID);
        if (saUsStoreShoppingBag == null || saUsStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_SAUS_ORDER;
        }

        return REDIRECT_SAUS_CONTACT_AND_SHIPPING;
    }

    @GetMapping("/contact-and-shipping")
    public String showSaUsStoreScreen2(HttpServletRequest request,
                                       HttpServletResponse response,
                                       Model model) {

        SaUsStoreShoppingBag saUsStoreShoppingBag =
                (SaUsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.SaUsStore.STORE_ID);
        if (saUsStoreShoppingBag == null || saUsStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_SAUS_ORDER;
        }

        ContactAndShipping contactAndShipping = storeService.handleCookieCANDS(request, response, StoreConstants.SaUsStore.STORE_ID);
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
        Set<String> territoryNamesSB = SaUsOrganizationMap.getAllTerritoryNames();

        // Divisions
        Set<String> allDivisionsCentralNamesSB = SaUsOrganizationMap.getAllDivisionsCentralNames();
        Set<String> allDivisionsEasternNamesSB = SaUsOrganizationMap.getAllDivisionsEasternNames();
        Set<String> allDivisionsSouthernNamesSB = SaUsOrganizationMap.getAllDivisionsSouthernNames();
        Set<String> allDivisionsWesternNamesSB = SaUsOrganizationMap.getAllDivisionsWesternNames();

        // Corps
        Set<String> allCorpsCentralNamesSB = SaUsOrganizationMap.getAllCorpsCentralNames();
        Set<String> allCorpsEasternNamesSB = SaUsOrganizationMap.getAllCorpsEasternNames();
        Set<String> allCorpsSouthernNamesSB = SaUsOrganizationMap.getAllCorpsSouthernNames();
        Set<String> allCorpsWesternNamesSB = SaUsOrganizationMap.getAllCorpsWesternNames();

        model.addAttribute("territories", territoryNamesSB);

        // Divisions
        model.addAttribute("centralDivisions", allDivisionsCentralNamesSB);
        model.addAttribute("easternDivisions", allDivisionsEasternNamesSB);
        model.addAttribute("southernDivisions", allDivisionsSouthernNamesSB);
        model.addAttribute("westernDivisions", allDivisionsWesternNamesSB);

        // Corps
        model.addAttribute("centralCorps", allCorpsCentralNamesSB);
        model.addAttribute("easternCorps", allCorpsEasternNamesSB);
        model.addAttribute("southernCorps", allCorpsSouthernNamesSB);
        model.addAttribute("westernCorps", allCorpsWesternNamesSB);
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, saUsStoreShoppingBag);
        model.addAttribute(ATTRIBUTE_CONTACT_AND_SHIPPING, contactAndShipping);

        return VIEW_SAUS_STORE_SCREEN_2;
    }

    @PostMapping("/contact-and-shipping")
    public String showSaUsStoreScreen2Post(ContactAndShipping contactAndShipping,
                                           HttpServletRequest request,
                                           HttpServletResponse response,
                                           RedirectAttributes redirectAttributes) {

        SaUsStoreShoppingBag saUsStoreShoppingBag =
                (SaUsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.SaUsStore.STORE_ID);
        if (saUsStoreShoppingBag == null || saUsStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_SAUS_ORDER;
        }

        if (contactAndShipping == null) {
            return REDIRECT_SAUS_CONTACT_AND_SHIPPING;
        }

        storeService.setCookieCANDS(response, contactAndShipping, StoreConstants.SaUsStore.STORE_ID);

        ResponseQuoteCookie responseQuoteCookie =
                storeService.handleCookieTSQ(request, response, saUsStoreShoppingBag, contactAndShipping, StoreConstants.SaUsStore.STORE_ID);
        if (responseQuoteCookie == null) {
            log.error("SaUsStoreController.showSaUsStoreScreen2Post > {}", "Tax and Shipping Quote is null");
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, "Unexpected Error Occurred : SS");
            return REDIRECT_SAUS_CONTACT_AND_SHIPPING;
        }

        if (!responseQuoteCookie.getQuote().isSuccess()) {
            log.error("SaUsStoreController.showSaUsStoreScreen2Post > {}", Arrays.toString(responseQuoteCookie.getQuote().getErrors()));
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, responseQuoteCookie.getQuote().getErrors());
            return REDIRECT_SAUS_CONTACT_AND_SHIPPING;
        }

        storeService.setCookieTSQ(response, responseQuoteCookie, StoreConstants.SaUsStore.STORE_ID);
        return REDIRECT_SAUS_ORDER_REVIEW;
    }

    @GetMapping("/order-review")
    public String showSaUsStoreScreen4(HttpServletRequest request,
                                       HttpServletResponse response,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {

        SaUsStoreShoppingBag saUsStoreShoppingBag =
                (SaUsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.SaUsStore.STORE_ID);
        if (saUsStoreShoppingBag == null || saUsStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_SAUS_ORDER;
        }

        ContactAndShipping contactAndShipping =
                storeService.handleCookieCANDS(request, response, StoreConstants.SaUsStore.STORE_ID);
        if (contactAndShipping == null) {
            return REDIRECT_SAUS_CONTACT_AND_SHIPPING;
        }

        ResponseQuoteCookie responseQuoteCookie =
                storeService.handleCookieTSQ(request, response, saUsStoreShoppingBag, contactAndShipping, StoreConstants.SaUsStore.STORE_ID);
        if (responseQuoteCookie == null) {
            log.error("SaUsStoreController.showSaUsStoreScreen4 > {}", "Tax and Shipping Quote is null");
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, "Unexpected Error Occurred : SS");
            return REDIRECT_SAUS_CONTACT_AND_SHIPPING;
        }

        if (!responseQuoteCookie.getQuote().isSuccess()) {
            log.error("SaUsStoreController.showSaUsStoreScreen4 > {}", Arrays.toString(responseQuoteCookie.getQuote().getErrors()));
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, responseQuoteCookie.getQuote());
            return REDIRECT_SAUS_CONTACT_AND_SHIPPING;
        }

        storeService.setCookieTSQ(response, responseQuoteCookie, StoreConstants.SaUsStore.STORE_ID);

        // Set Quote Values
        saUsStoreShoppingBag.setShippingAmountInCents((int) (responseQuoteCookie.getQuote().getShippingAmount() * 100));
        saUsStoreShoppingBag.setTaxAmountInCents((int) (responseQuoteCookie.getQuote().getTaxAmount() * 100));

        Stepper stepper = stepperService.getStepperByScreen(4);

        List<Product> products = saUsStoreService.getProductList(
                saUsStoreShoppingBag.getKettleDisplayQty(),
                saUsStoreShoppingBag.getDevice5DollarQty(),
                saUsStoreShoppingBag.getDevice10DollarQty(),
                saUsStoreShoppingBag.getDevice20DollarQty(),
                saUsStoreShoppingBag.getBatteryForKettleDisplayQty()
        );

        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, saUsStoreShoppingBag);
        model.addAttribute(ATTRIBUTE_PRODUCTS, products);
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_FORMAT_CENTS, new FormatCents());

        return VIEW_SAUS_STORE_SCREEN_4;
    }

    @PostMapping("/order-review")
    public String showSaUsStoreScreen4Post(HttpServletRequest request,
                                           HttpServletResponse response,
                                           RedirectAttributes redirectAttributes) {

        SaUsStoreShoppingBag saUsStoreShoppingBag =
                (SaUsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.SaUsStore.STORE_ID);
        if (saUsStoreShoppingBag == null || saUsStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_SAUS_ORDER;
        }

        ContactAndShipping contactAndShipping =
                storeService.handleCookieCANDS(request, response, StoreConstants.SaUsStore.STORE_ID);
        if (contactAndShipping == null) {
            return REDIRECT_SAUS_CONTACT_AND_SHIPPING;
        }

        ResponseQuoteCookie responseQuoteCookie =
                storeService.handleCookieTSQ(request, response, saUsStoreShoppingBag, contactAndShipping, StoreConstants.SaUsStore.STORE_ID);
        if (responseQuoteCookie == null || !responseQuoteCookie.getQuote().isSuccess()) {
            log.error("SaUsStoreController.showSaUsStoreScreen4Post > Quote Error: {}", responseQuoteCookie);
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, responseQuoteCookie != null ? responseQuoteCookie.getQuote().getErrors() : "Unexpected Error");
            return REDIRECT_SAUS_CONTACT_AND_SHIPPING;
        }

        // Store updated quote values in the shopping bag
        saUsStoreShoppingBag.setShippingAmountInCents((int) (responseQuoteCookie.getQuote().getShippingAmount() * 100));
        saUsStoreShoppingBag.setTaxAmountInCents((int) (responseQuoteCookie.getQuote().getTaxAmount() * 100));
        storeService.setCookieSB(response, saUsStoreShoppingBag, StoreConstants.SaUsStore.STORE_ID);

        // Make sure the latest C&S is also persisted
        storeService.setCookieCANDS(response, contactAndShipping, StoreConstants.SaUsStore.STORE_ID);

        return REDIRECT_SAUS_PAYMENT;
    }


    @GetMapping("/payment")
    public String showSaUsStoreScreen5(HttpServletRequest request,
                                       HttpServletResponse response,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {

        SaUsStoreShoppingBag saUsStoreShoppingBag =
                (SaUsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.SaUsStore.STORE_ID);
        if (saUsStoreShoppingBag == null || saUsStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_SAUS_ORDER;
        }

        ContactAndShipping contactAndShipping =
                storeService.handleCookieCANDS(request, response, StoreConstants.SaUsStore.STORE_ID);
        if (contactAndShipping == null) {
            return REDIRECT_SAUS_CONTACT_AND_SHIPPING;
        }

        ResponseQuoteCookie responseQuoteCookie =
                storeService.handleCookieTSQ(request, response, saUsStoreShoppingBag, contactAndShipping, StoreConstants.SaUsStore.STORE_ID);
        if (responseQuoteCookie == null) {
            log.error("SaUsStoreController.showSaUsStoreScreen5 > {}", "Tax and Shipping Quote is null");
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, "Unexpected Error Occurred : SS");
            return REDIRECT_SAUS_CONTACT_AND_SHIPPING;
        }

        if (!responseQuoteCookie.getQuote().isSuccess()) {
            log.error("SaUsStoreController.showSaUsStoreScreen5 > {}", Arrays.toString(responseQuoteCookie.getQuote().getErrors()));
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, responseQuoteCookie.getQuote());
            return REDIRECT_SAUS_CONTACT_AND_SHIPPING;
        }

        storeService.setCookieTSQ(response, responseQuoteCookie, StoreConstants.SaUsStore.STORE_ID);

        // Set Quote Values
        saUsStoreShoppingBag.setShippingAmountInCents((int) (responseQuoteCookie.getQuote().getShippingAmount() * 100));
        saUsStoreShoppingBag.setTaxAmountInCents((int) (responseQuoteCookie.getQuote().getTaxAmount() * 100));

        Stepper stepper = stepperService.getStepperByScreen(5);

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, saUsStoreShoppingBag);
        model.addAttribute(ATTRIBUTE_CONTACT_AND_SHIPPING, contactAndShipping);
        model.addAttribute(ATTRIBUTE_PAYMENT, new Payment());
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);

        return VIEW_SAUS_STORE_SCREEN_5;
    }

    @PostMapping("/payment")
    public String showSaUsStoreScreen5Post(Payment payment,
                                           HttpServletRequest request,
                                           HttpServletResponse response,
                                           RedirectAttributes redirectAttributes) {

        SaUsStoreShoppingBag saUsStoreShoppingBag =
                (SaUsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.SaUsStore.STORE_ID);
        if (saUsStoreShoppingBag == null || saUsStoreShoppingBag.totalNumberOfProducts() <= 0) {
            return REDIRECT_SAUS_ORDER;
        }

        ContactAndShipping contactAndShipping =
                storeService.handleCookieCANDS(request, response, StoreConstants.SaUsStore.STORE_ID);
        if (contactAndShipping == null) {
            return REDIRECT_SAUS_CONTACT_AND_SHIPPING;
        }

        ResponseQuoteCookie responseQuoteCookie =
                storeService.handleCookieTSQ(request, response, saUsStoreShoppingBag, contactAndShipping, StoreConstants.SaUsStore.STORE_ID);
        if (responseQuoteCookie == null) {
            log.error("SaUsStoreController.showSaUsStoreScreen5 > Tax and Shipping Quote is null");
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, "Unexpected Error Occurred : SS");
            return REDIRECT_SAUS_CONTACT_AND_SHIPPING;
        }

        if (!responseQuoteCookie.getQuote().isSuccess()) {
            log.error("SaUsStoreController.showSaUsStoreScreen5 > {}", Arrays.toString(responseQuoteCookie.getQuote().getErrors()));
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, responseQuoteCookie.getQuote());
            return REDIRECT_SAUS_CONTACT_AND_SHIPPING;
        }

        // Save quote in cookie
        storeService.setCookieTSQ(response, responseQuoteCookie, StoreConstants.SaUsStore.STORE_ID);

        // Update bag totals
        saUsStoreShoppingBag.setShippingAmountInCents((int) (responseQuoteCookie.getQuote().getShippingAmount() * 100));
        saUsStoreShoppingBag.setTaxAmountInCents((int) (responseQuoteCookie.getQuote().getTaxAmount() * 100));

        // Send payload to NetSuite
        PaymentReqRes paymentReqRes =
                storeService.sendRequestToNetSuite(response, saUsStoreShoppingBag, contactAndShipping, payment, StoreConstants.SaUsStore.STORE_ID);

        redirectAttributes.addFlashAttribute(ATTRIBUTE_RESPONSE_PAYMENT, paymentReqRes.getResponse());
        if (paymentReqRes.getResponse() != null){
        saUsStoreService.AthenaTerritoryCampaign(contactAndShipping);
        return REDIRECT_SAUS_THANKS;}
        else{
            return REDIRECT_SAUS_ERROR;
        }
    }


    @GetMapping("/thanks")
    public String showSaUsStoreScreen6(Model model, HttpServletRequest request, HttpServletResponse response) {

        boolean allCookiesAvailable =
                storeService.isAllCookiesAvailable(request, StoreConstants.SaUsStore.STORE_ID);
        if (!allCookiesAvailable) {
            return REDIRECT_SAUS_ORDER;
        }

        ResponsePayment responsePayment = (ResponsePayment) model.getAttribute(ATTRIBUTE_RESPONSE_PAYMENT);

        if (responsePayment == null) {
            return REDIRECT_SAUS_ERROR;
        }

        Stepper stepper = stepperService.getStepperByScreen(6);

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_RESPONSE_PAYMENT, responsePayment);
        model.addAttribute(ATTRIBUTE_DEVELOPER_INSIGHTS_ENABLED, developerInsightsEnabled);

        if (developerInsightsEnabled) {

            QuoteReqRes quoteReqRes = storeService.handleCookieNCQ(request, response, StoreConstants.SaUsStore.STORE_ID);
            PaymentReqRes paymentReqRes = storeService.handleCookieNCP(request, response, StoreConstants.SaUsStore.STORE_ID);

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
        //athena onboarding should be done here
        storeService.deleteAllCookiesByStoreId(response, StoreConstants.SaUsStore.STORE_ID);

        return VIEW_SAUS_STORE_SCREEN_6;
    }

    @GetMapping("/failed")
    public String showSaUsStoreScreen7(Model model, HttpServletRequest request, HttpServletResponse response) {

        boolean allCookiesAvailable = storeService.isAllCookiesAvailable(request, StoreConstants.SaUsStore.STORE_ID);
        if (!allCookiesAvailable) {
            return REDIRECT_SAUS_ORDER;
        }

        ResponsePayment responsePayment = (ResponsePayment) model.getAttribute(ATTRIBUTE_RESPONSE_PAYMENT);

        if (responsePayment == null) {
            return REDIRECT_SAUS_ERROR;
        }

        Stepper stepper = stepperService.getStepperByScreen(7);

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_RESPONSE_PAYMENT, responsePayment);
        model.addAttribute(ATTRIBUTE_DEVELOPER_INSIGHTS_ENABLED, developerInsightsEnabled);

        if (developerInsightsEnabled) {

            QuoteReqRes quoteReqRes = storeService.handleCookieNCQ(request, response, StoreConstants.SaUsStore.STORE_ID);
            PaymentReqRes paymentReqRes = storeService.handleCookieNCP(request, response, StoreConstants.SaUsStore.STORE_ID);

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

        storeService.deleteAllCookiesByStoreId(response, StoreConstants.SaUsStore.STORE_ID);

        return VIEW_SAUS_STORE_SCREEN_7;
    }

    @GetMapping("/error")
    public String showSaUsStoreScreen8(Model model, HttpServletRequest request, HttpServletResponse response) {

        boolean allCookiesAvailable = storeService.isAllCookiesAvailable(request, StoreConstants.SaUsStore.STORE_ID);
        if (!allCookiesAvailable) {
            return REDIRECT_SAUS_ORDER;
        }

        Stepper stepper = stepperService.getStepperByScreen(7);

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_DEVELOPER_INSIGHTS_ENABLED, developerInsightsEnabled);

        storeService.deleteAllCookiesByStoreId(response, StoreConstants.SaUsStore.STORE_ID);

        return VIEW_SAUS_STORE_SCREEN_8;
    }


    @GetMapping("/calculating-shipping-and-taxes")
    public String showSaUsStoreScreen3(Model model) {

        Stepper stepper = stepperService.getStepperByScreen(3);

        // Set Attributes
        model.addAttribute(ATTRIBUTE_STEPPER, stepper);
        model.addAttribute(ATTRIBUTE_CURRENT_YEAR, LocalDateTime.now().getYear());

        return VIEW_SAUS_STORE_SCREEN_3;
    }
}
