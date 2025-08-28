package com.tiptappay.store.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.tiptappay.store.app.constant.AppConstants.AppMessages.ERROR_ADDRESS_ISSUE;
import static com.tiptappay.store.app.constant.AppConstants.AppMessages.ERROR_INVALID_CAMPAIGN_START_DATE;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EXCEPTION_NETSUITE_QUOTE_REQUEST(1001, ERROR_ADDRESS_ISSUE, "Exception occurred while sending quote request to Netsuite."),
    INVALID_ADDRESS_ENTRY(1002, ERROR_ADDRESS_ISSUE, "Invalid address entry."),
    INVALID_CAMPAIGN_START_DATE(1004, ERROR_INVALID_CAMPAIGN_START_DATE, "Invalid campaign start date."),
    NETSUITE_INVALID_FIELD_ISSUE(1005, ERROR_ADDRESS_ISSUE, "Netsuite invalid field.");

    private final int code;
    private final String userMessage;
    private final String serverMessage;
}
