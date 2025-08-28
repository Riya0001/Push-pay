package com.tiptappay.store.app.service.restlet;

import com.tiptappay.store.app.dto.restlet.RestletCustomerResp;
import com.tiptappay.store.app.dto.restlet.RestletSalesOrderResp;
import com.tiptappay.store.app.service.oauth.OauthServices;

import com.tiptappay.store.app.util.DataUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestletQueryService {
    private final OauthServices oauthService;

    public RestletCustomerResp getNetsuiteCustomerByInternalId(String id, boolean includeSalesOrders, boolean includeInvoices) {
        Map<String, String> params = new HashMap<>();
        params.put("custpage_id", id);

        return getNetsuiteCustomer(params, includeSalesOrders, includeInvoices);
    }

    public RestletCustomerResp getNetsuiteCustomerByCustomerNumber(String id, boolean includeSalesOrders, boolean includeInvoices) {
        Map<String, String> params = new HashMap<>();
        params.put("custpage_number", id);

        return getNetsuiteCustomer(params, includeSalesOrders, includeInvoices);
    }

    private RestletCustomerResp getNetsuiteCustomer(Map<String, String> params, boolean includeSalesOrders, boolean includeInvoices) {
        // Add key-value pairs to the Map
        params.put("custpage_type", "customer");

        if (includeSalesOrders) {
            params.put("custpage_includesalesorders", "T");
        }

        if (includeInvoices) {
            params.put("custpage_includeinvoices", "T");
        }

        String responseBody = oauthService.runApiRequest("GET", "customscript_bf_queryendpoint_rl", params, null);
        RestletCustomerResp resp = DataUtils.convertToObject(responseBody, RestletCustomerResp.class);
        log.info("RestletCustomerService.getNetsuiteCustomer > {}", resp != null ? resp.toString() : "RestletCustomerResp : null");

        return resp;
    }

    public RestletSalesOrderResp getSalesOrderData(String salesOrderInternalId, boolean includePDF) {
        // Add key-value pairs to the Map
        Map<String, String> params = new HashMap<>();
        params.put("custpage_type", "salesorder");
        params.put("custpage_id", salesOrderInternalId);

        if (includePDF) {
            params.put("custpage_pdf", "T");
        }

        String responseBody = oauthService.runApiRequest("GET", "customscript_bf_queryendpoint_rl", params, null);
        RestletSalesOrderResp resp = DataUtils.convertToObject(responseBody, RestletSalesOrderResp.class);
        log.info("RestletCustomerService.getSalesOrderData > {}", resp != null ? resp.toString() : "RestletSalesOrderResp : null");

        return resp;
    }
}