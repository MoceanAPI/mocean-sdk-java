package com.mocean.modules.command.mc;

import com.mocean.exception.RequiredFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TgRequestContactTest {
    @Test
    public void testRequestParams() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put(
                    "from",
                    new HashMap<String, String>() {{
                        put("type", "bot_username");
                        put("id", "test id");
                    }}
            );
            put(
                    "to",
                    new HashMap<String, String>() {{
                        put("type", "chat_id");
                        put("id", "test id");
                    }}
            );
            put(
                    "content",
                    new HashMap<String, String>() {{
                        put("type", "text");
                        put("text", "test content");
                    }}
            );
            put(
                    "tg_keyboard",
                    new HashMap<String,String>(){{
                        put("button_request", "contact");
                        put("button_text", "test button text");
                    }}
            );
        }};

        TgRequestContact tgRequestContact = new TgRequestContact(params);

        assertEquals(params, tgRequestContact.getRequestData());

        tgRequestContact = new TgRequestContact();
        tgRequestContact.from("test from");
        tgRequestContact.to("test to");
        tgRequestContact.content("test content");
        tgRequestContact.button("test button text");

        assertEquals(params, tgRequestContact.getRequestData());
    }

    @Test
    public void testIfActionAutoDefined() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put(
                    "from",
                    new HashMap<String, String>() {{
                        put("type", "bot_username");
                        put("id", "test id");
                    }}
            );
            put(
                    "to",
                    new HashMap<String, String>() {{
                        put("type", "chat_id");
                        put("id", "test id");
                    }}
            );
            put(
                    "content",
                    new HashMap<String, String>() {{
                        put("type", "text");
                        put("text", "test content");
                    }}
            );
            put(
                    "tg_keyboard",
                    new HashMap<String,String>(){{
                        put("button_request", "contact");
                        put("button_text", "test button text");
                    }}
            );
        }};

        TgRequestContact tgRequestContact = new TgRequestContact(params);

        assertEquals("send-telegram", tgRequestContact.getRequestData().get("action"));
    }

    @Test
    public void testIfRequiredFieldNotSet() {
        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                TgRequestContact tgRequestContact = new TgRequestContact();
                tgRequestContact.getRequestData();
            }
        });
    }
}
