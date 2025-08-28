package com.tiptappay.store.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ResponseQuoteCookie {
    private boolean valid;

    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private ResponseQuote quote;

    public void setToken(Object storeShoppingBag, ContactAndShipping contactAndShipping) {
        this.token = storeShoppingBag.toString() + contactAndShipping.toShippingString();
    }

    public boolean isValid(Object storeShoppingBag, ContactAndShipping contactAndShipping) {
        this.valid = compareTokens(storeShoppingBag, contactAndShipping);
        return this.valid;
    }

    private boolean compareTokens(Object storeShoppingBag, ContactAndShipping contactAndShipping) {
        return this.token.equals(storeShoppingBag.toString() + contactAndShipping.toShippingString());
    }
}
