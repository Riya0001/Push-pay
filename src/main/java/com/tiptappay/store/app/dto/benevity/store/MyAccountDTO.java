package com.tiptappay.store.app.dto.benevity.store;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MyAccountDTO {
    private String email;
    private String password;
    private String passwordConfirm;
}
