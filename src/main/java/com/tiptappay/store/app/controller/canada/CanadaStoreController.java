package com.tiptappay.store.app.controller.canada;

import com.tiptappay.store.app.constant.AppConstants;
import com.tiptappay.store.app.constant.StoreConstants;
import com.tiptappay.store.app.dto.canada.CanadaStoreShoppingBag;
import com.tiptappay.store.app.model.*;
import com.tiptappay.store.app.model.CustomHttpResponse;
import com.tiptappay.store.app.model.membership.MembershipRequest;
import com.tiptappay.store.app.service.*;
import com.tiptappay.store.app.service.canada.CanadaStoreService;
import com.tiptappay.store.app.service.membership.MembershipService;
import com.tiptappay.store.app.util.FormatCents;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.tiptappay.store.app.constant.AppConstants.Attributes.*;
import static com.tiptappay.store.app.constant.AppConstants.CampaignConstants.CAMPAIGNS_ENDPOINT;
import static com.tiptappay.store.app.constant.AppConstants.RedirectTemplates.*;
import static com.tiptappay.store.app.constant.AppConstants.ViewTemplates.*;

@Slf4j
@Controller
@RequestMapping("/canada-store")
@RequiredArgsConstructor
public class CanadaStoreController {

    private final StoreService storeService;
    private final StepperService stepperService;
    private final CanadaStoreService canadaStoreService;
    private final CampaignService campaignService;
    private final GeoLocationService geoLocationService;

    @Value("${app.developer.insights.enabled}")
    private boolean developerInsightsEnabled;

    @GetMapping("/order")
    public String showOrderPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        // Handle shopping bag from cookie
        CanadaStoreShoppingBag shoppingBag = (CanadaStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.CanadaStore.STORE_ID);
        if (shoppingBag == null) {
            shoppingBag = new CanadaStoreShoppingBag();
            storeService.setCookieSB(response, shoppingBag, StoreConstants.CanadaStore.STORE_ID);
        }

