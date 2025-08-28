package com.tiptappay.store.app.service;

import com.tiptappay.store.app.model.ResponsePayment;
import com.tiptappay.store.app.model.ResponseQuote;
import com.tiptappay.store.app.util.DataUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataProcessorService {

    public ResponsePayment handleResponse(String responseBody) {
        ResponsePayment responsePayment;

        if (responseBody == null || responseBody.isEmpty()) {
            responsePayment = new ResponsePayment(0, 0, false, new String[]{"Unexpected Error Occurred : SS"}, "", false, "");
        } else {
            try {
                responsePayment = DataUtils.convertToObject(responseBody, ResponsePayment.class);

                if (responsePayment == null) {
                    responsePayment = new ResponsePayment(0, 0, false, new String[]{"Unexpected Error Occurred : SS"}, "", false, "");
                }
            } catch (Exception exception) {
                responsePayment = new ResponsePayment(0, 0, false, new String[]{exception.getMessage()}, "", false, "");
            }
        }

        return responsePayment;
    }

    public ResponseQuote handleQuoteResponse(String responseBody) {
        ResponseQuote response;

        if (responseBody == null || responseBody.isEmpty()) {
            response = new ResponseQuote(0, false, new String[]{"Unexpected Error Occurred : SS"}, 0, 0);
        } else {
            try {
                response = DataUtils.convertToObject(responseBody, ResponseQuote.class);

                if (response == null) {
                    response = new ResponseQuote(0, false, new String[]{"Unexpected Error Occurred : SS"}, 0, 0);
                }
            } catch (Exception exception) {
                response = new ResponseQuote(0, false, new String[]{exception.getMessage()}, 0, 0);
            }
        }

        return response;
    }
}
