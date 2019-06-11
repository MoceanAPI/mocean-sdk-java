package com.mocean.modules.message;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.Transmitter;
import com.mocean.modules.message.mapper.VerifyValidateResponse;
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
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doAnswer;

public class VerifyValidateTest {
    private Mocean mocean;

    @BeforeEach
    public void setUp() {
        this.mocean = TestingUtils.getMoceanObj();
    }

    @Test
    public void testSetterMethod() {
        VerifyValidate verifyValidate = this.mocean.verifyValidate();

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
    public void testSend() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("post", invocationOnMock.getArgument(0));
                        assertEquals("/verify/check", invocationOnMock.getArgument(1));

                        return new String(Files.readAllBytes(Paths.get("src", "test", "resources", "verify_code.json")), StandardCharsets.UTF_8);
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        //test is required field set
        try {
            mocean.verifyValidate().send();
            fail();
        } catch (RequiredFieldException ex) {
        }

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        mocean.verifyValidate()
                .send(new HashMap<String, String>(){{
                    put("mocean-reqid", "test req id");
                    put("mocean-code", "test code");
                }});

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    @Test
    public void testJsonResponseObject() throws IOException, MoceanErrorException {
        String jsonResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "verify_code.json")), StandardCharsets.UTF_8);

        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("post", invocationOnMock.getArgument(0));
                        assertEquals("/verify/check", invocationOnMock.getArgument(1));

                        return transmitterMock.formatResponse(
                                jsonResponse,
                                HttpURLConnection.HTTP_OK,
                                false,
                                "/verify/check"
                        );
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        VerifyValidateResponse verifyValidateResponse = mocean.verifyValidate()
                .setReqid("testing req id")
                .setCode("testing code")
                .send();
        assertEquals(verifyValidateResponse.toString(), jsonResponse);
        this.testObject(verifyValidateResponse);

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    @Test
    public void testXmlResponseObject() throws IOException, MoceanErrorException {
        String xmlResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "verify_code.xml")), StandardCharsets.UTF_8);

        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("post", invocationOnMock.getArgument(0));
                        assertEquals("/verify/check", invocationOnMock.getArgument(1));

                        return transmitterMock.formatResponse(
                                xmlResponse,
                                HttpURLConnection.HTTP_OK,
                                true,
                                "/verify/check"
                        );
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        VerifyValidateResponse verifyValidateResponse = mocean.verifyValidate()
                .setReqid("testing req id")
                .setCode("testing code")
                .send();
        assertEquals(verifyValidateResponse.toString(), xmlResponse);
        this.testObject(verifyValidateResponse);

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    private void testObject(VerifyValidateResponse verifyValidateResponse) {
        assertEquals(verifyValidateResponse.getStatus(), "0");
        assertEquals(verifyValidateResponse.getReqId(), "CPASS_restapi_C0000002737000000.0002");
        assertEquals(verifyValidateResponse.getMsgId(), "CPASS_restapi_C0000002737000000.0002");
        assertEquals(verifyValidateResponse.getPrice(), "0.35");
        assertEquals(verifyValidateResponse.getCurrency(), "MYR");
    }
}