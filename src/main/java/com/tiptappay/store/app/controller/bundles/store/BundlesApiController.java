package com.tiptappay.store.app.controller.bundles.store;

import com.tiptappay.store.app.dto.bundles.store.CartData;
import com.tiptappay.store.app.service.product.BundleProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/bundles")
@RequiredArgsConstructor
public class BundlesApiController {
    private final BundleProductService bundleProductService;

    @PostMapping("/calculate")
    public List<CartData> calculateBundles(@RequestBody List<CartData> bundles, @RequestParam(name = "sr", required = false) String salesRepId) {
        // No special pricing adjustments are applied for any sales rep's customer, we keep this for future usage if necessary
        return bundleProductService.setBundlesPriceKeyAndCalculate(bundles);
    }
}