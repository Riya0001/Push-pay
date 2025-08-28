package com.tiptappay.store.app.controller.us.store;

import com.tiptappay.store.app.constant.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/us-catholic")
@RequiredArgsConstructor
public class UsStoreController {
    @GetMapping()
    public String redirectToFresnoDiocesePageIndex() {
        return AppConstants.RedirectTemplates.REDIRECT_US_CATHOLIC_FRESNO_DIOCESE_ORDER;
    }
}
