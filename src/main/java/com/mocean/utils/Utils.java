package com.mocean.utils;

public final class Utils {
    private Utils() {
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }
}
