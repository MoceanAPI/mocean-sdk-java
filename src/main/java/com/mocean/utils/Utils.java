package com.mocean.utils;

public final class Utils {
    private Utils() {
    }

    public static boolean isNullOrEmpty(Object str) {
        try {
            String parsedStr = str.toString();
            return parsedStr.trim().isEmpty();
        } catch (Exception ex) {
            //unable to parse to string
            return true;
        }
    }

    public static boolean isArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }
}
