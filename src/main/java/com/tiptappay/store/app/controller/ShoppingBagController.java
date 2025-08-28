package com.tiptappay.store.app.controller;

import com.tiptappay.store.app.constant.AppConstants;
import com.tiptappay.store.app.constant.StoreConstants;
import com.tiptappay.store.app.dto.canada.CanadaStoreShoppingBag;
import com.tiptappay.store.app.dto.mosques.store.MosquesStoreShoppingBag;
import com.tiptappay.store.app.dto.saus.store.SaUsStoreShoppingBag;
import com.tiptappay.store.app.dto.us.store.UsStoreShoppingBag;
import com.tiptappay.store.app.model.Product;
import com.tiptappay.store.app.model.ProductReq;
import com.tiptappay.store.app.model.ShoppingBagUpdateResp;
import com.tiptappay.store.app.service.StoreService;
import com.tiptappay.store.app.service.canada.CanadaStoreService;
import com.tiptappay.store.app.service.mosques.MosquesStoreService;
import com.tiptappay.store.app.service.saus.SaUsStoreService;
import com.tiptappay.store.app.service.us.store.UsStoreService;
import com.tiptappay.store.app.util.FormatCents;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.tiptappay.store.app.constant.AppConstants.Attributes.*;
import static com.tiptappay.store.app.constant.AppConstants.RedirectTemplates.REDIRECT_CANADA_CONTACT_AND_SHIPPING;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ShoppingBagController {

    private final StoreService storeService;
    private final UsStoreService usStoreService;
    private final MosquesStoreService mosquesStoreService;
    private final SaUsStoreService saUsStoreService;
    private final CanadaStoreService canadaStoreService;

    @GetMapping("/us-catholic/fresno-diocese/shopping-bag")
    public String showUsStoreShoppingBag(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Model model) {

        boolean isQuoteAvailable = false;

        UsStoreShoppingBag usStoreShoppingBag =
                (UsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.USStore.STORE_ID);
        if (usStoreShoppingBag == null || usStoreShoppingBag.totalNumberOfProducts() <= 0) {
            log.info("ShoppingBagController.showUsStoreShoppingBag: {}", "Us Store Fresno Diocese Shopping bag is empty");
            usStoreShoppingBag = new UsStoreShoppingBag();
        }

        List<Product> products = usStoreService.getProductList(
                usStoreShoppingBag.getMiniClipKitQty(),
                usStoreShoppingBag.getDevice5DollarQty(),
                usStoreShoppingBag.getDevice10DollarQty(),
                usStoreShoppingBag.getDevice20DollarQty()
        );

        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, usStoreShoppingBag);
        model.addAttribute(ATTRIBUTE_PRODUCTS, products);
        model.addAttribute(ATTRIBUTE_IS_QUOTE_AVAILABLE, isQuoteAvailable);
        model.addAttribute(ATTRIBUTE_FORMAT_CENTS, new FormatCents());

        return AppConstants.ViewTemplates.VIEW_US_STORE_FRESNO_SCREEN_SHOPPING_BAG;
    }

    @GetMapping("/mosques/shopping-bag")
    public String showMosquesStoreShoppingBag(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Model model) {

        boolean isQuoteAvailable = false;

        MosquesStoreShoppingBag mosquesStoreShoppingBag =
                (MosquesStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.MosquesStore.STORE_ID);
        if (mosquesStoreShoppingBag == null || mosquesStoreShoppingBag.totalNumberOfProducts() <= 0) {
            log.info("ShoppingBagController.showMosquesStoreShoppingBag: {}", "Mosques Store Shopping bag is empty");
            mosquesStoreShoppingBag = new MosquesStoreShoppingBag();
        }

        List<Product> products = mosquesStoreService.getProductList(
                mosquesStoreShoppingBag.getFloorStandQty(),
                mosquesStoreShoppingBag.getDevice5DollarQty(),
                mosquesStoreShoppingBag.getDevice10DollarQty(),
                mosquesStoreShoppingBag.getDevice20DollarQty()
        );

        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, mosquesStoreShoppingBag);
        model.addAttribute(ATTRIBUTE_PRODUCTS, products);
        model.addAttribute(ATTRIBUTE_IS_QUOTE_AVAILABLE, isQuoteAvailable);
        model.addAttribute(ATTRIBUTE_FORMAT_CENTS, new FormatCents());

        return AppConstants.ViewTemplates.VIEW_MOSQUES_STORE_SCREEN_SHOPPING_BAG;
    }

    @GetMapping("/canada-store/shopping-bag")
    public String showCanadaStoreShoppingBag(HttpServletRequest request,
                                             HttpServletResponse response,
                                             Model model) {

        boolean isQuoteAvailable = false;

        CanadaStoreShoppingBag canadaStoreShoppingBag =
                (CanadaStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.CanadaStore.STORE_ID);

        if (canadaStoreShoppingBag == null || canadaStoreShoppingBag.totalNumberOfProducts() <= 0) {
            log.info("CanadaStoreShoppingBag is null or empty. Initializing new bag.");
            canadaStoreShoppingBag = new CanadaStoreShoppingBag(); // fallback
        } else {
            log.info("Loaded CanadaStoreShoppingBag from cookie: {}", canadaStoreShoppingBag);
        }

        List<Product> products = canadaStoreService.getProductList(canadaStoreShoppingBag);


        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, canadaStoreShoppingBag);
        model.addAttribute(ATTRIBUTE_PRODUCTS, products);
        model.addAttribute(ATTRIBUTE_IS_QUOTE_AVAILABLE, isQuoteAvailable);
        model.addAttribute(ATTRIBUTE_FORMAT_CENTS, new FormatCents());

        return REDIRECT_CANADA_CONTACT_AND_SHIPPING;
    }



    @GetMapping("/salvation-army-us/shopping-bag")
    public String showSaUsStoreShoppingBag(HttpServletRequest request,
                                           HttpServletResponse response,
                                           Model model) {

        boolean isQuoteAvailable = false;

        SaUsStoreShoppingBag saUsStoreShoppingBag =
                (SaUsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.SaUsStore.STORE_ID);
        if (saUsStoreShoppingBag == null || saUsStoreShoppingBag.totalNumberOfProducts() <= 0) {
            log.info("ShoppingBagController.showSaUsStoreShoppingBag: {}", "Sa Us Store Shopping bag is empty");
            saUsStoreShoppingBag = new SaUsStoreShoppingBag();
        }

        List<Product> products = saUsStoreService.getProductList(
                saUsStoreShoppingBag.getKettleDisplayQty(),
                saUsStoreShoppingBag.getDevice5DollarQty(),
                saUsStoreShoppingBag.getDevice10DollarQty(),
                saUsStoreShoppingBag.getDevice20DollarQty(),
                saUsStoreShoppingBag.getBatteryForKettleDisplayQty()
        );

        model.addAttribute(ATTRIBUTE_STORE_SHOPPING_BAG, saUsStoreShoppingBag);
        model.addAttribute(ATTRIBUTE_PRODUCTS, products);
        model.addAttribute(ATTRIBUTE_IS_QUOTE_AVAILABLE, isQuoteAvailable);
        model.addAttribute(ATTRIBUTE_FORMAT_CENTS, new FormatCents());

        return AppConstants.ViewTemplates.VIEW_SAUS_STORE_SCREEN_SHOPPING_BAG;
    }

    @GetMapping("/us-catholic/fresno-diocese/shopping-bag/update")
    public String showUpdateUsStoreBag(HttpSession httpSession) {
        httpSession.invalidate();
        return AppConstants.RedirectTemplates.REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ORDER;
    }

    @GetMapping("/mosques/shopping-bag/update")
    public String showUpdateMosquesBag(HttpSession httpSession) {
        httpSession.invalidate();
        return AppConstants.RedirectTemplates.REDIRECT_MOSQUES_ORDER;
    }

    @GetMapping("/salvation-army-us/shopping-bag/update")
    public String showUpdateSaUsBag(HttpSession httpSession) {
        httpSession.invalidate();
        return AppConstants.RedirectTemplates.REDIRECT_SAUS_ORDER;
    }

    @PostMapping("/us-catholic/fresno-diocese/shopping-bag/update")
    public ResponseEntity<ShoppingBagUpdateResp> updateUsStoreBag(HttpServletRequest request,
                                                                  HttpServletResponse response,
                                                                  @RequestBody List<ProductReq> productReqList) {

        boolean isUpdated = false;
        UsStoreShoppingBag usStoreShoppingBag =
                (UsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.USStore.STORE_ID);

        if (usStoreShoppingBag == null) {
            return ResponseEntity.ok(new ShoppingBagUpdateResp(false,
                    0, 0, 0, 0, 0,
                    0, 0,
                    "0.00", "0.00", "0.00",
                    "0.00", "0.00", "0.00",
                    "0.00", 0,
                    "0.00",
                    "0.00",
                    "0.00",
                    "0.00",
                    "0.00",
                    "Nothing to proceed")
            );
        }

        for (ProductReq productReq : productReqList) {

            if (productReq.getId().equals(StoreConstants.USStore.PRODUCT_ID_MINI_CLIP_KIT)) {
                isUpdated = true;
                usStoreShoppingBag.setMiniClipKitQty(productReq.getQuantity());
            }

            if (productReq.getId().equals(StoreConstants.USStore.PRODUCT_ID_DEVICE_5_DOLLAR)) {
                isUpdated = true;
                usStoreShoppingBag.setDevice5DollarQty(productReq.getQuantity());
            }

            if (productReq.getId().equals(StoreConstants.USStore.PRODUCT_ID_DEVICE_10_DOLLAR)) {
                isUpdated = true;
                usStoreShoppingBag.setDevice10DollarQty(productReq.getQuantity());
            }

            if (productReq.getId().equals(StoreConstants.USStore.PRODUCT_ID_DEVICE_20_DOLLAR)) {
                isUpdated = true;
                usStoreShoppingBag.setDevice20DollarQty(productReq.getQuantity());
            }

        }

        if (!isUpdated) {
            return ResponseEntity.ok(new ShoppingBagUpdateResp(false,
                    0, 0, 0, 0, 0,
                    0, 0,
                    "0.00", "0.00", "0.00",
                    "0.00", "0.00", "0.00",
                    "0.00", 0,
                    "0.00",
                    "0.00",
                    "0.00",
                    "0.00",
                    "0.00",
                    "Shopping bag not updated")
            );
        }

        storeService.setCookieSB(response, usStoreShoppingBag, StoreConstants.USStore.STORE_ID);

        ShoppingBagUpdateResp resp = new ShoppingBagUpdateResp(true,
                usStoreShoppingBag.getMiniClipKitQty(),
                0,
                0,
                usStoreShoppingBag.getDevice5DollarQty(),
                usStoreShoppingBag.getDevice10DollarQty(),
                usStoreShoppingBag.getDevice20DollarQty(),
                0,
                usStoreShoppingBag.miniClipKitTotalFormatted(),
                "0.00",
                "0.00",
                usStoreShoppingBag.device5DollarTotalFormatted(),
                usStoreShoppingBag.device10DollarTotalFormatted(),
                usStoreShoppingBag.device20DollarTotalFormatted(),
                "0.00",
                usStoreShoppingBag.totalNumberOfProducts(),
                usStoreShoppingBag.totalOneTimeFeeFormatted(),
                usStoreShoppingBag.totalRentalFeeFormatted(),
                "0.00",
                "0.00",
                usStoreShoppingBag.totalAmountFormatted(),
                "Shopping bag updated");

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/canada-store/shopping-bag/update")
    public String updateCanadaStoreBag(HttpServletRequest request,
                                       HttpServletResponse response,
                                       @ModelAttribute CanadaStoreShoppingBag storeShoppingBag) {

        CanadaStoreShoppingBag canadaStoreShoppingBag =
                (CanadaStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.CanadaStore.STORE_ID);

        if (canadaStoreShoppingBag == null) {
            canadaStoreShoppingBag = new CanadaStoreShoppingBag();
        }

        // 3-device display quantities
        canadaStoreShoppingBag.setThreeDeviceDisplayEnglishQuantity(storeShoppingBag.getThreeDeviceDisplayEnglishQuantity());
        canadaStoreShoppingBag.setThreeDeviceDisplayFrenchQuantity(storeShoppingBag.getThreeDeviceDisplayFrenchQuantity());
        canadaStoreShoppingBag.setThreeDeviceDisplayBilingualQuantity(storeShoppingBag.getThreeDeviceDisplayBilingualQuantity());

        // Single curve - $5
        canadaStoreShoppingBag.setSingleCurve5DollarEnglishQuantity(storeShoppingBag.getSingleCurve5DollarEnglishQuantity());
        canadaStoreShoppingBag.setSingleCurve5DollarFrenchQuantity(storeShoppingBag.getSingleCurve5DollarFrenchQuantity());
        canadaStoreShoppingBag.setSingleCurve5DollarBilingualQuantity(storeShoppingBag.getSingleCurve5DollarBilingualQuantity());

        // Single curve - $10
        canadaStoreShoppingBag.setSingleCurve10DollarEnglishQuantity(storeShoppingBag.getSingleCurve10DollarEnglishQuantity());
        canadaStoreShoppingBag.setSingleCurve10DollarFrenchQuantity(storeShoppingBag.getSingleCurve10DollarFrenchQuantity());
        canadaStoreShoppingBag.setSingleCurve10DollarBilingualQuantity(storeShoppingBag.getSingleCurve10DollarBilingualQuantity());

        // Single curve - $20
        canadaStoreShoppingBag.setSingleCurve20DollarEnglishQuantity(storeShoppingBag.getSingleCurve20DollarEnglishQuantity());
        canadaStoreShoppingBag.setSingleCurve20DollarFrenchQuantity(storeShoppingBag.getSingleCurve20DollarFrenchQuantity());
        canadaStoreShoppingBag.setSingleCurve20DollarBilingualQuantity(storeShoppingBag.getSingleCurve20DollarBilingualQuantity());
        canadaStoreShoppingBag.setSingleCurveEnglishQuantity(
                storeShoppingBag.getSingleCurve5DollarEnglishQuantity()
                        + storeShoppingBag.getSingleCurve10DollarEnglishQuantity()
                        + storeShoppingBag.getSingleCurve20DollarEnglishQuantity()
        );

        canadaStoreShoppingBag.setSingleCurveFrenchQuantity(
                storeShoppingBag.getSingleCurve5DollarFrenchQuantity()
                        + storeShoppingBag.getSingleCurve10DollarFrenchQuantity()
                        + storeShoppingBag.getSingleCurve20DollarFrenchQuantity()
        );

        canadaStoreShoppingBag.setSingleCurveBilingualQuantity(
                storeShoppingBag.getSingleCurve5DollarBilingualQuantity()
                        + storeShoppingBag.getSingleCurve10DollarBilingualQuantity()
                        + storeShoppingBag.getSingleCurve20DollarBilingualQuantity()
        );


        // Rental device totals (set from JS-calculated hidden fields)
        canadaStoreShoppingBag.setDevice5DollarQty(storeShoppingBag.getDevice5DollarQty());
        canadaStoreShoppingBag.setDevice10DollarQty(storeShoppingBag.getDevice10DollarQty());
        canadaStoreShoppingBag.setDevice20DollarQty(storeShoppingBag.getDevice20DollarQty());

        storeService.setCookieSB(response, canadaStoreShoppingBag, StoreConstants.CanadaStore.STORE_ID);

        return "redirect:/canada-store/shopping-bag";
    }


    @PostMapping("/mosques/shopping-bag/update")
    public ResponseEntity<ShoppingBagUpdateResp> updateMosquesStoreBag(HttpServletRequest request,
                                                                       HttpServletResponse response,
                                                                       @RequestBody List<ProductReq> productReqList) {

        boolean isUpdated = false;
        MosquesStoreShoppingBag mosquesStoreShoppingBag =
                (MosquesStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.MosquesStore.STORE_ID);

        if (mosquesStoreShoppingBag == null) {
            return ResponseEntity.ok(new ShoppingBagUpdateResp(false,
                    0, 0, 0, 0, 0,
                    0, 0,
                    "0.00", "0.00", "0.00",
                    "0.00", "0.00", "0.00",
                    "0.00", 0,
                    "0.00",
                    "0.00",
                    "0.00",
                    "0.00",
                    "0.00",
                    "Shopping bag not updated")
            );
        }

        for (ProductReq productReq : productReqList) {
            if (productReq.getId().equals(StoreConstants.MosquesStore.PRODUCT_ID_FLOOR_STAND)) {
                isUpdated = true;
                mosquesStoreShoppingBag.setFloorStandQty(productReq.getQuantity());
                mosquesStoreShoppingBag.setDevice5DollarQty(productReq.getQuantity());
                mosquesStoreShoppingBag.setDevice10DollarQty(productReq.getQuantity());
                mosquesStoreShoppingBag.setDevice20DollarQty(productReq.getQuantity());
            }
        }

        if (!isUpdated) {
            return ResponseEntity.ok(new ShoppingBagUpdateResp(false,
                    0, 0, 0, 0, 0,
                    0, 0,
                    "0.00", "0.00", "0.00",
                    "0.00", "0.00", "0.00",
                    "0.00", 0,
                    "0.00",
                    "0.00",
                    "0.00",
                    "0.00",
                    "0.00",
                    "Shopping bag not updated")
            );
        }

        storeService.setCookieSB(response, mosquesStoreShoppingBag, StoreConstants.MosquesStore.STORE_ID);

        ShoppingBagUpdateResp resp = new ShoppingBagUpdateResp(true,
                0,
                mosquesStoreShoppingBag.getFloorStandQty(),
                0,
                mosquesStoreShoppingBag.getDevice5DollarQty(),
                mosquesStoreShoppingBag.getDevice10DollarQty(),
                mosquesStoreShoppingBag.getDevice20DollarQty(),
                0,
                "0.00",
                mosquesStoreShoppingBag.floorStandTotalFormatted(),
                "0.00",
                mosquesStoreShoppingBag.device5DollarTotalFormatted(),
                mosquesStoreShoppingBag.device10DollarTotalFormatted(),
                mosquesStoreShoppingBag.device20DollarTotalFormatted(),
                "0.00",
                mosquesStoreShoppingBag.totalNumberOfProducts(),
                mosquesStoreShoppingBag.totalOneTimeFeeFormatted(),
                mosquesStoreShoppingBag.totalRentalFeeFormatted(),
                "0.00",
                "0.00",
                mosquesStoreShoppingBag.totalAmountFormatted(),
                "Shopping bag updated");

        return ResponseEntity.ok(resp);
    }


    @PostMapping("/salvation-army-us/shopping-bag/update")
    public ResponseEntity<ShoppingBagUpdateResp> updateSaUsStoreBag(HttpServletRequest request,
                                                                    HttpServletResponse response,
                                                                    @RequestBody List<ProductReq> productReqList) {

        boolean isUpdated = false;
        SaUsStoreShoppingBag saUsStoreShoppingBag =
                (SaUsStoreShoppingBag) storeService.handleCookieSB(request, response, StoreConstants.SaUsStore.STORE_ID);

        if (saUsStoreShoppingBag == null) {
            return ResponseEntity.ok(new ShoppingBagUpdateResp(false,
                    0, 0, 0, 0, 0,
                    0, 0,
                    "0.00", "0.00", "0.00",
                    "0.00", "0.00", "0.00",
                    "0.00", 0,
                    "0.00",
                    "0.00",
                    "0.00",
                    "0.00",
                    "0.00",
                    "Shopping bag not updated")
            );
        }

        for (ProductReq productReq : productReqList) {
            if (productReq.getId().equals(StoreConstants.SaUsStore.PRODUCT_ID_KETTLE_DISPLAY)) {
                isUpdated = true;
                saUsStoreShoppingBag.setKettleDisplayQty(productReq.getQuantity());
                saUsStoreShoppingBag.setDevice5DollarQty(productReq.getQuantity());
                saUsStoreShoppingBag.setDevice10DollarQty(productReq.getQuantity());
                saUsStoreShoppingBag.setDevice20DollarQty(productReq.getQuantity());
                saUsStoreShoppingBag.setBatteryForKettleDisplayQty(productReq.getQuantity() * 2);
            }
        }

        if (!isUpdated) {
            return ResponseEntity.ok(new ShoppingBagUpdateResp(false,
                    0, 0, 0, 0, 0,
                    0, 0,
                    "0.00", "0.00", "0.00",
                    "0.00", "0.00", "0.00",
                    "0.00", 0,
                    "0.00",
                    "0.00",
                    "0.00",
                    "0.00",
                    "0.00",
                    "Shopping bag not updated")
            );
        }

        storeService.setCookieSB(response, saUsStoreShoppingBag, StoreConstants.SaUsStore.STORE_ID);

        ShoppingBagUpdateResp resp = new ShoppingBagUpdateResp(true,
                0,
                0,
                saUsStoreShoppingBag.getKettleDisplayQty(),
                saUsStoreShoppingBag.getDevice5DollarQty(),
                saUsStoreShoppingBag.getDevice10DollarQty(),
                saUsStoreShoppingBag.getDevice20DollarQty(),
                saUsStoreShoppingBag.getBatteryForKettleDisplayQty(),
                "0.00",
                "0.00",
                saUsStoreShoppingBag.kettleDisplayTotalFormatted(),
                saUsStoreShoppingBag.device5DollarTotalFormatted(),
                saUsStoreShoppingBag.device10DollarTotalFormatted(),
                saUsStoreShoppingBag.device20DollarTotalFormatted(),
                saUsStoreShoppingBag.batteryForKettleDisplayTotalFormatted(),
                saUsStoreShoppingBag.totalNumberOfProducts(),
                saUsStoreShoppingBag.totalOneTimeFeeFormatted(),
                saUsStoreShoppingBag.totalRentalFeeFormatted(),
                "0.00",
                "0.00",
                saUsStoreShoppingBag.totalAmountFormatted(),
                "Shopping bag updated");

        return ResponseEntity.ok(resp);
    }
}
