package com.mocean.modules.message;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.Transmitter;
import com.mocean.modules.message.mapper.SmsResponse;
import com.mocean.system.Mocean;
import com.mocean.utils.Utils;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mock.RuleAnswer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class SmsTest {
    @Test
    public void testSetterMethod() {
        Sms sms = TestingUtils.getMoceanObj().sms();

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
    public void testRequiredFieldNotSet() {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                return TestingUtils.getResponse("message.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);

        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mocean.sms().send();
            }
        });
    }

    @Test
    public void testSendFlashSms() throws MoceanErrorException, IOException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                HashMap<String, String> mapBody = TestingUtils.rewindBody((FormBody) request.body());
                assertTrue(mapBody.containsKey("mocean-mclass"));
                assertTrue(mapBody.containsKey("mocean-alt-dcs"));
                assertEquals(mapBody.get("mocean-to"), "testing to,another to");
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/sms"));
                return TestingUtils.getResponse("message.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        Sms sms = mocean.flashSms();
        sms.setFrom("testing from")
                .addTo("testing to")
                .addTo("another to")
                .setText("testing text")
                .send();
    }

    @Test
    public void testJsonSend() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/sms"));
                return TestingUtils.getResponse("message.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        SmsResponse smsResponse = mocean.sms()
                .send(new HashMap<String, String>() {{
                    put("from", "testing from");
                    put("to", "testing to");
                    put("text", "testing text");
                }});
        assertEquals(smsResponse.toString(), TestingUtils.getResponseString("message.json"));
        this.testObject(smsResponse);
    }

    @Test
    public void testXmlSend() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("1", "/sms"));
                return TestingUtils.getResponse("message.xml", 200);
            }
        }));

        transmitterMock.setTransmitterConfig(transmitterMock.getTransmitterConfig().setVersion("1"));
        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        SmsResponse smsResponse = mocean.sms()
                .send(new HashMap<String, String>() {{
                    put("mocean-from", "testing from");
                    put("mocean-to", "testing to");
                    put("mocean-text", "testing text");
                    put("mocean-resp-format", "xml");
                }});
        assertEquals(smsResponse.toString(), TestingUtils.getResponseString("message.xml"));
        this.testObject(smsResponse);

        //v2 test
        Transmitter transmitterV2Mock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                return TestingUtils.getResponse("message_v2.xml", 200);
            }
        }));

        transmitterV2Mock.setTransmitterConfig(transmitterV2Mock.getTransmitterConfig().setVersion("2"));
        mocean = TestingUtils.getMoceanObj(transmitterV2Mock);
        smsResponse = mocean.sms()
                .setFrom("testing from")
                .setTo("testing to")
                .setText("testing text")
                .setRespFormat("xml")
                .send();
        assertEquals(smsResponse.toString(), TestingUtils.getResponseString("message_v2.xml"));
        this.testObject(smsResponse);
    }

    private void testObject(SmsResponse smsResponse) {
        assertTrue(Utils.isArray(smsResponse.getMessages()));
        assertEquals(smsResponse.getMessages()[0].getStatus(), "0");
        assertEquals(smsResponse.getMessages()[0].getReceiver(), "60123456789");
        assertEquals(smsResponse.getMessages()[0].getMsgId(), "CPASS_restapi_C0000002737000000.0001");
    }
}