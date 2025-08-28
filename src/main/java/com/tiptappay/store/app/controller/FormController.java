package com.tiptappay.store.app.controller;

import com.tiptappay.store.app.constant.AppConstants;
import com.tiptappay.store.app.model.*;
import com.tiptappay.store.app.service.FormService;
import com.tiptappay.store.app.util.FormatCents;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class FormController {

    private final FormService formService;

    @GetMapping()
    public String showStoreIndex(HttpSession httpSession, Model model) {
        Bag bag = (Bag) httpSession.getAttribute(AppConstants.Attributes.ATTRIBUTE_STORE_BAG);

        if (bag == null) {
            bag = new Bag();
        }

        // set pricing for every page
        Pricing pricing = formService.setPricingDefaults();
        httpSession.setAttribute(AppConstants.Attributes.ATTRIBUTE_STORE_PRICING, pricing);

        model.addAttribute(AppConstants.Attributes.ATTRIBUTE_STORE_BAG, bag);
        model.addAttribute("bagBackTo", "/store");
        return AppConstants.ViewTemplates.VIEW_STORE_INDEX;
    }


    @PostMapping()
    public String processStoreIndex(HttpSession httpSession,
                                    Model model) {

        Bag bag = (Bag) httpSession.getAttribute(AppConstants.Attributes.ATTRIBUTE_STORE_BAG);

        if (bag == null || Bag.isBagEmpty(bag)) {
            model.addAttribute(AppConstants.Attributes.ATTRIBUTE_BAG_EMPTY, AppConstants.AppMessages.BAG_EMPTY_MESSAGE);
            return AppConstants.ViewTemplates.VIEW_STORE_INDEX;
        }

        return AppConstants.RedirectTemplates.REDIRECT_STORE_CHECKOUT;
    }

    @GetMapping("/checkout")
    public String showStoreCheckout(HttpSession httpSession, Model model) {

        // Check Store Bag
        Bag bag = (Bag) httpSession.getAttribute(AppConstants.Attributes.ATTRIBUTE_STORE_BAG);

        if (bag == null || Bag.isBagEmpty(bag)) {
            httpSession.invalidate();
            return AppConstants.RedirectTemplates.REDIRECT_STORE;
        }

        // Check Order Form
        Form form = (Form) httpSession.getAttribute(AppConstants.Attributes.ATTRIBUTE_ORDER_FORM);

        if (form == null) {
            form = formService.setFormDefaults(); // TODO : Change later!
        }

        model.addAttribute(AppConstants.Attributes.ATTRIBUTE_ORDER_FORM, form);
        model.addAttribute("bagBackTo", "/store/checkout");
        model.addAttribute(AppConstants.Attributes.ATTRIBUTE_STORE_BAG, bag);
        return AppConstants.ViewTemplates.VIEW_STORE_CHECKOUT_STEP_1;
    }

    @PostMapping("/checkout")
    public String processStoreCheckout(@Valid Form form, BindingResult bindingResult, HttpSession httpSession, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("invalidForm", "Please fill in all fields in the form correctly.");
            return AppConstants.ViewTemplates.VIEW_STORE_CHECKOUT_STEP_1;
        }

        Bag bag = (Bag) httpSession.getAttribute(AppConstants.Attributes.ATTRIBUTE_STORE_BAG);
        Pricing pricing = (Pricing) httpSession.getAttribute(AppConstants.Attributes.ATTRIBUTE_STORE_PRICING);
        CalculationToken calculationToken = (CalculationToken) httpSession.getAttribute("calculationToken");

        if (calculationToken == null || !calculationToken.getTokenString().equalsIgnoreCase(formService.generateTokenString(form, bag))) {
            // Send Quote Request to NetSuite
            ResponseQuote quoteResp = formService.sendQuoteRequestToNetSuite(form, null, bag, pricing);

            if (!quoteResp.isSuccess()) {
                model.addAttribute("invalidAddress", "Please enter a valid address.");
                return AppConstants.ViewTemplates.VIEW_STORE_CHECKOUT_STEP_1;
            }

            String shippingString = formService.generateTokenString(form, bag);

            calculationToken = new CalculationToken(shippingString, quoteResp);
            httpSession.setAttribute("calculationToken", calculationToken);
        }

        httpSession.setAttribute(AppConstants.Attributes.ATTRIBUTE_ORDER_FORM, form);

        return AppConstants.RedirectTemplates.REDIRECT_STORE_SUMMARY;
    }

    @GetMapping("/summary")
    public String showStoreSummary(HttpSession httpSession, Model model) {

        CalculationToken calculationToken = (CalculationToken) httpSession.getAttribute("calculationToken");
        Bag bag = (Bag) httpSession.getAttribute(AppConstants.Attributes.ATTRIBUTE_STORE_BAG);
        Pricing pricing = (Pricing) httpSession.getAttribute(AppConstants.Attributes.ATTRIBUTE_STORE_PRICING);
        Form form = (Form) httpSession.getAttribute(AppConstants.Attributes.ATTRIBUTE_ORDER_FORM);

        if (calculationToken == null || bag == null || Bag.isBagEmpty(bag) || pricing == null || form == null) {
            return AppConstants.RedirectTemplates.REDIRECT_STORE_CHECKOUT;
        }

        model.addAttribute("quoteResp", calculationToken.getQuote());

        int totalQty = Bag.totalBagQuantity(bag);
        int subscriptionFee = totalQty * pricing.getMonthlyFeeAmount();
        int serviceFee = pricing.getSetupFeeAmount();

        int sh = (int) (calculationToken.getQuote().getShippingAmount() * 100);
        int tx = (int) (calculationToken.getQuote().getTaxAmount() * 100);

        int totalAmount = subscriptionFee + serviceFee + sh + tx;

        boolean isBagEmpty = Bag.isBagEmpty(bag);

        model.addAttribute("subscriptionFee", subscriptionFee);
        model.addAttribute("serviceFee", isBagEmpty ? 0 : serviceFee);
        model.addAttribute("totalAmount", isBagEmpty ? 0 : totalAmount);
        model.addAttribute("formatter", new FormatCents());

        model.addAttribute(AppConstants.Attributes.ATTRIBUTE_ORDER_FORM, form);
        model.addAttribute(AppConstants.Attributes.ATTRIBUTE_STORE_BAG, bag);
        model.addAttribute(AppConstants.Attributes.ATTRIBUTE_STORE_PRICING, pricing);

        CheckoutForm checkoutForm = new CheckoutForm();
        model.addAttribute("checkoutForm", checkoutForm);

        model.addAttribute("bagBackTo", "/store/summary");

        return AppConstants.ViewTemplates.VIEW_STORE_CHECKOUT_STEP_2;
    }

    @PostMapping("/summary")
    public String processStoreSummary(@Valid CheckoutForm checkoutForm, BindingResult bindingResult, HttpSession httpSession, Model model) {

        CalculationToken calculationToken = (CalculationToken) httpSession.getAttribute("calculationToken");
        Bag bag = (Bag) httpSession.getAttribute(AppConstants.Attributes.ATTRIBUTE_STORE_BAG);
        Pricing pricing = (Pricing) httpSession.getAttribute(AppConstants.Attributes.ATTRIBUTE_STORE_PRICING);
        Form form = (Form) httpSession.getAttribute(AppConstants.Attributes.ATTRIBUTE_ORDER_FORM);

        if (bindingResult.hasErrors()) {
            model.addAttribute("invalidForm", "Please fill in all fields in the form correctly.");
            model.addAttribute("quoteResp", calculationToken.getQuote());

            int totalQty = Bag.totalBagQuantity(bag);
            int subscriptionFee = totalQty * pricing.getMonthlyFeeAmount();
            int serviceFee = pricing.getSetupFeeAmount();

            int sh = (int) (calculationToken.getQuote().getShippingAmount() * 100);
            int tx = (int) (calculationToken.getQuote().getTaxAmount() * 100);

            int totalAmount = subscriptionFee + serviceFee + sh + tx;

            boolean isBagEmpty = Bag.isBagEmpty(bag);

            model.addAttribute("subscriptionFee", subscriptionFee);
            model.addAttribute("serviceFee", isBagEmpty ? 0 : serviceFee);
            model.addAttribute("totalAmount", isBagEmpty ? 0 : totalAmount);
            model.addAttribute("formatter", new FormatCents());

            model.addAttribute(AppConstants.Attributes.ATTRIBUTE_ORDER_FORM, form);
            model.addAttribute(AppConstants.Attributes.ATTRIBUTE_STORE_BAG, bag);
            model.addAttribute(AppConstants.Attributes.ATTRIBUTE_STORE_PRICING, pricing);


            return AppConstants.ViewTemplates.VIEW_STORE_CHECKOUT_STEP_2;
        }

        // Send Request to NetSuite
        ResponsePayment responsePayment = formService.sendRequestToNetSuite(form, checkoutForm, bag, pricing);

        httpSession.setAttribute(AppConstants.Attributes.ATTRIBUTE_ORDER_RESPONSE, responsePayment);

        return "redirect:/store/submit";
    }

    @GetMapping("/submit")
    public String showStoreSubmit(HttpSession httpSession, Model model) {

        ResponsePayment responsePayment = (ResponsePayment) httpSession.getAttribute(AppConstants.Attributes.ATTRIBUTE_ORDER_RESPONSE);

        if (responsePayment == null) {
            httpSession.invalidate();
            return AppConstants.RedirectTemplates.REDIRECT_STORE;
        }

        model.addAttribute(AppConstants.Attributes.ATTRIBUTE_ORDER_RESPONSE, responsePayment);

        // Clear the Session
        httpSession.invalidate();

        return AppConstants.ViewTemplates.VIEW_STORE_RESPONSE;
    }

}
