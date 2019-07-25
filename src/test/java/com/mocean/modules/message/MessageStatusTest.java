package com.mocean.modules.message;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.Transmitter;
import com.mocean.modules.message.mapper.MessageStatusResponse;
import com.mocean.system.Mocean;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mock.RuleAnswer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class MessageStatusTest {
    @Test
    public void testSetterMethod() {
        MessageStatus messageStatus = TestingUtils.getMoceanObj().messageStatus();

        messageStatus.setMsgid("test msg id");
        assertNotNull(messageStatus.getParams().get("mocean-msgid"));
        assertEquals("test msg id", messageStatus.getParams().get("mocean-msgid"));

        messageStatus.setRespFormat("json");
        assertNotNull(messageStatus.getParams().get("mocean-resp-format"));
        assertEquals("json", messageStatus.getParams().get("mocean-resp-format"));
    }

    @Test
    public void testJsonInquiry() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("get"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/report/message"));
                return TestingUtils.getResponse("message_status.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        MessageStatusResponse messageStatusResponse = mocean.messageStatus()
                .inquiry(new HashMap<String, String>() {{
                    put("mocean-msgid", "test msg id");
                }});
        assertEquals(messageStatusResponse.toString(), TestingUtils.getResponseString("message_status.json"));
        this.testObject(messageStatusResponse);
    }

    @Test
    public void testRequiredFieldNotSet() {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                return TestingUtils.getResponse("message_status.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);

        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mocean.messageStatus().inquiry();
            }
        });
    }

    @Test
    public void testXmlInquiry() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("get"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/report/message"));
                return TestingUtils.getResponse("message_status.xml", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        MessageStatusResponse messageStatusResponse = mocean.messageStatus()
                .setMsgid("test msg id")
                .setRespFormat("xml")
                .inquiry();
        assertEquals(messageStatusResponse.toString(), TestingUtils.getResponseString("message_status.xml"));
        this.testObject(messageStatusResponse);
    }

    private void testObject(MessageStatusResponse messageStatusResponse) {
        assertEquals(messageStatusResponse.getStatus(), "0");
        assertEquals(messageStatusResponse.getMessageStatus(), "5");
        assertEquals(messageStatusResponse.getMsgId(), "CPASS_restapi_C0000002737000000.0001");
        assertEquals(messageStatusResponse.getCreditDeducted(), "0.0000");
    }
}