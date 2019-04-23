package com.mocean.modules.message;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.message.mapper.MessageStatusResponse;
import com.mocean.system.Mocean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageStatusTest {
    private Mocean mocean;

    @BeforeEach
    public void setUp() {
        this.mocean = TestingUtils.getMoceanObj();
    }

    @Test
    public void testSetterMethod() {
        MessageStatus messageStatus = this.mocean.messageStatus();

        messageStatus.setMsgid("test msg id");
        assertNotNull(messageStatus.getParams().get("mocean-msgid"));
        assertEquals("test msg id", messageStatus.getParams().get("mocean-msgid"));

        messageStatus.setRespFormat("json");
        assertNotNull(messageStatus.getParams().get("mocean-resp-format"));
        assertEquals("json", messageStatus.getParams().get("mocean-resp-format"));
    }

    @Test
    public void testJsonInquiry() {
        try {
            String jsonResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "message_status.json")), StandardCharsets.UTF_8);

            MessageStatus messageStatusMock = mock(MessageStatus.class);
            when(messageStatusMock.inquiry())
                    .thenReturn(
                            ResponseFactory
                                    .createObjectFromRawResponse(jsonResponse, MessageStatusResponse.class)
                                    .setRawResponse(jsonResponse)
                    );

            MessageStatusResponse messageStatusResponse = messageStatusMock.inquiry();
            assertEquals(messageStatusResponse.toString(), jsonResponse);
            assertEquals(messageStatusResponse.getStatus(), "0");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testXmlInquiry() {
        try {
            String xmlResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "message_status.xml")), StandardCharsets.UTF_8);

            MessageStatus messageStatusMock = mock(MessageStatus.class);
            when(messageStatusMock.inquiry())
                    .thenReturn(
                            ResponseFactory
                                    .createObjectFromRawResponse(xmlResponse, MessageStatusResponse.class)
                                    .setRawResponse(xmlResponse)
                    );

            MessageStatusResponse messageStatusResponse = messageStatusMock.inquiry();
            assertEquals(messageStatusResponse.toString(), xmlResponse);
            assertEquals(messageStatusResponse.getStatus(), "0");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testMalformedResponse() throws IOException {
        try {
            ResponseFactory
                    .createObjectFromRawResponse("malform string", MessageStatusResponse.class)
                    .setRawResponse("malform string");
            fail();
        } catch (MoceanErrorException ignored) {
        }
    }
}