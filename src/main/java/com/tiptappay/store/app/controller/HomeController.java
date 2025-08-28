package com.tiptappay.store.app.controller;

import com.tiptappay.store.app.service.StoreService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.tiptappay.store.app.constant.AppConstants.RedirectTemplates.*;
import static com.tiptappay.store.app.constant.AppConstants.ViewTemplates.VIEW_HOME_INDEX;
import static com.tiptappay.store.app.constant.AppConstants.ViewTemplates.VIEW_STYLES_INDEX;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final StoreService storeService;

    @GetMapping("/")
    public String home(HttpServletResponse response) {

        storeService.deleteAllCookies(response);

        return VIEW_HOME_INDEX;
    }

    @GetMapping("/styles")
    public String styles() {
        return VIEW_STYLES_INDEX;
    }

    @GetMapping("/fresno-diocese")
    public String fresnoDiocese() {
        return REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ORDER;
    }

    @GetMapping("/mosques")
    public String mosques() {
        return REDIRECT_MOSQUES_ORDER;
    }

    @GetMapping("/salvation-army-us")
    public String saus() {
        return REDIRECT_SAUS_ORDER;
    }

    @GetMapping("/bundles")
    public String bundles() {
        return REDIRECT_TTP_BUNDLES_ORDER;
    }

    @GetMapping("{salesRepId}/bundles")
    public String bundlesWithSalesRep(@PathVariable String salesRepId) {
        return String.format("redirect:/%s/bundles/order", salesRepId);
    }

    @GetMapping("/benevity")
    public String benevity() {
        return REDIRECT_BENEVITY_ORDER;
    }

    @GetMapping("/benevity/fb")
    public String benevityFB() {
        return REDIRECT_BENEVITY_FB_ORDER;
    }
    
    @GetMapping("/benevity/faith")
    public String benevityFaith() {
        return REDIRECT_BENEVITY_FB_ORDER;
    }

    @GetMapping("/benevity/education")
    public String benevityEducation() {
        return REDIRECT_BENEVITY_EDUCATION_ORDER;
    }
}