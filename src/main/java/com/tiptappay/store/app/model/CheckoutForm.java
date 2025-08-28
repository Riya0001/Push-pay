package com.tiptappay.store.app.model;

import com.tiptappay.store.app.validation.PoBoxValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutForm {
    // Billing Information

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 100, message = "Too long.")
    private String billingFirstName;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 100, message = "Too long.")
    private String billingLastName;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 200, message = "Too long.")
    @PoBoxValidation
    private String billingAddr1;

    @Size(max = 200, message = "Too long.")
    @PoBoxValidation
    private String billingAddr2;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 100, message = "Too long.")
    private String billingCity;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 100, message = "Too long.")
    private String billingStateUS;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 100, message = "Too long.")
    private String billingStateCA;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 100, message = "Too long.")
    private String billingCountry;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 5, message = "Too long.")
    @Pattern(regexp = "\\d{5}", message = "Format should be 99999")
    private String billingZip;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 7, message = "Too long.")
    @Pattern(regexp = "[A-Za-z]\\d[A-Za-z] \\d[A-Za-z]\\d", message = "Format should be A9A 9A9")
    private String billingPostalCode;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 16, message = "Too long.")
    private String cardNumber;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 2, message = "Too long.")
    private String expirationMonth;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 2, message = "Too long.")
    private String expirationYear;

    @NotBlank(message = "Cannot be empty!")
    @Size(max = 3, message = "Too long.")
    private String cvv;
}
