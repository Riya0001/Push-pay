package com.tiptappay.store.app.service;
import com.tiptappay.store.app.model.ResponsePayment;
import com.tiptappay.store.app.model.CustomHttpResponse;
import com.tiptappay.store.app.service.CustomHttpService;
import com.tiptappay.store.app.service.app.AppService;
import com.tiptappay.store.app.service.saus.SaUsStoreService;
import com.tiptappay.store.app.util.DataUtils;

import java.util.Map;
import java.util.HashMap;

import static com.tiptappay.store.app.constant.AppConstants.HttpConstants.HTTP_HEADER_NAME_CONTENT_TYPE;
import static com.tiptappay.store.app.constant.AppConstants.HttpConstants.HTTP_HEADER_NAME_AUTHORIZATION;
import static com.tiptappay.store.app.constant.AppConstants.HttpConstants.HTTP_HEADER_VALUE_CONTENT_TYPE_JSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiptappay.store.app.constant.AppConstants;
import com.tiptappay.store.app.constant.StoreConstants;
import com.tiptappay.store.app.dto.mosques.store.MosquesStoreShoppingBag;
import com.tiptappay.store.app.dto.saus.store.SaUsStoreShoppingBag;
import com.tiptappay.store.app.dto.canada.CanadaStoreShoppingBag;
import com.tiptappay.store.app.dto.us.store.UsStoreShoppingBag;
import com.tiptappay.store.app.model.*;
import com.tiptappay.store.app.model.payload.Payload;
import com.tiptappay.store.app.util.DataUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.tiptappay.store.app.constant.AppConstants.CookiesConstants.*;
import static com.tiptappay.store.app.constant.AppConstants.NetSuiteActions.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreService {

    private final OauthService oauthService;
    private final DataProcessorService dataProcessorService;
    private final CookieService cookieService;
    private final PayloadService payloadService;
    private final AppService appService; // ✅ Add this
    private final CustomHttpService customHttpService;

    @Value("${app.developer.insights.enabled}")
    private boolean developerInsightsEnabled;

    public QuoteReqRes sendQuoteRequestToNetSuite(HttpServletResponse response,
                                                  Object mosquesStoreShoppingBag,
                                                  ContactAndShipping contactAndShipping,
                                                  String storeId) {
        QuoteReqRes quoteReqRes = new QuoteReqRes();

        Payload payload = payloadService.preparePayload(storeId,
                mosquesStoreShoppingBag,
                contactAndShipping,
                null,
                ACTION_QUOTE_TAX_AND_SHIPPING);

        quoteReqRes.setRequest(payload);

        String jsonPayload = DataUtils.convertToJsonString(payload);
        log.info("Request for quoteTaxAndShipping : {}", jsonPayload);

        try {
            CustomHttpResponse customHttpResponse = oauthService.runApiRequest(jsonPayload);

            ResponseQuote responseQuote = dataProcessorService.handleQuoteResponse(customHttpResponse.getResponseBody());

            quoteReqRes.setResponse(responseQuote);

            if (developerInsightsEnabled) {
                setCookieNCQ(response, quoteReqRes, storeId);
            }

        } catch (Exception exception) {
            log.error(AppConstants.AppMessages.ERROR_SENDING_PAYLOAD + " : {}", exception.getMessage());
        }

        return quoteReqRes;
    }

    public PaymentReqRes sendRequestToNetSuite(HttpServletResponse response,
                                               Object storeShoppingBag,
                                               ContactAndShipping contactAndShipping,
                                               Payment payment,
                                               String storeId) {
        PaymentReqRes paymentReqRes = new PaymentReqRes();

        String action = ACTION_COMPLETE_ORDER_WITH_PAYMENT;
        Payload payload = SaUsStoreService.preparePayload(storeId, storeShoppingBag, contactAndShipping, payment, action);
        paymentReqRes.setRequest(payload);

        String jsonPayload = DataUtils.convertToJsonString(payload);
        String jsonPayloadToLog = maskPaymentInformation(jsonPayload);

        log.info("Request for {} : {}", action, jsonPayloadToLog);
        log.info("Sending request with form ID: {}", payload.getHeader().getNsSOFormInternalID());
        log.info("Full payload: {}", jsonPayload);
        System.out.println("▶️ Request for action: " + action);
        System.out.println("▶️ Masked Payload: " + jsonPayloadToLog);
        System.out.println("▶️ Sending request with form ID: " + payload.getHeader().getNsSOFormInternalID());
        System.out.println("▶️ Full Payload: " + jsonPayload);
        try {
            CustomHttpResponse customHttpResponse = oauthService.runApiRequest(jsonPayload);
            ResponsePayment responsePayment = dataProcessorService.handleResponse(customHttpResponse.getResponseBody());

            paymentReqRes.setResponse(responsePayment);

            if (developerInsightsEnabled) {
                setCookieNCP(response, paymentReqRes, storeId);
            }

        } catch (Exception exception) {
            log.error(AppConstants.AppMessages.ERROR_SENDING_PAYLOAD + " : {}", exception.getMessage());
        }
        return paymentReqRes;
    }
    public PaymentReqRes sendRequestToNetSuiteSACA(HttpServletResponse response,
                                               Object storeShoppingBag,
                                               ContactAndShipping contactAndShipping,
                                               Payment payment,
                                               String storeId) {
        PaymentReqRes paymentReqRes = new PaymentReqRes();

        String action = ACTION_COMPLETE_ORDER_WITH_PAYMENT;
        Payload payload = payloadService.preparePayload(storeId, storeShoppingBag, contactAndShipping, payment, action);
        paymentReqRes.setRequest(payload);

        String jsonPayload = DataUtils.convertToJsonString(payload);
        String jsonPayloadToLog = maskPaymentInformation(jsonPayload);

        log.info("Request for {} : {}", action, jsonPayloadToLog);
        log.info("Sending request with form ID: {}", payload.getHeader().getNsSOFormInternalID());
        log.info("Full payload: {}", jsonPayload);
        System.out.println("▶️ Request for action: " + action);
        System.out.println("▶️ Masked Payload: " + jsonPayloadToLog);
        System.out.println("▶️ Sending request with form ID: " + payload.getHeader().getNsSOFormInternalID());
        System.out.println("▶️ Full Payload: " + jsonPayload);
        try {
            CustomHttpResponse customHttpResponse = oauthService.runApiRequest(jsonPayload);
            ResponsePayment responsePayment = dataProcessorService.handleResponse(customHttpResponse.getResponseBody());

            paymentReqRes.setResponse(responsePayment);

            if (developerInsightsEnabled) {
                setCookieNCP(response, paymentReqRes, storeId);
            }

        } catch (Exception exception) {
            log.error(AppConstants.AppMessages.ERROR_SENDING_PAYLOAD + " : {}", exception.getMessage());
        }
        return paymentReqRes;
    }

    public Object handleCookieSB(HttpServletRequest request, HttpServletResponse response, String storeId) {
        String cookieSB = cookieService.readCookie(request, COOKIE_SHOPPING_BAG + "_" + storeId.toLowerCase());

        switch (storeId) {
            case StoreConstants.USStore.STORE_ID -> {
                UsStoreShoppingBag usStoreShoppingBag;

                if (cookieSB != null) {
                    try {
                        usStoreShoppingBag = DataUtils.convertToObject(cookieSB, UsStoreShoppingBag.class);
                        if (usStoreShoppingBag == null) {

                            usStoreShoppingBag = new UsStoreShoppingBag();
                            usStoreShoppingBag.setOneTimeFeeInCents(StoreConstants.USStore.FEE_ONE_TIME_IN_CENTS);
                            usStoreShoppingBag.setRentalFeeInCents(StoreConstants.USStore.FEE_RENTAL_IN_CENTS);

                            setCookieSB(response, usStoreShoppingBag, storeId);

                        }
                    } catch (Exception exception) {
                        log.error("StoreService.handleCookieSB > Cookie " + COOKIE_SHOPPING_BAG + " corrupted : {}", exception.getMessage());
                        usStoreShoppingBag = null;
                        cookieService.deleteCookie(response, COOKIE_SHOPPING_BAG);
                    }
                } else {
                    usStoreShoppingBag = new UsStoreShoppingBag();
                    usStoreShoppingBag.setOneTimeFeeInCents(StoreConstants.USStore.FEE_ONE_TIME_IN_CENTS);
                    usStoreShoppingBag.setRentalFeeInCents(StoreConstants.USStore.FEE_RENTAL_IN_CENTS);

                    setCookieSB(response, usStoreShoppingBag, storeId);
                }

                return usStoreShoppingBag;
            }
            case StoreConstants.MosquesStore.STORE_ID -> {
                MosquesStoreShoppingBag mosquesStoreShoppingBag;

                if (cookieSB != null) {
                    try {
                        mosquesStoreShoppingBag = DataUtils.convertToObject(cookieSB, MosquesStoreShoppingBag.class);
                        if (mosquesStoreShoppingBag == null) {

                            mosquesStoreShoppingBag = new MosquesStoreShoppingBag();
                            mosquesStoreShoppingBag.setOneTimeFeeInCents(StoreConstants.MosquesStore.FEE_ONE_TIME_IN_CENTS);
                            mosquesStoreShoppingBag.setRentalFeeInCents(StoreConstants.MosquesStore.FEE_RENTAL_IN_CENTS);

                            setCookieSB(response, mosquesStoreShoppingBag, storeId);

                        }
                    } catch (Exception exception) {
                        // Cookie corrupted
                        log.error("StoreService.handleCookieSB > Cookie "
                                        + COOKIE_SHOPPING_BAG + "_{} corrupted : {}", storeId.toLowerCase(),
                                exception.getMessage());
                        mosquesStoreShoppingBag = null;
                        cookieService.deleteCookie(response, COOKIE_SHOPPING_BAG);
                    }
                } else {
                    mosquesStoreShoppingBag = new MosquesStoreShoppingBag();
                    mosquesStoreShoppingBag.setOneTimeFeeInCents(StoreConstants.MosquesStore.FEE_ONE_TIME_IN_CENTS);
                    mosquesStoreShoppingBag.setRentalFeeInCents(StoreConstants.MosquesStore.FEE_RENTAL_IN_CENTS);

                    setCookieSB(response, mosquesStoreShoppingBag, storeId);
                }

                return mosquesStoreShoppingBag;
            }
            case StoreConstants.CanadaStore.STORE_ID -> {
                CanadaStoreShoppingBag canadaStoreShoppingBag;

                String cookieKey = COOKIE_SHOPPING_BAG + "_" + storeId.toLowerCase();
                if (cookieSB != null) {
                    try {
                        canadaStoreShoppingBag = DataUtils.convertToObject(cookieSB, CanadaStoreShoppingBag.class);
                        if (canadaStoreShoppingBag == null) {
                            canadaStoreShoppingBag = new CanadaStoreShoppingBag();
                            canadaStoreShoppingBag.setOneTimeFeeInCents(StoreConstants.CanadaStore.FEE_ONE_TIME_IN_CENTS);
                            canadaStoreShoppingBag.setRentalFeeInCents(StoreConstants.CanadaStore.FEE_RENTAL_IN_CENTS);
                            setCookieSB(response, canadaStoreShoppingBag, storeId);
                        }
                    } catch (Exception exception) {
                        log.error("StoreService.handleCookieSB > Cookie {} corrupted : {}", cookieKey, exception.getMessage());
                        canadaStoreShoppingBag = null;
                        cookieService.deleteCookie(response, cookieKey); // ✅ fix: delete the correct cookie
                    }
                } else {
                    canadaStoreShoppingBag = new CanadaStoreShoppingBag();
                    canadaStoreShoppingBag.setOneTimeFeeInCents(StoreConstants.CanadaStore.FEE_ONE_TIME_IN_CENTS);
                    canadaStoreShoppingBag.setRentalFeeInCents(StoreConstants.CanadaStore.FEE_RENTAL_IN_CENTS);
                    setCookieSB(response, canadaStoreShoppingBag, storeId);
                }

                return canadaStoreShoppingBag;
            }

            case StoreConstants.SaUsStore.STORE_ID -> {
                SaUsStoreShoppingBag saUsStoreShoppingBag;

                if (cookieSB != null) {
                    try {
                        saUsStoreShoppingBag = DataUtils.convertToObject(cookieSB, SaUsStoreShoppingBag.class);
                        if (saUsStoreShoppingBag == null) {

                            saUsStoreShoppingBag = new SaUsStoreShoppingBag();
                            saUsStoreShoppingBag.setOneTimeFeeInCents(StoreConstants.MosquesStore.FEE_ONE_TIME_IN_CENTS);
                            saUsStoreShoppingBag.setRentalFeeInCents(StoreConstants.MosquesStore.FEE_RENTAL_IN_CENTS);

                            setCookieSB(response, saUsStoreShoppingBag, storeId);

                        }
                    } catch (Exception exception) {
                        // Cookie corrupted
                        log.error("StoreService.handleCookieSB > Cookie " + COOKIE_SHOPPING_BAG
                                + "_{} corrupted : {}", storeId.toLowerCase(), exception.getMessage());
                        saUsStoreShoppingBag = null;
                        cookieService.deleteCookie(response, COOKIE_SHOPPING_BAG);
                    }
                } else {
                    saUsStoreShoppingBag = new SaUsStoreShoppingBag();
                    saUsStoreShoppingBag.setOneTimeFeeInCents(StoreConstants.SaUsStore.FEE_ONE_TIME_IN_CENTS);
                    saUsStoreShoppingBag.setRentalFeeInCents(StoreConstants.SaUsStore.FEE_RENTAL_IN_CENTS);

                    setCookieSB(response, saUsStoreShoppingBag, storeId);
                }

                return saUsStoreShoppingBag;
            }
        }

        return null;
    }

    public ContactAndShipping handleCookieCANDS(HttpServletRequest request, HttpServletResponse response, String storeId) {
        String cookieCANDS = cookieService.readCookie(request, COOKIE_CONTACT_SHIPPING + "_" + storeId.toLowerCase());
        ContactAndShipping contactAndShipping = null;

        if (cookieCANDS != null) {
            try {
                contactAndShipping = DataUtils.convertToObject(cookieCANDS, ContactAndShipping.class);
            } catch (Exception exception) {
                // Cookie corrupted
                log.error("UsStoreService.handleCookieCANDS > Cookie " + COOKIE_CONTACT_SHIPPING
                        + "_{} corrupted : {}", storeId.toLowerCase(), exception.getMessage());
                cookieService.deleteCookie(response, COOKIE_CONTACT_SHIPPING + "_" + storeId.toLowerCase()); // ✅

            }
        }

        return contactAndShipping;
    }

    public ResponseQuoteCookie handleCookieTSQ(HttpServletRequest request,
                                               HttpServletResponse response,
                                               Object shoppingBag,
                                               ContactAndShipping contactAndShipping,
                                               String storeId) {
        String cookieTSQ = cookieService.readCookie(request, COOKIE_TAX_SHIPPING_QUOTE + "_" + storeId.toLowerCase());
        ResponseQuoteCookie responseQuoteCookie = null;

        if (cookieTSQ != null) {
            try {
                responseQuoteCookie = DataUtils.convertToObject(cookieTSQ, ResponseQuoteCookie.class);
                if (responseQuoteCookie != null) {
                    if (!responseQuoteCookie.isValid(shoppingBag, contactAndShipping)) {
                        log.info("UsStoreService.handleCookieTSQ > Cookie " + COOKIE_TAX_SHIPPING_QUOTE
                                + "_{} is invalid, tax & shipping will be recalculated", storeId.toLowerCase());

                        // Calculate Tax and Shipping
                        QuoteReqRes quoteReqRes = sendQuoteRequestToNetSuite(response, shoppingBag, contactAndShipping, storeId);
                        if (quoteReqRes.getResponse() != null) {
                            responseQuoteCookie = new ResponseQuoteCookie();
                            responseQuoteCookie.setQuote(quoteReqRes.getResponse());
                            responseQuoteCookie.setToken(shoppingBag, contactAndShipping);
                        }
                    } else {
                        log.info("UsStoreService.handleCookieTSQ > Cookie " + COOKIE_TAX_SHIPPING_QUOTE
                                + "_{} is valid, no need to recalculate tax & shipping", storeId.toLowerCase());
                    }
                }

            } catch (Exception exception) {
                // Cookie corrupted
                log.error("UsStoreService.handleCookieTSQ > Cookie named " + COOKIE_TAX_SHIPPING_QUOTE
                        + "_{} corrupted : {}", storeId.toLowerCase(), exception.getMessage());
                cookieService.deleteCookie(response, COOKIE_TAX_SHIPPING_QUOTE);
            }
        } else {
            // Calculate Tax and Shipping
            log.info("StoreService.handleCookieTSQ > Quote unavailable, tax & shipping will be calculated");

            QuoteReqRes quoteReqRes = sendQuoteRequestToNetSuite(response, shoppingBag, contactAndShipping, storeId);
            if (quoteReqRes.getResponse() != null) {
                responseQuoteCookie = new ResponseQuoteCookie();
                responseQuoteCookie.setQuote(quoteReqRes.getResponse());
                responseQuoteCookie.setToken(shoppingBag, contactAndShipping);
            }
        }

        return responseQuoteCookie;
    }

    public QuoteReqRes handleCookieNCQ(HttpServletRequest request, HttpServletResponse response, String storeId) {
        String cookieKey = COOKIE_NETSUITE_COMMUNICATE_QUOTE + "_" + storeId.toLowerCase();
        String cookieNCQ = cookieService.readCookie(request, cookieKey);
        QuoteReqRes quoteReqRes = null;

        System.out.println("handleCookieNCQ > Reading cookie: " + cookieKey);

        if (cookieNCQ != null) {
            try {
                String decoded;
                try {
                    // Attempt Base64 decode
                    System.out.println("handleCookieNCQ > Attempting Base64 decode...");
                    decoded = new String(Base64.getDecoder().decode(cookieNCQ), StandardCharsets.UTF_8);
                    System.out.println("handleCookieNCQ > Successfully Base64-decoded cookie value.");
                } catch (IllegalArgumentException e) {
                    // Not Base64? Use raw JSON
                    System.out.println("handleCookieNCQ > Cookie value is not Base64 encoded. Using raw value.");
                    decoded = cookieNCQ;
                }

                System.out.println("handleCookieNCQ > Decoded JSON string: " + decoded);

                // Convert JSON to QuoteReqRes
                quoteReqRes = new ObjectMapper().readValue(decoded, QuoteReqRes.class);
                System.out.println("handleCookieNCQ > Successfully parsed QuoteReqRes object from cookie.");

            } catch (Exception exception) {
                System.out.println("handleCookieNCQ > Failed to parse cookie '" + cookieKey + "': " + exception.getMessage());
                cookieService.deleteCookie(response, cookieKey);
                System.out.println("handleCookieNCQ > Corrupted cookie deleted: " + cookieKey);
            }
        } else {
            System.out.println("handleCookieNCQ > No cookie found with key: " + cookieKey);
        }

        return quoteReqRes;
    }

    public PaymentReqRes handleCookieNCP(HttpServletRequest request, HttpServletResponse response, String storeId) {
        String cookieKey = COOKIE_NETSUITE_COMMUNICATE_PAYMENT + "_" + storeId.toLowerCase();
        String cookieNCP = cookieService.readCookie(request, cookieKey);
        PaymentReqRes paymentReqRes = null;

        if (cookieNCP != null) {
            try {
                String decoded;
                try {
                    decoded = new String(Base64.getDecoder().decode(cookieNCP), StandardCharsets.UTF_8);
                    log.info("handleCookieNCP > Successfully Base64-decoded cookie for storeId: {}", storeId);
                } catch (IllegalArgumentException e) {
                    log.warn("handleCookieNCP > Cookie not Base64, using raw value for storeId: {}", storeId);
                    decoded = cookieNCP;
                }

                paymentReqRes = new ObjectMapper().readValue(decoded, PaymentReqRes.class);
                log.info("handleCookieNCP > Parsed PaymentReqRes successfully for storeId: {}", storeId);

            } catch (Exception exception) {
                log.error("handleCookieNCP > Failed to parse PaymentReqRes cookie '{}' : {}", cookieKey, exception.getMessage());
                cookieService.deleteCookie(response, cookieKey);
            }
        } else {
            log.warn("handleCookieNCP > No cookie found with key: {}", cookieKey);
        }

        return paymentReqRes;
    }


    public void setCookieSB(HttpServletResponse response, Object shoppingBag, String storeId) {
        String json = DataUtils.convertToJsonString(shoppingBag);
        cookieService.writeCookie(response, COOKIE_SHOPPING_BAG + "_" + storeId.toLowerCase(), json);
    }

    public void setCookieCANDS(HttpServletResponse response, ContactAndShipping contactAndShipping, String storeId) {
        String json = DataUtils.convertToJsonString(contactAndShipping);
        cookieService.writeCookie(response, COOKIE_CONTACT_SHIPPING + "_" + storeId.toLowerCase(), json);
    }

    public void setCookieTSQ(HttpServletResponse response, ResponseQuoteCookie responseQuoteCookie, String storeId) {
        String json = DataUtils.convertToJsonString(responseQuoteCookie);
        cookieService.writeCookie(response, COOKIE_TAX_SHIPPING_QUOTE + "_" + storeId.toLowerCase(), json);
    }

    public void setCookieNCQ(HttpServletResponse response, QuoteReqRes quoteReqRes, String storeId) {
        try {
            String json = new ObjectMapper().writeValueAsString(quoteReqRes);
            String encoded = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
            cookieService.writeCookie(response, COOKIE_NETSUITE_COMMUNICATE_QUOTE + "_" + storeId.toLowerCase(), encoded);
        } catch (Exception e) {
            log.error("setCookieNCQ > Failed to encode and store cookie: {}", e.getMessage());
        }
    }


    public void setCookieNCP(HttpServletResponse response, PaymentReqRes paymentReqRes, String storeId) {
        try {
            log.info("setCookieNCP > Trimming and storing response to cookie for storeId: {}", storeId);

            PaymentReqRes trimmed = new PaymentReqRes();
            trimmed.setResponse(paymentReqRes.getResponse());

            String json = new ObjectMapper().writeValueAsString(trimmed);
            String encoded = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));

            log.info("setCookieNCP > Encoded cookie length: {} bytes", encoded.length());
            cookieService.writeCookie(response, COOKIE_NETSUITE_COMMUNICATE_PAYMENT + "_" + storeId.toLowerCase(), encoded);
            log.info("setCookieNCP > Successfully stored cookie for NCP.");

        } catch (Exception e) {
            log.error("setCookieNCP > Failed to encode and store cookie for NCP: {}", e.getMessage(), e);
        }
    }



    public boolean isAllCookiesAvailable(HttpServletRequest request, String storeId) {
        String cookieSB = cookieService.readCookie(request, COOKIE_SHOPPING_BAG + "_" + storeId.toLowerCase());
        String cookieCANDS = cookieService.readCookie(request, COOKIE_CONTACT_SHIPPING + "_" + storeId.toLowerCase());
        String cookieTSQ = cookieService.readCookie(request, COOKIE_TAX_SHIPPING_QUOTE + "_" + storeId.toLowerCase());

        return cookieSB != null && cookieCANDS != null && cookieTSQ != null;
    }

    public void deleteAllCookiesByStoreId(HttpServletResponse response, String storeId) {
        cookieService.deleteCookie(response, COOKIE_SHOPPING_BAG + "_" + storeId.toLowerCase());
        cookieService.deleteCookie(response, COOKIE_CONTACT_SHIPPING + "_" + storeId.toLowerCase());
        cookieService.deleteCookie(response, COOKIE_TAX_SHIPPING_QUOTE + "_" + storeId.toLowerCase());
        cookieService.deleteCookie(response, COOKIE_NETSUITE_COMMUNICATE_QUOTE + "_" + storeId.toLowerCase());
        cookieService.deleteCookie(response, COOKIE_NETSUITE_COMMUNICATE_PAYMENT + "_" + storeId.toLowerCase());
    }

    public void deleteAllCookies(HttpServletResponse response) {
        // Fresno Diocese Cookies
        cookieService.deleteCookie(response, COOKIE_SHOPPING_BAG + "_" + StoreConstants.USStore.STORE_ID.toLowerCase());
        cookieService.deleteCookie(response, COOKIE_CONTACT_SHIPPING + "_" + StoreConstants.USStore.STORE_ID.toLowerCase());
        cookieService.deleteCookie(response, COOKIE_TAX_SHIPPING_QUOTE + "_" + StoreConstants.USStore.STORE_ID.toLowerCase());
        cookieService.deleteCookie(response, COOKIE_NETSUITE_COMMUNICATE_QUOTE + "_" + StoreConstants.USStore.STORE_ID.toLowerCase());
        cookieService.deleteCookie(response, COOKIE_NETSUITE_COMMUNICATE_PAYMENT + "_" + StoreConstants.USStore.STORE_ID.toLowerCase());

        // Mosque Cookies
        cookieService.deleteCookie(response, COOKIE_SHOPPING_BAG + "_" + StoreConstants.MosquesStore.STORE_ID.toLowerCase());
        cookieService.deleteCookie(response, COOKIE_CONTACT_SHIPPING + "_" + StoreConstants.MosquesStore.STORE_ID.toLowerCase());
        cookieService.deleteCookie(response, COOKIE_TAX_SHIPPING_QUOTE + "_" + StoreConstants.MosquesStore.STORE_ID.toLowerCase());
        cookieService.deleteCookie(response, COOKIE_NETSUITE_COMMUNICATE_QUOTE + "_" + StoreConstants.MosquesStore.STORE_ID.toLowerCase());
        cookieService.deleteCookie(response, COOKIE_NETSUITE_COMMUNICATE_PAYMENT + "_" + StoreConstants.MosquesStore.STORE_ID.toLowerCase());

        // Salvation Army US Cookies
        cookieService.deleteCookie(response, COOKIE_SHOPPING_BAG + "_" + StoreConstants.SaUsStore.STORE_ID.toLowerCase());
        cookieService.deleteCookie(response, COOKIE_CONTACT_SHIPPING + "_" + StoreConstants.SaUsStore.STORE_ID.toLowerCase());
        cookieService.deleteCookie(response, COOKIE_TAX_SHIPPING_QUOTE + "_" + StoreConstants.SaUsStore.STORE_ID.toLowerCase());
        cookieService.deleteCookie(response, COOKIE_NETSUITE_COMMUNICATE_QUOTE + "_" + StoreConstants.SaUsStore.STORE_ID.toLowerCase());
        cookieService.deleteCookie(response, COOKIE_NETSUITE_COMMUNICATE_PAYMENT + "_" + StoreConstants.SaUsStore.STORE_ID.toLowerCase());
    }

    public String generatePrettyJson(Object object) {
        String prettyJson = "{}";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception exception) {
            log.error("StoreService.generatePrettyJson > Cannot pretty serialize response : {}", exception.getMessage());
        }
        return prettyJson;
    }
    public CustomHttpResponse createAthenaCampaign(ResponsePayment responsePayment) {
        String url = appService.getBaseUrl() + "/" + appService.getAthenaApiPrefix() + "/campaigns";

        Map<String, String> headers = Map.of(
                HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
                HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + appService.getAthenaApiKey()
        );

        Map<String, Object> campaignPayload = new HashMap<>();
        campaignPayload.put("campaignName", responsePayment.getSoNumber());
        campaignPayload.put("customerId", responsePayment.getCustomerId());
        campaignPayload.put("orderId", responsePayment.getId());
        campaignPayload.put("description", "Order " + responsePayment.getSoNumber() + " created via Canada Store");
        campaignPayload.put("source", "canada-store");
        campaignPayload.put("status", "active");

        try {
            String jsonBody = DataUtils.convertToJsonString(campaignPayload);
            return customHttpService.postRequest(url, headers, jsonBody);
        } catch (Exception e) {
            log.error("StoreService.createAthenaCampaign > Failed to create campaign: {}", e.getMessage());
            return null;
        }
    }

    public String maskPaymentInformation(String jsonPayload) {
        if (jsonPayload == null || jsonPayload.isEmpty()) {
            return "{}";
        }

        String maskedJson = jsonPayload.replaceAll("(\"cardNumber\":\"\\d+)(\\d{4})\"", "\"************$2\"");
        maskedJson = maskedJson.replaceAll("(\"expirationDate\":\")[0-9]{2}/[0-9]{2}\"", "\"**/**\"");
        maskedJson = maskedJson.replaceAll("(\"cvv\":\")[0-9]+\"", "\"***\"");

        return maskedJson;
    }
}
