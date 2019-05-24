package com.mocean.system;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransmitterConfigTest {
    @Test
    public void testMakeMethodShouldReturnInstance() {
        assertTrue(TransmitterConfig.make() instanceof TransmitterConfig);
    }

    @Test
    public void testTrasmitterConfigShouldHaveDefaultValue() {
        TransmitterConfig transmitterConfig = new TransmitterConfig();

        assertNotNull(transmitterConfig.getBaseUrl());
        assertNotNull(transmitterConfig.getVersion());
    }

    @Test
    public void testSetterMethod() {
        TransmitterConfig transmitterConfig = new TransmitterConfig();

        transmitterConfig.setBaseUrl("test base url");
        assertNotNull(transmitterConfig.getBaseUrl());
        assertEquals("test base url", transmitterConfig.getBaseUrl());

        transmitterConfig.setVersion("2");
        assertNotNull(transmitterConfig.getVersion());
        assertEquals("2", transmitterConfig.getVersion());
    }

    @Test
    public void testSetConfigThroughConstructor() {
        TransmitterConfig transmitterConfig = new TransmitterConfig("test base url", "2");

        assertEquals("test base url", transmitterConfig.getBaseUrl());
        assertEquals("2", transmitterConfig.getVersion());
    }
}