package com.mocean.modules.command.mc;

import com.mocean.exception.RequiredFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TgSendTextTest {
    @Test
    public void testRequestParams() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put(
                    "from",
                    new HashMap<String, String>() {{
                        put("type", "bot_username");
                        put("id", "test from");
                    }}
            );
            put(
                    "to",
                    new HashMap<String, String>() {{
                        put("type", "chat_id");
                        put("id", "test to");
                    }}
            );
            put(
                    "content",
                    new HashMap<String, String>() {{
                        put("type", "text");
                        put("text", "test content");
                    }}
            );
        }};

        TgSendText tgSendText = new TgSendText(params);

        assertEquals(params, tgSendText.getRequestData());

        tgSendText = new TgSendText();
        tgSendText.from("test from");
        tgSendText.to("test to");
        tgSendText.content("test content");

        assertEquals(params, tgSendText.getRequestData());
    }

    @Test
    public void testIfActionAutoDefined() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put(
                    "from",
                    new HashMap<String, String>() {{
                        put("type", "bot_username");
                        put("id", "test from");
                    }}
            );
            put(
                    "to",
                    new HashMap<String, String>() {{
                        put("type", "chat_id");
                        put("id", "test to");
                    }}
            );
            put(
                    "content",
                    new HashMap<String, String>() {{
                        put("type", "text");
                        put("text", "test content");
                    }}
            );
        }};
        TgSendText tgSendText = new TgSendText(params);

        assertEquals("send-telegram", tgSendText.getRequestData().get("action"));
    }

    @Test
    public void testIfRequiredFieldNotSet() {
        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                TgSendText tgSendText = new TgSendText();
                tgSendText.getRequestData();
            }
        });
    }
}
