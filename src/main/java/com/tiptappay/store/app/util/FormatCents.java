package com.tiptappay.store.app.util;

import java.text.DecimalFormat;

public class FormatCents {

    // Format cent to dollar
    public static String formatCents(int cents) {
        double dollars = cents / 100.0;

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(dollars);
    }
}