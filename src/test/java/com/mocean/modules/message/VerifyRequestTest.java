package com.mocean.modules.message;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.Transmitter;
import com.mocean.modules.message.mapper.VerifyRequestResponse;
import com.mocean.system.Mocean;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mock.RuleAnswer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class VerifyRequestTest {
    @Test
    public void testSetterMethod() {
        VerifyRequest verifyRequest = TestingUtils.getMoceanObj().verifyRequest();

        verifyRequest.setTo("test to");
        assertNotNull(verifyRequest.getParams().get("mocean-to"));
        assertEquals("test to", verifyRequest.getParams().get("mocean-to"));

        verifyRequest.setBrand("test brand");
        assertNotNull(verifyRequest.getParams().get("mocean-brand"));
        assertEquals("test brand", verifyRequest.getParams().get("mocean-brand"));

        verifyRequest.setFrom("test from");
        assertNotNull(verifyRequest.getParams().get("mocean-from"));
        assertEquals("test from", verifyRequest.getParams().get("mocean-from"));

        verifyRequest.setCodeLength("test code length");
        assertNotNull(verifyRequest.getParams().get("mocean-code-length"));
        assertEquals("test code length", verifyRequest.getParams().get("mocean-code-length"));

        verifyRequest.setTemplate("test template");
        assertNotNull(verifyRequest.getParams().get("mocean-template"));
        assertEquals("test template", verifyRequest.getParams().get("mocean-template"));

        verifyRequest.setPinValidity("test pin validity");
        assertNotNull(verifyRequest.getParams().get("mocean-pin-validity"));
        assertEquals("test pin validity", verifyRequest.getParams().get("mocean-pin-validity"));

        verifyRequest.setNextEventWait("test next event wait");
        assertNotNull(verifyRequest.getParams().get("mocean-next-event-wait"));
        assertEquals("test next event wait", verifyRequest.getParams().get("mocean-next-event-wait"));

        verifyRequest.setReqId("test req id");
        assertNotNull(verifyRequest.getParams().get("mocean-reqid"));
        assertEquals("test req id", verifyRequest.getParams().get("mocean-reqid"));

        verifyRequest.setRespFormat("json");
        assertNotNull(verifyRequest.getParams().get("mocean-resp-format"));
        assertEquals("json", verifyRequest.getParams().get("mocean-resp-format"));
    }

    @Test
    public void testRequiredFieldNotSet() {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                return TestingUtils.getResponse("send_code.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);

        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mocean.verifyRequest().send();
            }
        });
    }

    @Test
    public void testSendAsSmsChannel() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/verify/req/sms"));
                return TestingUtils.getResponse("send_code.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        VerifyRequest verifyRequest = mocean.verifyRequest();
        assertEquals(Channel.AUTO, verifyRequest.channel);
        verifyRequest.sendAs(Channel.SMS);
        assertEquals(Channel.SMS, verifyRequest.channel);
        verifyRequest
                .setTo("testing to")
                .setBrand("testing brand")
                .send();
    }

    @Test
    public void testResend() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/verify/resend/sms"));
                return TestingUtils.getResponse("send_code.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        mocean.verifyRequest()
                .setReqId("testing req id")
                .resend();
    }

    @Test
    public void testResendThroughResponseObject() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                HashMap<String, String> mapBody = TestingUtils.rewindBody((FormBody) request.body());
                assertEquals("CPASS_restapi_C0000002737000000.0002", mapBody.get("mocean-reqid"));
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/verify/resend/sms"));
                return TestingUtils.getResponse("resend_code.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        VerifyRequestResponse res = ResponseFactory.createObjectFromRawResponse(TestingUtils.getResponseString("send_code.json"), VerifyRequestResponse.class)
                .setRawResponse(TestingUtils.getResponseString("resend_code.json"))
                .setVerifyRequest(mocean.verifyRequest());

        VerifyRequestResponse resendRes = res.resend();
        assertEquals(resendRes.getStatus(), "0");
        assertEquals(resendRes.getReqId(), "CPASS_restapi_C0000002737000000.0002");
        assertEquals(resendRes.getTo(), "60123456789");
        assertEquals(resendRes.getResendNumber(), "1");
    }

    @Test
    public void testJsonSend() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/verify/req"));
                return TestingUtils.getResponse("send_code.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        VerifyRequestResponse verifyRequestResponse = mocean.verifyRequest()
                .send(new HashMap<String, String>() {{
                    put("mocean-to", "testing to");
                    put("mocean-brand", "testing brand");
                }});
        assertEquals(verifyRequestResponse.toString(), TestingUtils.getResponseString("send_code.json"));
        this.testObject(verifyRequestResponse);
    }

    @Test
    public void textXmlSend() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/verify/req"));
                return TestingUtils.getResponse("send_code.xml", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        VerifyRequestResponse verifyRequestResponse = mocean.verifyRequest()
                .setTo("testing to")
                .setBrand("testing brand")
                .setRespFormat("xml")
                .send();
        assertEquals(verifyRequestResponse.toString(), TestingUtils.getResponseString("send_code.xml"));
        this.testObject(verifyRequestResponse);
    }

    private void testObject(VerifyRequestResponse verifyRequestResponse) {
        assertEquals(verifyRequestResponse.getStatus(), "0");
        assertEquals(verifyRequestResponse.getReqId(), "CPASS_restapi_C0000002737000000.0002");
    }
}