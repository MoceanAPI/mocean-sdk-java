package com.mocean.modules.command.mc;

import com.mocean.exception.RequiredFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TgSendAudioTest {
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
                        put("type", "audio");
                        put("rich_media_url","test url");
                        put("text", "test content");
                    }}
            );
        }};

        TgSendAudio tgSendAudio = new TgSendAudio(params);

        assertEquals(params, tgSendAudio.getRequestData());

        tgSendAudio = new TgSendAudio();
        tgSendAudio.from("test from");
        tgSendAudio.to("test to");
        tgSendAudio.content("test url", "test content");

        assertEquals(params, tgSendAudio.getRequestData());
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
                        put("type", "audio");
                        put("rich_media_url", "test url");
                        put("text", "test content");
                    }}
            );
        }};
        TgSendAudio tgSendAudio = new TgSendAudio(params);

        assertEquals("send-telegram", tgSendAudio.getRequestData().get("action"));
    }

    @Test
    public void testIfRequiredFieldNotSet() {
        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                TgSendAudio tgSendAudio = new TgSendAudio();
                tgSendAudio.getRequestData();
                tgSendAudio.getRequestData();
            }
        });
    }
}
