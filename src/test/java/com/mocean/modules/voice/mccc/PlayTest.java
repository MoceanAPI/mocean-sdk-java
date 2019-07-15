package com.mocean.modules.voice.mccc;

import com.mocean.exception.RequiredFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayTest {
    @Test
    public void testRequestParams() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put("file", "testing file");
            put("barge-in", true);
            put("action", "play");
        }};
        Play play = new Play(params);

        assertEquals(params, play.getRequestData());

        play = new Play();
        play.setFiles("testing file");
        play.setBargeIn(true);

        assertEquals(params, play.getRequestData());
    }

    @Test
    public void testIfActionAutoDefined() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put("file", "testing file");
            put("barge-in", true);
        }};
        Play play = new Play(params);

        assertEquals("play", play.getRequestData().get("action"));
    }

    @Test
    public void testIfRequiredFieldNotSet() {
        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Play play = new Play();
                play.getRequestData();
            }
        });
    }
}
