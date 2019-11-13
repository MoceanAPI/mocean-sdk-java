package com.mocean.modules.voice.mc;

import com.mocean.exception.RequiredFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CollectTest {
    @Test
    public void testRequestParams() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put("event-url", "testing event url");
            put("min", 1);
            put("max", 10);
            put("terminators", "#");
            put("timeout", 10000);
            put("action", "collect");
        }};
        Collect collect = new Collect(params);

        assertEquals(params, collect.getRequestData());

        collect = new Collect();
        collect.setEventUrl("testing event url");
        collect.setMin(1);
        collect.setMax(10);
        collect.setTerminators("#");
        collect.setTimeout(10000);

        assertEquals(params, collect.getRequestData());
    }

    @Test
    public void testIfActionAutoDefined() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put("event-url", "testing event url");
            put("min", 1);
            put("max", 10);
            put("terminators", "#");
            put("timeout", 10000);
        }};
        Collect collect = new Collect(params);

        assertEquals("collect", collect.getRequestData().get("action"));
    }

    @Test
    public void testIfRequiredFieldNotSet() {
        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Collect collect = new Collect();
                collect.getRequestData();
            }
        });
    }
}
