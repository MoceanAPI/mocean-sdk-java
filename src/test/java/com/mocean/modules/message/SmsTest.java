package com.mocean.modules.message;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.Transmitter;
import com.mocean.modules.account.mapper.BalanceResponse;
import com.mocean.modules.message.mapper.SmsResponse;
import com.mocean.system.Mocean;
import com.mocean.utils.Utils;
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

public class SmsTest {
    private Mocean mocean;

    @BeforeEach
    public void setUp() {
        this.mocean = TestingUtils.getMoceanObj();
    }

    @Test
    public void testSetterMethod() {
        Sms sms = this.mocean.sms();

        sms.setFrom("test from");
        assertNotNull(sms.getParams().get("mocean-from"));
        assertEquals("test from", sms.getParams().get("mocean-from"));

        sms.setTo("test to");
        assertNotNull(sms.getParams().get("mocean-to"));
        assertEquals("test to", sms.getParams().get("mocean-to"));

        sms.setText("test text");
        assertNotNull(sms.getParams().get("mocean-text"));
        assertEquals("test text", sms.getParams().get("mocean-text"));

        sms.setUdh("test udh");
        assertNotNull(sms.getParams().get("mocean-udh"));
        assertEquals("test udh", sms.getParams().get("mocean-udh"));

        sms.setCoding("test coding");
        assertNotNull(sms.getParams().get("mocean-coding"));
        assertEquals("test coding", sms.getParams().get("mocean-coding"));

        sms.setDlrMask("test dlr mask");
        assertNotNull(sms.getParams().get("mocean-dlr-mask"));
        assertEquals("test dlr mask", sms.getParams().get("mocean-dlr-mask"));

        sms.setDlrUrl("test dlr url");
        assertNotNull(sms.getParams().get("mocean-dlr-url"));
        assertEquals("test dlr url", sms.getParams().get("mocean-dlr-url"));

        sms.setSchedule("test schedule");
        assertNotNull(sms.getParams().get("mocean-schedule"));
        assertEquals("test schedule", sms.getParams().get("mocean-schedule"));

        sms.setMclass("test mclass");
        assertNotNull(sms.getParams().get("mocean-mclass"));
        assertEquals("test mclass", sms.getParams().get("mocean-mclass"));

        sms.setAltDcs("test alt dcs");
        assertNotNull(sms.getParams().get("mocean-alt-dcs"));
        assertEquals("test alt dcs", sms.getParams().get("mocean-alt-dcs"));

        sms.setCharset("test charset");
        assertNotNull(sms.getParams().get("mocean-charset"));
        assertEquals("test charset", sms.getParams().get("mocean-charset"));

        sms.setValidity("test validity");
        assertNotNull(sms.getParams().get("mocean-validity"));
        assertEquals("test validity", sms.getParams().get("mocean-validity"));

        sms.setRespFormat("json");
        assertNotNull(sms.getParams().get("mocean-resp-format"));
        assertEquals("json", sms.getParams().get("mocean-resp-format"));
    }

    @Test
    public void testInquiry() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("post", invocationOnMock.getArgument(0));
                        assertEquals("/sms", invocationOnMock.getArgument(1));

                        return new String(Files.readAllBytes(Paths.get("src", "test", "resources", "message.json")), StandardCharsets.UTF_8);
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        mocean.sms()
                .setFrom("testing from")
                .setTo("testing to")
                .setText("testing text")
                .send();

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    @Test
    public void testJsonResponseObject() throws IOException, MoceanErrorException {
        String jsonResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "message.json")), StandardCharsets.UTF_8);

        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("post", invocationOnMock.getArgument(0));
                        assertEquals("/sms", invocationOnMock.getArgument(1));

                        return transmitterMock.formatResponse(
                                jsonResponse,
                                HttpURLConnection.HTTP_OK,
                                false,
                                "/sms"
                        );
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        SmsResponse smsResponse = mocean.sms()
                .setFrom("testing from")
                .setTo("testing to")
                .setText("testing text")
                .send();
        assertEquals(smsResponse.toString(), jsonResponse);
        this.testObject(smsResponse);

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    @Test
    public void testXmlResponseObject() throws IOException, MoceanErrorException {
        String xmlResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "message.xml")), StandardCharsets.UTF_8);

        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("post", invocationOnMock.getArgument(0));
                        assertEquals("/sms", invocationOnMock.getArgument(1));

                        return transmitterMock.formatResponse(
                                xmlResponse,
                                HttpURLConnection.HTTP_OK,
                                true,
                                "/sms"
                        );
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        transmitterMock.setTransmitterConfig(transmitterMock.getTransmitterConfig().setVersion("1"));
        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        SmsResponse smsResponse = mocean.sms()
                .setFrom("testing from")
                .setTo("testing to")
                .setText("testing text")
                .send();
        assertEquals(smsResponse.toString(), xmlResponse);
        this.testObject(smsResponse);

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());


        //v2 test
        String xmlResponseV2 = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "message_v2.xml")), StandardCharsets.UTF_8);

        Transmitter transmitterV2Mock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("post", invocationOnMock.getArgument(0));
                        assertEquals("/sms", invocationOnMock.getArgument(1));

                        return transmitterV2Mock.formatResponse(
                                xmlResponseV2,
                                HttpURLConnection.HTTP_OK,
                                true,
                                "/sms"
                        );
                    }
                }
        ).when(transmitterV2Mock).send(anyString(), anyString(), any());

        transmitterV2Mock.setTransmitterConfig(transmitterV2Mock.getTransmitterConfig().setVersion("2"));
        mocean = TestingUtils.getMoceanObj(transmitterV2Mock);
        smsResponse = mocean.sms()
                .setFrom("testing from")
                .setTo("testing to")
                .setText("testing text")
                .send();
        assertEquals(smsResponse.toString(), xmlResponseV2);
        this.testObject(smsResponse);

        verify(transmitterV2Mock, times(1)).send(anyString(), anyString(), any());
    }

    private void testObject(SmsResponse smsResponse) {
        assertTrue(Utils.isArray(smsResponse.getMessages()));
        assertEquals(smsResponse.getMessages()[0].getStatus(), "0");
        assertEquals(smsResponse.getMessages()[0].getReceiver(), "60123456789");
        assertEquals(smsResponse.getMessages()[0].getMsgId(), "CPASS_restapi_C0000002737000000.0001");
    }
}