package com.tiptappay.store.app.controller;

import com.tiptappay.store.app.model.*;
import com.tiptappay.store.app.util.FormatCents;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.tiptappay.store.app.constant.AppConstants.Attributes.ATTRIBUTE_STORE_BAG;
import static com.tiptappay.store.app.constant.AppConstants.Attributes.ATTRIBUTE_STORE_PRICING;
import static com.tiptappay.store.app.constant.AppConstants.RedirectTemplates.REDIRECT_STORE;
import static com.tiptappay.store.app.constant.AppConstants.ViewTemplates.VIEW_STORE_BAG;

@Slf4j
@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class BagController {

    @GetMapping("/bag")
    public String showStoreBag(@RequestParam(value = "backTo", required = false) String backTo, HttpSession httpSession, Model model) {

        // Check Store Bag
        Bag bag = (Bag) httpSession.getAttribute(ATTRIBUTE_STORE_BAG);

        if (bag == null) {
            bag = new Bag();
        }

        Pricing pricing = (Pricing) httpSession.getAttribute(ATTRIBUTE_STORE_PRICING);

        if (pricing == null) {
            return REDIRECT_STORE;
        }

        int totalQty = Bag.totalBagQuantity(bag);
        int subscriptionFee = totalQty * pricing.getMonthlyFeeAmount();
        int serviceFee = pricing.getSetupFeeAmount();
        int totalAmount = subscriptionFee + serviceFee;

        boolean isBagEmpty = Bag.isBagEmpty(bag);

        model.addAttribute("subscriptionFee", subscriptionFee);
        model.addAttribute("serviceFee", isBagEmpty ? 0 : serviceFee);
        model.addAttribute("totalAmount", isBagEmpty ? 0 : totalAmount);
        model.addAttribute("formatter", new FormatCents());

        model.addAttribute(ATTRIBUTE_STORE_BAG, bag);
        model.addAttribute(ATTRIBUTE_STORE_PRICING, pricing);
        model.addAttribute("backTo", (backTo == null ? "/store" : backTo));

        return VIEW_STORE_BAG;
    }

    @GetMapping("/bag/add")
    public String showBagAdd(HttpSession httpSession) {
        httpSession.invalidate();
        return REDIRECT_STORE;
    }

    @PostMapping("/bag/add")
    public ResponseEntity<BagAddResp> addToBag(@RequestBody Bag incomingBag, HttpSession httpSession) {
        if (incomingBag != null) {
            System.out.println(incomingBag.toString());

            if (Bag.isBagEmpty(incomingBag)) {
                return ResponseEntity.ok(new BagAddResp(false, 0, "Nothing to add!"));
            }

            Bag sessionBag = (Bag) httpSession.getAttribute(ATTRIBUTE_STORE_BAG);
            int totalDevices = 0;
            if (sessionBag != null) {
                sessionBag = sessionBag.addToCurrentBag(incomingBag);
                httpSession.setAttribute(ATTRIBUTE_STORE_BAG, sessionBag);
            } else {
                httpSession.setAttribute(ATTRIBUTE_STORE_BAG, incomingBag);
            }

            Bag updatedBag = (Bag) httpSession.getAttribute(ATTRIBUTE_STORE_BAG);
            totalDevices = Bag.totalBagQuantity(updatedBag);

            return ResponseEntity.ok(new BagAddResp(true, totalDevices, "Product(s) added to bag successfully."));
        }
        return ResponseEntity.ok(new BagAddResp(false, 0, "Incoming bag is null!"));
    }

    @GetMapping("/bag/remove")
    public String showBagRemove(HttpSession httpSession) {
        httpSession.invalidate();
        return REDIRECT_STORE;
    }

    @PostMapping("/bag/remove")
    public ResponseEntity<BagRemoveResp> removeFromBag(@RequestBody BagRemoveReq bagRemoveReq, HttpSession httpSession) {
        if (bagRemoveReq != null) {
            System.out.println(bagRemoveReq.toString());

            Bag sessionBag = (Bag) httpSession.getAttribute(ATTRIBUTE_STORE_BAG);

            if (sessionBag != null && !bagRemoveReq.getFieldName().isEmpty()) {
                sessionBag.removeFromCurrentBag(bagRemoveReq.getFieldName());
                httpSession.setAttribute(ATTRIBUTE_STORE_BAG, sessionBag);
            }
        }

        Bag currentBag = (Bag) httpSession.getAttribute(ATTRIBUTE_STORE_BAG);
        int totalDevices = Bag.totalBagQuantity(currentBag);
        return ResponseEntity.ok(new BagRemoveResp(true, totalDevices, "Product(s) removed from bag successfully."));
    }

}
