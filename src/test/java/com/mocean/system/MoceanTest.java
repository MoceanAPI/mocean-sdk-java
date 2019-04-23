package com.mocean.system;

import com.mocean.exception.RequiredFieldException;
import com.mocean.system.auth.Basic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoceanTest {
    private Basic basic;

    @BeforeEach
    public void setUp() {
        this.basic = new Basic("test api key", "test api secret");
    }

    @Test
    public void testMoceanCreationFailedWhenNoApiKeyOrApiSecret() {
        assertThrows(RequiredFieldException.class, () -> {
            new Mocean(new Basic("test api key", ""));
        });

        assertThrows(RequiredFieldException.class, () -> {
            new Mocean(new Basic("", "test api secret"));
        });

        assertThrows(RequiredFieldException.class, () -> {
            new Mocean(new Basic("", ""));
        });

        assertThrows(RequiredFieldException.class, () -> {
            new Mocean(new Basic("test api key", null));
        });

        assertThrows(RequiredFieldException.class, () -> {
            new Mocean(new Basic(null, "test api secret"));
        });

        assertThrows(RequiredFieldException.class, () -> {
            new Mocean(new Basic());
        });
    }

    @Test
    public void testCreateMoceanObj() {
        try {
            new Mocean(this.basic);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}