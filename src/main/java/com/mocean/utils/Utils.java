package com.mocean.utils;

public class Utils {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }
}
