package com.tiptappay.store.app.dto.restlet;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RestletCustomerReqParams {
    private String custPageType;
    private String custPageNumber;
    private String custPageId;
    private boolean custPageIncludeInvoices;
}
