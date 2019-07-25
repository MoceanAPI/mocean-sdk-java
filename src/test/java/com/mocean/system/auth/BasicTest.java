package com.mocean.system.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicTest {
    private Basic basic;

    @BeforeEach
    public void setUp() {
        this.basic = new Basic();
    }

    @Test
    public void setApiKey() {
        basic.setApiKey("test api key");
        assertEquals(basic.getParams().get("mocean-api-key"), "test api key");
    }

    @Test
    public void setApiSecret() {
        basic.setApiSecret("test api secret");
        assertEquals(basic.getParams().get("mocean-api-secret"), "test api secret");
    }

    @Test
    public void getParams() {
        basic.setApiKey("test api key");
        basic.setApiSecret("test api secret");

        assertEquals(basic.getParams().get("mocean-api-key"), "test api key");
        assertEquals(basic.getParams().get("mocean-api-secret"), "test api secret");
    }
}