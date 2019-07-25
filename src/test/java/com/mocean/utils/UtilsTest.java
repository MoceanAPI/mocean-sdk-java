package com.mocean.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilsTest {

    @Test
    public void isNullOrEmpty() {
        assertTrue(Utils.isNullOrEmpty(""));
        assertTrue(Utils.isNullOrEmpty(null));
        assertFalse(Utils.isNullOrEmpty("test"));
    }

    @Test
    public void isArray() {
        String[] strArr = new String[]{"Hello"};
        assertTrue(Utils.isArray(strArr));
        assertFalse(Utils.isArray("Hello"));
        assertFalse(Utils.isArray(null));
    }
}