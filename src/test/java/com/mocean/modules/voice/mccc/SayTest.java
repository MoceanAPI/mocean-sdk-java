package com.mocean.modules.voice.mccc;

import com.mocean.exception.RequiredFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SayTest {
    @Test
    public void testRequestParams() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put("language", "testing language");
            put("text", "testing text");
            put("barge-in", true);
            put("action", "say");
        }};
        Say say = new Say(params);

        assertEquals(params, say.getRequestData());

        say = new Say();
        say.setLanguage("testing language");
        say.setText("testing text");
        say.setBargeIn(true);

        assertEquals(params, say.getRequestData());
    }

    @Test
    public void testIfActionAutoDefined() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put("language", "testing language");
            put("text", "testing text");
            put("barge-in", true);
        }};
        Say say = new Say(params);

        assertEquals("say", say.getRequestData().get("action"));
    }

    @Test
    public void testIfRequiredFieldNotSet() {
        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Say say = new Say();
                say.getRequestData();
            }
        });
    }
}
