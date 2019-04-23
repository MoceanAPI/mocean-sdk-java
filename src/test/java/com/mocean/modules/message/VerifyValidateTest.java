package com.mocean.modules.message;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.message.mapper.VerifyValidateResponse;
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
    public void testJsonSend() {
        try {
            String jsonResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "verify_code.json")), StandardCharsets.UTF_8);

            VerifyValidate verifyValidateMock = mock(VerifyValidate.class);
            when(verifyValidateMock.send())
                    .thenReturn(
                            ResponseFactory
                                    .createObjectFromRawResponse(jsonResponse, VerifyValidateResponse.class)
                                    .setRawResponse(jsonResponse)
                    );

            VerifyValidateResponse verifyValidateResponse = verifyValidateMock.send();
            assertEquals(verifyValidateResponse.toString(), jsonResponse);
            assertEquals(verifyValidateResponse.getStatus(), "0");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testXmlSend() {
        try {
            String xmlResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "verify_code.xml")), StandardCharsets.UTF_8);

            VerifyValidate verifyValidateMock = mock(VerifyValidate.class);
            when(verifyValidateMock.send())
                    .thenReturn(
                            ResponseFactory
                                    .createObjectFromRawResponse(xmlResponse
                                                    .replaceAll("<verify_check>", "")
                                                    .replaceAll("</verify_check>", ""),
                                            VerifyValidateResponse.class
                                    ).setRawResponse(xmlResponse)
                    );

            VerifyValidateResponse verifyValidateResponse = verifyValidateMock.send();
            assertEquals(verifyValidateResponse.toString(), xmlResponse);
            assertEquals(verifyValidateResponse.getStatus(), "0");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testMalformedResponse() throws IOException {
        try {
            ResponseFactory
                    .createObjectFromRawResponse("malform string", VerifyValidateResponse.class)
                    .setRawResponse("malform string");
            fail();
        } catch (MoceanErrorException ignored) {
        }
    }
}