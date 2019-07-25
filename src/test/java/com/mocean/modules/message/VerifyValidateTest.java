package com.mocean.modules.message;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.Transmitter;
import com.mocean.modules.message.mapper.VerifyValidateResponse;
import com.mocean.system.Mocean;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mock.RuleAnswer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class VerifyValidateTest {
    @Test
    public void testSetterMethod() {
        VerifyValidate verifyValidate = TestingUtils.getMoceanObj().verifyValidate();

        verifyValidate.setReqid("test reqid");
        assertNotNull(verifyValidate.getParams().get("mocean-reqid"));
        assertEquals("test reqid", verifyValidate.getParams().get("mocean-reqid"));

        verifyValidate.setCode("test code");
        assertNotNull(verifyValidate.getParams().get("mocean-code"));
        assertEquals("test code", verifyValidate.getParams().get("mocean-code"));

        verifyValidate.setRespFormat("json");
        assertNotNull(verifyValidate.getParams().get("mocean-resp-format"));
        assertEquals("json", verifyValidate.getParams().get("mocean-resp-format"));
    }

    @Test
    public void testRequiredFieldNotSet() {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                return TestingUtils.getResponse("verify_code.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);

        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mocean.verifyValidate().send();
            }
        });
    }

    @Test
    public void testJsonSend() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/verify/check"));
                return TestingUtils.getResponse("verify_code.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        VerifyValidateResponse verifyValidateResponse = mocean.verifyValidate()
                .send(new HashMap<String, String>() {{
                    put("mocean-reqid", "test req id");
                    put("mocean-code", "test code");
                }});
        assertEquals(verifyValidateResponse.toString(), TestingUtils.getResponseString("verify_code.json"));
        this.testObject(verifyValidateResponse);
    }

    @Test
    public void testXmlSend() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/verify/check"));
                return TestingUtils.getResponse("verify_code.xml", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        VerifyValidateResponse verifyValidateResponse = mocean.verifyValidate()
                .setReqid("testing req id")
                .setCode("testing code")
                .setRespFormat("xml")
                .send();
        assertEquals(verifyValidateResponse.toString(), TestingUtils.getResponseString("verify_code.xml"));
        this.testObject(verifyValidateResponse);
    }

    private void testObject(VerifyValidateResponse verifyValidateResponse) {
        assertEquals(verifyValidateResponse.getStatus(), "0");
        assertEquals(verifyValidateResponse.getReqId(), "CPASS_restapi_C0000002737000000.0002");
    }
}