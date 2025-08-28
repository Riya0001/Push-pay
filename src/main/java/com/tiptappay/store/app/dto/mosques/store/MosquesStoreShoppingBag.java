package com.tiptappay.store.app.dto.mosques.store;

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
public class MosquesStoreShoppingBag implements Serializable {
    private int oneTimeFeeInCents = StoreConstants.MosquesStore.FEE_ONE_TIME_IN_CENTS;
    private int rentalFeeInCents = StoreConstants.MosquesStore.FEE_RENTAL_IN_CENTS;
    private int taxAmountInCents;
    private int serviceFeeAmountInCents = StoreConstants.MosquesStore.FEE_SETUP_IN_CENTS;
    private int shippingAmountInCents;
    private int floorStandQty;
    private int device5DollarQty;
    private int device10DollarQty;
    private int device20DollarQty;

    public int totalNumberOfProducts() {
        return floorStandQty + device5DollarQty + device10DollarQty + device20DollarQty;
    }

    public int totalRentalFeeInCents() {
        return ((device5DollarQty + device10DollarQty + device20DollarQty) * rentalFeeInCents);
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
        return floorStandQty * oneTimeFeeInCents;
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

    public int floorStandTotalInCents() {
        return floorStandQty * oneTimeFeeInCents;
    }

    public String floorStandTotalFormatted() {
        return FormatCents.formatCents(floorStandTotalInCents());
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
        return "MosquesStoreShoppingBag{" +
                "oneTimeFeeInCents=" + oneTimeFeeInCents +
                ", rentalFeeInCents=" + rentalFeeInCents +
                ", taxAmountInCents=" + taxAmountInCents +
                ", serviceFeeAmountInCents=" + serviceFeeAmountInCents +
                ", shippingAmountInCents=" + shippingAmountInCents +
                ", floorStandQty=" + floorStandQty +
                ", device5DollarQty=" + device5DollarQty +
                ", device10DollarQty=" + device10DollarQty +
                ", device20DollarQty=" + device20DollarQty +
                '}';
    }
}
