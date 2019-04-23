package com.mocean.modules.message;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.message.mapper.VerifyRequestResponse;
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

public class VerifyRequestTest {
    private Mocean mocean;

    @BeforeEach
    public void setUp() {
        this.mocean = TestingUtils.getMoceanObj();
    }

    @Test
    public void testSetterMethod() {
        VerifyRequest verifyRequest = this.mocean.verifyRequest();

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

        verifyRequest.setRespFormat("json");
        assertNotNull(verifyRequest.getParams().get("mocean-resp-format"));
        assertEquals("json", verifyRequest.getParams().get("mocean-resp-format"));
    }

    @Test
    public void testJsonSend() {
        try {
            String jsonResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "send_code.json")), StandardCharsets.UTF_8);

            VerifyRequest verifyRequestMock = mock(VerifyRequest.class);
            when(verifyRequestMock.send())
                    .thenReturn(
                            ResponseFactory
                                    .createObjectFromRawResponse(jsonResponse, VerifyRequestResponse.class)
                                    .setRawResponse(jsonResponse)
                    );

            VerifyRequestResponse verifyRequestResponse = verifyRequestMock.send();
            assertEquals(verifyRequestResponse.toString(), jsonResponse);
            assertEquals(verifyRequestResponse.getStatus(), "0");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testXmlSend() {
        try {
            String xmlResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "send_code.xml")), StandardCharsets.UTF_8);

            VerifyRequest verifyRequestMock = mock(VerifyRequest.class);
            when(verifyRequestMock.send())
                    .thenReturn(
                            ResponseFactory
                                    .createObjectFromRawResponse(xmlResponse
                                                    .replaceAll("<verify_request>", "")
                                                    .replaceAll("</verify_request>", ""),
                                            VerifyRequestResponse.class
                                    ).setRawResponse(xmlResponse)
                    );

            VerifyRequestResponse verifyRequestResponse = verifyRequestMock.send();
            assertEquals(verifyRequestResponse.toString(), xmlResponse);
            assertEquals(verifyRequestResponse.getStatus(), "0");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testMalformedResponse() throws IOException {
        try {
            ResponseFactory
                    .createObjectFromRawResponse("malform string", VerifyRequestResponse.class)
                    .setRawResponse("malform string");
            fail();
        } catch (MoceanErrorException ignored) {
        }
    }
}