package com.mocean.modules.voice.mccc;

import com.mocean.exception.RequiredFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class DialTest {
    @Test
    public void testRequestParams() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put("to", "testing to");
            put("action", "dial");
            put("from", "callerid");
            put("dial-sequentially", true);
        }};
        Dial dial = new Dial(params);

        assertEquals(params, dial.getRequestData());

        dial = new Dial();
        dial.setTo("testing to");
        dial.setFrom("callerid");
        dial.setDialSequentially(true);

        assertEquals(params, dial.getRequestData());
    }

    @Test
    public void testIfActionAutoDefined() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put("to", "testing to");
        }};
        Dial dial = new Dial(params);

        assertEquals("dial", dial.getRequestData().get("action"));
    }

    @Test
    public void testIfRequiredFieldNotSet() {
        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Dial dial = new Dial();
                dial.getRequestData();
            }
        });
    }
}
