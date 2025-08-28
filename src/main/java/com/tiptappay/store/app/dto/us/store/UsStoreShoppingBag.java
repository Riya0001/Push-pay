package com.tiptappay.store.app.dto.us.store;

import com.tiptappay.store.app.util.FormatCents;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsStoreShoppingBag implements Serializable {
    private int oneTimeFeeInCents;
    private int rentalFeeInCents;
    private int taxAmountInCents;
    private int serviceFeeAmountInCents = 9900; // Fixed price service fee
    private int shippingAmountInCents;
    private int miniClipKitQty;
    private int device5DollarQty;
    private int device10DollarQty;
    private int device20DollarQty;

    public int totalNumberOfProducts() {
        return miniClipKitQty + device5DollarQty + device10DollarQty + device20DollarQty;
    }

    public int totalRentalFeeInCents() {
        return (device5DollarQty + device10DollarQty + device20DollarQty) * rentalFeeInCents;
    }

    public String totalRentalFeeFormatted() {
        return FormatCents.formatCents(totalRentalFeeInCents());
    }

    public String taxAmountInCentsFormatted() {
        return FormatCents.formatCents(taxAmountInCents);
    }

    public String shippingAmountInCentsFormatted() {
        return FormatCents.formatCents(shippingAmountInCents);
    }

    public String serviceAmountInCentsFormatted() {
        return FormatCents.formatCents(serviceFeeAmountInCents);
    }

    public int totalOneTimeFeeInCents() {
        return miniClipKitQty * oneTimeFeeInCents;
    }

    public String totalOneTimeFeeFormatted() {
        return FormatCents.formatCents(totalOneTimeFeeInCents());
    }

    public int totalAmount() {
        return totalOneTimeFeeInCents() + totalRentalFeeInCents() + taxAmountInCents + shippingAmountInCents + serviceFeeAmountInCents;
    }

    public String totalAmountFormatted() {
        return FormatCents.formatCents(totalAmount());
    }

    public int miniClipKitTotalInCents() {
        return miniClipKitQty * oneTimeFeeInCents;
    }

    public String miniClipKitTotalFormatted() {
        return FormatCents.formatCents(miniClipKitTotalInCents());
    }

    public int device5DollarTotalInCents() {
        return device5DollarQty * rentalFeeInCents;
    }

    public String device5DollarTotalFormatted() {
        return FormatCents.formatCents(device5DollarTotalInCents());
    }

    public int device10DollarTotalInCents() {
        return device10DollarQty * rentalFeeInCents;
    }

    public String device10DollarTotalFormatted() {
        return FormatCents.formatCents(device10DollarTotalInCents());
    }

    public int device20DollarTotalInCents() {
        return device20DollarQty * rentalFeeInCents;
    }

    public String device20DollarTotalFormatted() {
        return FormatCents.formatCents(device20DollarTotalInCents());
    }

    @Override
    public String toString() {
        return "UsStoreShoppingBag{" +
                "oneTimeFeeInCents=" + oneTimeFeeInCents +
                ", rentalFeeInCents=" + rentalFeeInCents +
                ", taxAmountInCents=" + taxAmountInCents +
                ", shippingAmountInCents=" + shippingAmountInCents +
                ", miniClipKitQty=" + miniClipKitQty +
                ", device5DollarQty=" + device5DollarQty +
                ", device10DollarQty=" + device10DollarQty +
                ", device20DollarQty=" + device20DollarQty +
                '}';
    }
}
