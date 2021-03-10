package com.mocean.modules.command.mc;

import com.mocean.exception.RequiredFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SendSMSTest {
    @Test
    public void testRequestParams() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put(
                    "from",
                    new HashMap<String, String>() {{
                        put("type", "phone_num");
                        put("id", "test from");
                    }}
            );
            put(
                    "to",
                    new HashMap<String, String>() {{
                        put("type", "phone_num");
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

        SendSMS sendSMS = new SendSMS(params);

        assertEquals(params, sendSMS.getRequestData());

        sendSMS = new SendSMS();
        sendSMS.from("test from");
        sendSMS.to("test to");
        sendSMS.content("test content");

        assertEquals(params, sendSMS.getRequestData());
    }

    @Test
    public void testIfActionAutoDefined() throws RequiredFieldException {
        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put(
                    "from",
                    new HashMap<String, String>() {{
                        put("type", "phone_num");
                        put("id", "test from");
                    }}
            );
            put(
                    "to",
                    new HashMap<String, String>() {{
                        put("type", "phone_num");
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
        SendSMS sendSMS = new SendSMS(params);

        assertEquals("send-sms", sendSMS.getRequestData().get("action"));
    }

    @Test
    public void testIfRequiredFieldNotSet() {
        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                SendSMS sendSMS = new SendSMS();
                sendSMS.getRequestData();
                sendSMS.getRequestData();
            }
        });
    }
}
