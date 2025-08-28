package com.tiptappay.store.app.dto.canada;

import com.tiptappay.store.app.constant.StoreConstants;
import com.tiptappay.store.app.util.FormatCents;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class CanadaStoreShoppingBag implements Serializable {

    // Fees
    private int oneTimeFeeInCents = StoreConstants.CanadaStore.FEE_ONE_TIME_IN_CENTS;
    private int rentalFeeInCents = StoreConstants.CanadaStore.FEE_RENTAL_IN_CENTS;
    private int batteryRentalFeeInCents = StoreConstants.CanadaStore.FEE_RENTAL_IN_CENTS_2;

    // Totals
    private int taxAmountInCents;
    private int shippingAmountInCents;

    // Legacy / Desktop
    private int desktopDisplayQty;

    // Rental Device Quantities
    private int device5DollarQty;
    private int device10DollarQty;
    private int device20DollarQty;

    // 3-Device Display Quantities
    private int threeDeviceDisplayEnglishQuantity;
    private int threeDeviceDisplayFrenchQuantity;
    private int threeDeviceDisplayBilingualQuantity;

    // Single Curve 5dollar Display Quantities
    private int singleCurve5DollarEnglishQuantity;
    private int singleCurve5DollarFrenchQuantity;
    private int singleCurve5DollarBilingualQuantity;

    // Single Curve 10dollar Display Quantities
    private int singleCurve10DollarEnglishQuantity;
    private int singleCurve10DollarFrenchQuantity;
    private int singleCurve10DollarBilingualQuantity;

    // Single Curve 20dollar Display Quantities
    private int singleCurve20DollarEnglishQuantity;
    private int singleCurve20DollarFrenchQuantity;
    private int singleCurve20DollarBilingualQuantity;

    // Single Curve Display Quantities
    private int singleCurveEnglishQuantity;
    private int singleCurveFrenchQuantity;
    private int singleCurveBilingualQuantity;

    // --- Computed Values ---

    public int totalThreeDeviceDisplayQty() {
        return threeDeviceDisplayEnglishQuantity + threeDeviceDisplayFrenchQuantity + threeDeviceDisplayBilingualQuantity;
    }

    public int totalNumberOfProducts() {
        return desktopDisplayQty + device5DollarQty + device10DollarQty + device20DollarQty +
                totalThreeDeviceDisplayQty() +
                totalSingleCurveEnglishQty() +
                totalSingleCurveFrenchQty() +
                totalSingleCurveBilingualQty();
    }
    public int totalSingleCurveEnglishQty() {
        return singleCurve20DollarEnglishQuantity + singleCurve10DollarEnglishQuantity + singleCurve5DollarEnglishQuantity;
    }
    public int totalSingleCurveFrenchQty() {
        return singleCurve20DollarFrenchQuantity + singleCurve10DollarFrenchQuantity + singleCurve5DollarFrenchQuantity;
    }
    public int totalSingleCurveBilingualQty() {
        return singleCurve20DollarBilingualQuantity + singleCurve10DollarBilingualQuantity + singleCurve5DollarBilingualQuantity;
    }



    public int totalRentalFeeInCents() {
        return (device5DollarQty + device10DollarQty + device20DollarQty) * rentalFeeInCents;
    }

    public int totalOneTimeFeeInCents() {
        return desktopDisplayQty * oneTimeFeeInCents;
    }

    public int totalAmount() {
        return totalOneTimeFeeInCents() + totalRentalFeeInCents()
                + taxAmountInCents + shippingAmountInCents;
    }

    // --- Formatted Outputs ---

    public String totalRentalFeeFormatted() {
        return FormatCents.formatCents(totalRentalFeeInCents());
    }

    public String totalOneTimeFeeFormatted() {
        return FormatCents.formatCents(totalOneTimeFeeInCents());
    }

    public int totalAmountFormatted() {
        return (((threeDeviceDisplayBilingualQuantity+threeDeviceDisplayEnglishQuantity+threeDeviceDisplayFrenchQuantity)*300)+((singleCurveBilingualQuantity+singleCurveEnglishQuantity+singleCurveFrenchQuantity)*150));
    }

    public String taxAmountInCentsFormatted() {
        return FormatCents.formatCents(taxAmountInCents);
    }

    public String shippingAmountInCentsFormatted() {
        return FormatCents.formatCents(shippingAmountInCents);
    }

    // --- toString ---

    @Override
    public String toString() {
        return "CanadaStoreShoppingBag{" +
                "desktopDisplayQty=" + desktopDisplayQty +
                ", device5DollarQty=" + device5DollarQty +
                ", device10DollarQty=" + device10DollarQty +
                ", device20DollarQty=" + device20DollarQty +
                ", threeDeviceDisplayEnglishQuantity=" + threeDeviceDisplayEnglishQuantity +
                ", threeDeviceDisplayFrenchQuantity=" + threeDeviceDisplayFrenchQuantity +
                ", threeDeviceDisplayBilingualQuantity=" + threeDeviceDisplayBilingualQuantity +
                ", singleCurveEnglishQuantity=" + singleCurveEnglishQuantity +
                ", singleCurveFrenchQuantity=" + singleCurveFrenchQuantity +
                ", singleCurveBilingualQuantity=" + singleCurveBilingualQuantity +
                ", taxAmountInCents=" + taxAmountInCents +
                ", shippingAmountInCents=" + shippingAmountInCents +
                '}';
    }


}
