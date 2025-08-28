package com.tiptappay.store.app.dto.saus.store;

import com.tiptappay.store.app.constant.StoreConstants;
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
public class SaUsStoreShoppingBag implements Serializable {
    private int oneTimeFeeInCents = StoreConstants.SaUsStore.FEE_ONE_TIME_IN_CENTS;
    private int rentalFeeInCents = StoreConstants.SaUsStore.FEE_RENTAL_IN_CENTS;
    private int batteryRentalFeeInCents = StoreConstants.SaUsStore.FEE_RENTAL_IN_CENTS_2;
    private int taxAmountInCents;
    private int serviceFeeAmountInCents = 0;
    private int shippingAmountInCents;
    private int kettleDisplayQty;
    private int device5DollarQty;
    private int device10DollarQty;
    private int device20DollarQty;
    private int batteryForKettleDisplayQty;

    public int totalNumberOfProducts() {
        return kettleDisplayQty + device5DollarQty + device10DollarQty + device20DollarQty + batteryForKettleDisplayQty;
    }

    public int totalRentalFeeInCents() {
        return kettleDisplayQty * 23000;
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
        return kettleDisplayQty * oneTimeFeeInCents;
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

    public int kettleDisplayTotalInCents() {
        return kettleDisplayQty * oneTimeFeeInCents;
    }

    public String kettleDisplayTotalFormatted() {
        return FormatCents.formatCents(kettleDisplayTotalInCents());
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

    public int batteryForKettleDisplayTotalInCents() {
        return batteryForKettleDisplayQty * batteryRentalFeeInCents;
    }

    public String batteryForKettleDisplayTotalFormatted() {
        return FormatCents.formatCents(batteryForKettleDisplayTotalInCents());
    }

    @Override
    public String toString() {
        return "SaUsStoreShoppingBag{" +
                "oneTimeFeeInCents=" + oneTimeFeeInCents +
                ", rentalFeeInCents=" + rentalFeeInCents +
                ", batteryRentalFeeInCents=" + batteryRentalFeeInCents +
                ", taxAmountInCents=" + taxAmountInCents +
                ", serviceFeeAmountInCents=" + serviceFeeAmountInCents +
                ", shippingAmountInCents=" + shippingAmountInCents +
                ", kettleDisplayQty=" + kettleDisplayQty +
                ", device5DollarQty=" + device5DollarQty +
                ", device10DollarQty=" + device10DollarQty +
                ", device20DollarQty=" + device20DollarQty +
                ", batteryForKettleDisplayQty=" + batteryForKettleDisplayQty +
                '}';
    }
}
