package com.mocean.modules.message;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.Transmitter;
import com.mocean.modules.message.mapper.MessageStatusResponse;
import com.mocean.system.Mocean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    public void testInquiry() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));
                        assertEquals("/report/message", invocationOnMock.getArgument(1));

                        return new String(Files.readAllBytes(Paths.get("src", "test", "resources", "message_status.json")), StandardCharsets.UTF_8);
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        mocean.messageStatus()
                .setMsgid("test msg id")
                .inquiry();

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    @Test
    public void testJsonResponseObject() throws IOException, MoceanErrorException {
        String jsonResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "message_status.json")), StandardCharsets.UTF_8);

        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));
                        assertEquals("/report/message", invocationOnMock.getArgument(1));

                        return transmitterMock.formatResponse(
                                jsonResponse,
                                HttpURLConnection.HTTP_OK,
                                false,
                                "/report/message"
                        );
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        MessageStatusResponse messageStatusResponse = mocean.messageStatus()
                .setMsgid("test msg id")
                .inquiry();
        assertEquals(messageStatusResponse.toString(), jsonResponse);
        this.testObject(messageStatusResponse);

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    @Test
    public void testXmlResponseObject() throws IOException, MoceanErrorException {
        String xmlResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "message_status.json")), StandardCharsets.UTF_8);

        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));
                        assertEquals("/report/message", invocationOnMock.getArgument(1));

                        return transmitterMock.formatResponse(
                                xmlResponse,
                                HttpURLConnection.HTTP_OK,
                                true,
                                "/report/message"
                        );
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        MessageStatusResponse messageStatusResponse = mocean.messageStatus()
                .setMsgid("test msg id")
                .inquiry();
        assertEquals(messageStatusResponse.toString(), xmlResponse);
        this.testObject(messageStatusResponse);

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    private void testObject(MessageStatusResponse messageStatusResponse) {
        assertEquals(messageStatusResponse.getStatus(), "0");
        assertEquals(messageStatusResponse.getMessageStatus(), "5");
        assertEquals(messageStatusResponse.getMsgId(), "CPASS_restapi_C0000002737000000.0001");
        assertEquals(messageStatusResponse.getCreditDeducted(), "0.0000");
    }
}