        // Add store-related data to model
        model.addAttribute(ATTRIBUTE_STEPPER, stepperService.getStepperByScreen(1));
        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, shoppingBag);
        model.addAttribute(ATTRIBUTE_PRODUCTS, canadaStoreService.getProductList());

        String countryCode = geoLocationService.resolveCountryCode(request);
        String countryName = switch (countryCode) {
            case "CA" -> "Canada";
            case "US" -> "USA";
            case "IN" -> "India";
            default   -> "Unknown";
        };
        model.addAttribute("userCountry", countryName);
        return VIEW_CANADA_STORE_SCREEN_1;
    }

    @PostMapping("/order")
    public String handleOrderPost(HttpServletRequest request, HttpServletResponse response) {
        CanadaStoreShoppingBag shoppingBag = (CanadaStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.CanadaStore.STORE_ID);
        return (shoppingBag == null || shoppingBag.totalNumberOfProducts() <= 0)
                ? REDIRECT_CANADA_ORDER : REDIRECT_CANADA_CONTACT_AND_SHIPPING;
    }

    @GetMapping("/contact-and-shipping")
    public String showContactAndShippingPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        CanadaStoreShoppingBag shoppingBag = (CanadaStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.CanadaStore.STORE_ID);
        if (shoppingBag == null || shoppingBag.totalNumberOfProducts() <= 0) return REDIRECT_CANADA_ORDER;

        ContactAndShipping contactAndShipping = storeService.handleCookieCANDS(request, response, StoreConstants.CanadaStore.STORE_ID);
        if (contactAndShipping == null) contactAndShipping = new ContactAndShipping();

        model.addAttribute(ATTRIBUTE_STEPPER, stepperService.getStepperByScreen(2));
        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, shoppingBag);
        model.addAttribute(ATTRIBUTE_CONTACT_AND_SHIPPING, contactAndShipping);
        return VIEW_CANADA_STORE_SCREEN_2;
    }

    @PostMapping("/contact-and-shipping")
    public String handleContactAndShippingPost(@ModelAttribute ContactAndShipping contactAndShipping,
                                               HttpServletRequest request,
                                               HttpServletResponse response,
                                               HttpSession session,
                                               RedirectAttributes redirectAttributes) {
        CanadaStoreShoppingBag shoppingBag = (CanadaStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.CanadaStore.STORE_ID);
        if (shoppingBag == null || shoppingBag.totalNumberOfProducts() <= 0) return REDIRECT_CANADA_ORDER;

        storeService.setCookieCANDS(response, contactAndShipping, StoreConstants.CanadaStore.STORE_ID);
        ResponseQuoteCookie quoteCookie = storeService.handleCookieTSQ(request, response, shoppingBag, contactAndShipping, StoreConstants.CanadaStore.STORE_ID);
        if (quoteCookie == null || !quoteCookie.getQuote().isSuccess()) {
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED,
                    quoteCookie != null ? quoteCookie.getQuote().getErrors() : "Error calculating quote");
            return REDIRECT_CANADA_CONTACT_AND_SHIPPING;
        }

        session.setAttribute("tsq_s04", quoteCookie);
        return REDIRECT_CANADA_ORDER_REVIEW;
    }

    @GetMapping("/order-review")
    public String showOrderReviewPage(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        CanadaStoreShoppingBag shoppingBag = (CanadaStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.CanadaStore.STORE_ID);
        ContactAndShipping contactAndShipping = storeService.handleCookieCANDS(request, response, StoreConstants.CanadaStore.STORE_ID);
        ResponseQuoteCookie quoteCookie = (ResponseQuoteCookie) session.getAttribute("tsq_s04");

        if (shoppingBag == null || shoppingBag.totalNumberOfProducts() <= 0) return REDIRECT_CANADA_ORDER;
        if (contactAndShipping == null) return REDIRECT_CANADA_CONTACT_AND_SHIPPING;
        if (quoteCookie == null || !quoteCookie.getQuote().isSuccess()) {
            redirectAttributes.addFlashAttribute(ATTRIBUTE_QUOTE_FAILED, "Missing or invalid quote.");
            return REDIRECT_CANADA_CONTACT_AND_SHIPPING;
        }

        shoppingBag.setShippingAmountInCents((int) (quoteCookie.getQuote().getShippingAmount() * 100));
        shoppingBag.setTaxAmountInCents((int) (quoteCookie.getQuote().getTaxAmount() * 100));

        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, shoppingBag);
        model.addAttribute(ATTRIBUTE_PRODUCTS, canadaStoreService.getProductList());
        model.addAttribute(ATTRIBUTE_STEPPER, stepperService.getStepperByScreen(4));
        model.addAttribute(ATTRIBUTE_FORMAT_CENTS, new FormatCents());
        return VIEW_CANADA_STORE_SCREEN_3;
    }

    @PostMapping("/order-review")
    public String handleOrderReviewPost(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        CanadaStoreShoppingBag shoppingBag = (CanadaStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.CanadaStore.STORE_ID);
        ContactAndShipping contactAndShipping = storeService.handleCookieCANDS(request, response, StoreConstants.CanadaStore.STORE_ID);

        PaymentReqRes paymentReqRes = storeService.sendRequestToNetSuiteSACA(response, shoppingBag, contactAndShipping, null, StoreConstants.CanadaStore.STORE_ID);
        if (paymentReqRes == null || paymentReqRes.getResponse() == null) return REDIRECT_CANADA_FAILED;

        session.setAttribute("ncp_s04", paymentReqRes);
        return REDIRECT_CANADA_THANKS;
    }

    @GetMapping("/thanks")
    public String showThanksPage(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        PaymentReqRes paymentReqRes = (PaymentReqRes) session.getAttribute("ncp_s04");
        if (paymentReqRes == null || paymentReqRes.getResponse() == null) return REDIRECT_CANADA_ORDER;

        ResponsePayment responsePayment = paymentReqRes.getResponse();
        ContactAndShipping contactAndShipping = storeService.handleCookieCANDS(request, response, StoreConstants.CanadaStore.STORE_ID);
        String contactEmail = (contactAndShipping != null && contactAndShipping.getContactEmail() != null)
                ? contactAndShipping.getContactEmail().trim() : "n/a";

        model.addAttribute(ATTRIBUTE_RESPONSE_PAYMENT, responsePayment);
        model.addAttribute("contactEmail", contactEmail);
        model.addAttribute(ATTRIBUTE_DEVELOPER_INSIGHTS_ENABLED, developerInsightsEnabled);
        model.addAttribute(ATTRIBUTE_STEPPER, stepperService.getStepperByScreen(6));

        // Developer Insights Debug
        if (developerInsightsEnabled) {
            QuoteReqRes quoteReqRes = storeService.handleCookieNCQ(request, response, StoreConstants.CanadaStore.STORE_ID);
            if (quoteReqRes != null) {
                model.addAttribute(ATTRIBUTE_PRETTY_JSON_QUOTE_REQUEST, storeService.generatePrettyJson(quoteReqRes.getRequest()));
                model.addAttribute(ATTRIBUTE_PRETTY_JSON_QUOTE_RESPONSE, storeService.generatePrettyJson(quoteReqRes.getResponse()));
            }
            model.addAttribute(ATTRIBUTE_PRETTY_JSON_PAYMENT_REQUEST, storeService.generatePrettyJson(paymentReqRes.getRequest()));
            model.addAttribute(ATTRIBUTE_PRETTY_JSON_PAYMENT_RESPONSE, storeService.generatePrettyJson(responsePayment));
        }

        // Membership Onboarding Logic
        if (!"n/a".equals(contactEmail)) {
            try {
                String givenName = (contactAndShipping.getContactFirstName() != null)
                        ? contactAndShipping.getContactFirstName().trim()
                        : "Canada";

                String familyName = (contactAndShipping.getContactLastName() != null)
                        ? contactAndShipping.getContactLastName().trim()
                        : "User";

                String campaignName = "2025 " + contactAndShipping.getRespcNumber() + " " + contactAndShipping.getMinistryName();
                Integer campaignId = campaignService.getOrCreateCampaign(campaignName, "2025-11-01");

                boolean isCreated = campaignService.createMembership(
                        165,
                        campaignId,
                        contactEmail,
                        familyName,
                        givenName,
                        AppConstants.MembershipConstants.DEFAULT_PASSWORD,
                        (contactAndShipping.getContactPhone() != null)
                                ? contactAndShipping.getContactPhone().trim()
                                : AppConstants.MembershipConstants.DEFAULT_PHONE_FALLBACK,
                        AppConstants.MembershipConstants.DEFAULT_ROLE,
                        AppConstants.MembershipConstants.DEFAULT_STATUS,
                        CAMPAIGNS_ENDPOINT + campaignId
                );


                if (!isCreated) {
                    log.warn("🚨 Membership creation failed for email: {}", contactEmail);
                }

            } catch (Exception e) {
                log.error("❌ Exception in membership onboarding: {}", e.getMessage(), e);
            }
        }

        // Clear store-specific cookies
        storeService.deleteAllCookiesByStoreId(response, StoreConstants.CanadaStore.STORE_ID);

        return VIEW_CANADA_STORE_SCREEN_7;
    }


    @GetMapping("/failed")
    public String showFailedPage(Model model) {
        model.addAttribute(ATTRIBUTE_STEPPER, stepperService.getStepperByScreen(7));
        return VIEW_CANADA_STORE_SCREEN_6;
    }

    @GetMapping("/error")
    public String showErrorPage(Model model) {
        model.addAttribute(ATTRIBUTE_STEPPER, stepperService.getStepperByScreen(8));
        return VIEW_CANADA_STORE_SCREEN_7;
    }
}
