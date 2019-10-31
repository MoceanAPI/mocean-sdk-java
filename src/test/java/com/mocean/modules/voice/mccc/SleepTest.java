package com.mocean.modules.voice.mccc;

import com.mocean.exception.RequiredFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SleepTest {
    @Test
    public void testRequestParams() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put("duration", 10000);
            put("action", "sleep");
        }};
        Sleep sleep = new Sleep(params);

        assertEquals(params, sleep.getRequestData());

        sleep = new Sleep();
        sleep.setDuration(10000);

        assertEquals(params, sleep.getRequestData());
    }

    @Test
    public void testIfActionAutoDefined() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put("duration", 10000);
            put("barge-in", true);
        }};
        Sleep sleep = new Sleep(params);

        assertEquals("sleep", sleep.getRequestData().get("action"));
    }

    @Test
    public void testIfRequiredFieldNotSet() {
        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Sleep sleep = new Sleep();
                sleep.getRequestData();
            }
        });
    }
}
