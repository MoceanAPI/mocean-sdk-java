package com.mocean.modules.command.mc;

import com.mocean.exception.RequiredFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TgSendVideoTest {
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
                        put("type", "video");
                        put("rich_media_url","test url");
                        put("text", "test content");
                    }}
            );
        }};

        TgSendVideo tgSendVideo = new TgSendVideo(params);

        assertEquals(params, tgSendVideo.getRequestData());

        tgSendVideo = new TgSendVideo();
        tgSendVideo.from("test from");
        tgSendVideo.to("test to");
        tgSendVideo.content("test url", "test content");

        assertEquals(params, tgSendVideo.getRequestData());
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
                        put("type", "video");
                        put("rich_media_url","test url");
                        put("text", "test content");
                    }}
            );
        }};
        TgSendVideo tgSendVideo = new TgSendVideo(params);

        assertEquals("send-telegram", tgSendVideo.getRequestData().get("action"));
    }

    @Test
    public void testIfRequiredFieldNotSet() {
        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                TgSendVideo tgSendVideo = new TgSendVideo();
                tgSendVideo.getRequestData();
                tgSendVideo.getRequestData();
            }
        });
    }
}
