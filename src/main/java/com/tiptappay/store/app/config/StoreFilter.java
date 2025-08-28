package com.tiptappay.store.app.config;

import com.tiptappay.store.app.constant.AppConstants;
import com.tiptappay.store.app.model.StoreFilterInfo;
import com.tiptappay.store.app.service.app.CustomLoggerService;
import com.tiptappay.store.app.service.bundles.BundlesStoreService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class StoreFilter implements Filter {
    private final CustomLoggerService logger;
    private final BundlesStoreService bundlesStoreService;

    @Value("${app.environment}")
    private String appEnvironment;

    @Value("${stores.store.endpoint}")
    private String storeEndpoint;

    @Value("${stores.store.active}")
    private boolean isStoreActive;

    @Value("${stores.us-catholic.fresno-diocese.endpoint}")
    private String fresnoDioceseEndpoint;

    @Value("${stores.us-catholic.fresno-diocese.active}")
    private boolean isFresnoDioceseActive;

    @Value("${stores.salvation-army-us.endpoint}")
    private String salvationArmyUsEndpoint;

    @Value("${stores.salvation-army-us.active}")
    private boolean isSalvationArmyUsActive;

    @Value("${stores.mosques.endpoint}")
    private String mosquesEndpoint;

    @Value("${stores.mosques.active}")
    private boolean isMosquesActive;

    @Value("${stores.bundles.endpoint}")
    private String ttpBundlesEndpoint;

    @Value("${stores.bundles.active}")
    private boolean isTtpBundlesActive;

    @Value("${stores.benevity.endpoint}")
    private String benevityEndpoint;

    @Value("${stores.benevity.active}")
    private boolean isBenevityActive;

    @Value("${stores.benevity.fb.endpoint}")
    private String benevityFBEndpoint;

    @Value("${stores.benevity.fb.active}")
    private boolean isBenevityFBActive;

    @Value("${stores.benevity.education.endpoint}")
    private String benevityEducationEndpoint;

    @Value("${stores.benevity.education.active}")
    private boolean isBenevityEducationActive;

    @Value("${stores.canada.endpoint}")
    private String canadaStoreEndpoint;

    @Value("${stores.canada.active}")
    private boolean isCanadaStoreActive;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();
        StoreFilterInfo storeFilterInfo = isStoreActive(path);

        if (!storeFilterInfo.isActive()) {
            String redirectTo;

            if (storeFilterInfo.getRedirectTo() == null) {
                redirectTo = "https://welcome.tiptappay.com/order-form-is-closed";
            } else {
                redirectTo = storeFilterInfo.getRedirectTo();
            }

            httpResponse.sendRedirect(redirectTo);
            return;
        }

        if (storeFilterInfo.getRedirectTo() != null) {
            httpResponse.sendRedirect(storeFilterInfo.getRedirectTo());
            return;
        }

        chain.doFilter(request, response);
    }

    private StoreFilterInfo isStoreActive(String path) {
        // Bundles with Sales Rep
        String[] pathSplit = path.split("/");

        if (pathSplit.length >= 4) {
            if (path.split("/")[2].equals(ttpBundlesEndpoint) && (path.split("/")[3].equals("order"))) {
                String id = path.split("/")[1];

                if (!bundlesStoreService.isSalesRepIdExist(id)) {
                    logger.logWarn("Sales Rep ID " + id + " does not exist, redirecting to tiptap");
                    return new StoreFilterInfo(isTtpBundlesActive, "https://tiptappay.com/");
                }

                if (AppConstants.AppEnvironmentOptions.DEVELOPMENT.equals(appEnvironment)) { // Make active in DEV
                    return new StoreFilterInfo(true, null);
                }

                return new StoreFilterInfo(isTtpBundlesActive, null);
            }
        }

        if (AppConstants.AppEnvironmentOptions.DEVELOPMENT.equals(appEnvironment)) { // Make all other stores active in DEV
            return new StoreFilterInfo(true, null);
        }

        if (path.startsWith("/" + ttpBundlesEndpoint)) {
            return new StoreFilterInfo(isTtpBundlesActive, null);
        }

        if (path.startsWith("/" + benevityEndpoint)) {
            return new StoreFilterInfo(isBenevityActive, null);
        }

        if (path.startsWith("/" + benevityFBEndpoint)) {
            return new StoreFilterInfo(isBenevityFBActive, null);
        }

        if (path.startsWith("/" + benevityEducationEndpoint)) {
            return new StoreFilterInfo(isBenevityEducationActive, null);
        }

        if (path.startsWith("/" + storeEndpoint)) {
            return new StoreFilterInfo(isStoreActive, null);
        }

        if (path.startsWith("/" + salvationArmyUsEndpoint)) {
            return new StoreFilterInfo(isSalvationArmyUsActive, null);
        }

        if (path.startsWith("/" + fresnoDioceseEndpoint)) {
            return new StoreFilterInfo(isFresnoDioceseActive, null);
        }

        if (path.startsWith("/" + mosquesEndpoint)) {
            return new StoreFilterInfo(isMosquesActive, null);
        }

        if (path.startsWith("/" + canadaStoreEndpoint)) {
            return new StoreFilterInfo(isCanadaStoreActive, null);
        }

        return new StoreFilterInfo(true, null);
    }

    @Override
    public void destroy() {
    }
}
