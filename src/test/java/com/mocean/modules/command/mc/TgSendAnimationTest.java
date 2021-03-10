package com.mocean.modules.command.mc;

import com.mocean.exception.RequiredFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TgSendAnimationTest {
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
                        put("type", "animation");
                        put("rich_media_url","test url");
                        put("text", "test content");
                    }}
            );
        }};

        TgSendAnimation tgSendAnimation = new TgSendAnimation(params);

        assertEquals(params, tgSendAnimation.getRequestData());

        tgSendAnimation = new TgSendAnimation();
        tgSendAnimation.from("test from");
        tgSendAnimation.to("test to");
        tgSendAnimation.content("test url", "test content");

        assertEquals(params, tgSendAnimation.getRequestData());
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
                        put("type", "animation");
                        put("rich_media_url","test url");
                        put("text", "test content");
                    }}
            );
        }};
        TgSendAnimation tgSendAnimation = new TgSendAnimation(params);

        assertEquals("send-telegram", tgSendAnimation.getRequestData().get("action"));
    }

    @Test
    public void testIfRequiredFieldNotSet() {
        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                TgSendAnimation tgSendAnimation = new TgSendAnimation();
                tgSendAnimation.getRequestData();
                tgSendAnimation.getRequestData();
            }
        });
    }
}
