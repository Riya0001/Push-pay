package com.tiptappay.store.app.dto.benevity.store;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BenevityCause {
    private String id;
    private String name;
    private String phone;
    private String logoUrl;
    private Address address;

    @Data
    public static class Address {
        private String street;
        private String city;
        private String state;
        private String zip;
        private String country;
    }
}
