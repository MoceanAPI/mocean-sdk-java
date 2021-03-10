package com.mocean.modules.command.mc;

import com.mocean.exception.RequiredFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TgSendPhotoTest {
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
                        put("type", "photo");
                        put("rich_media_url","test url");
                        put("text", "test content");
                    }}
            );
        }};

        TgSendPhoto tgSendPhoto = new TgSendPhoto(params);

        assertEquals(params, tgSendPhoto.getRequestData());

        tgSendPhoto = new TgSendPhoto();
        tgSendPhoto.from("test from");
        tgSendPhoto.to("test to");
        tgSendPhoto.content("test url", "test content");

        assertEquals(params, tgSendPhoto.getRequestData());
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
                        put("id", "test content");
                    }}
            );
            put(
                    "content",
                    new HashMap<String, String>() {{
                        put("type", "photo");
                        put("rich_media_url","test url");
                        put("text", "test content");
                    }}
            );
        }};
        TgSendPhoto tgSendPhoto = new TgSendPhoto(params);

        assertEquals("send-telegram", tgSendPhoto.getRequestData().get("action"));
    }

    @Test
    public void testIfRequiredFieldNotSet() {
        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                TgSendPhoto tgSendPhoto = new TgSendPhoto();
                tgSendPhoto.getRequestData();
                tgSendPhoto.getRequestData();
            }
        });
    }
}
