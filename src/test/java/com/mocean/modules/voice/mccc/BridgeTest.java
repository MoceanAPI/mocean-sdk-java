package com.mocean.modules.voice.mccc;

import com.mocean.exception.RequiredFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class BridgeTest {
    @Test
    public void testRequestParams() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put("to", "testing to");
            put("action", "dial");
        }};
        Bridge bridge = new Bridge(params);

        assertEquals(params, bridge.getRequestData());

        bridge = new Bridge();
        bridge.setTo("testing to");

        assertEquals(params, bridge.getRequestData());
    }

    @Test
    public void testIfActionAutoDefined() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put("to", "testing to");
        }};
        Bridge bridge = new Bridge(params);

        assertEquals("dial", bridge.getRequestData().get("action"));
    }

    @Test
    public void testIfRequiredFieldNotSet() {
        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Bridge bridge = new Bridge();
                bridge.getRequestData();
            }
        });
    }
}
