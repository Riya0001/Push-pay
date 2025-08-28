package com.tiptappay.store.app.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class AppUtil {

    public static String mapValueToString(Object value) {
        return Optional.ofNullable(value)
                .map(Object::toString)
                .orElse(null);
    }

    public static String extractNumericDigits(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceAll("[^0-9]", "");
    }

    public static String normalizePhone(String input) {
        String digits = extractNumericDigits(input);

        if (digits.length() != 10) {
            return "";
        }

        return String.format("(%s) %s-%s",
                digits.substring(0, 3),
                digits.substring(3, 6),
                digits.substring(6));
    }

    public static String formatCents(int cents) {
        double dollars = cents / 100.0;

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(dollars);
    }

    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static long getCurrentTimeMillisPlus(TimeUnit timeUnit, long duration) {
        long plusDuration = timeUnit.toMillis(duration);
        return System.currentTimeMillis() + plusDuration;
    }
}
