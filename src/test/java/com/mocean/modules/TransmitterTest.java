package com.mocean.modules;

import com.mocean.exception.MoceanErrorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransmitterTest {
    @Test
    public void testGetMethod() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));

                        return "testing only";
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        assertEquals("testing only", transmitterMock.get("test uri", new HashMap<>()));

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    @Test
    public void testPostMethod() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("post", invocationOnMock.getArgument(0));

                        return "testing only";
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        assertEquals("testing only", transmitterMock.post("test uri", new HashMap<>()));

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    //this is test for v1
    @Test
    public void testErrorResponseWith2xxStatusCode() throws IOException {
        String jsonErrorResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "error_response.json")), StandardCharsets.UTF_8);
        Transmitter transmitter = new Transmitter();

        try {
            transmitter.formatResponse(jsonErrorResponse, 202, false, null);
            fail();
        } catch (MoceanErrorException ex) {
            assertEquals(ex.getMessage(), ex.getErrorResponse().toString());
            assertEquals(jsonErrorResponse, ex.getErrorResponse().toString());
            assertEquals(ex.getErrorResponse().getStatus(), "1");
        }

        try {
            transmitter.formatResponse(jsonErrorResponse, 200, false, null);
        } catch (MoceanErrorException ex) {
            assertEquals(ex.getMessage(), ex.getErrorResponse().toString());
            assertEquals(jsonErrorResponse, ex.getErrorResponse().toString());
            assertEquals(ex.getErrorResponse().getStatus(), "1");
        }

        String xmlErrorResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "error_response.json")), StandardCharsets.UTF_8);

        try {
            transmitter.formatResponse(xmlErrorResponse, 202, false, null);
        } catch (MoceanErrorException ex) {
            assertEquals(ex.getMessage(), ex.getErrorResponse().toString());
            assertEquals(xmlErrorResponse, ex.getErrorResponse().toString());
            assertEquals(ex.getErrorResponse().getStatus(), "1");
        }

        try {
            transmitter.formatResponse(xmlErrorResponse, 200, false, null);
        } catch (MoceanErrorException ex) {
            assertEquals(ex.getMessage(), ex.getErrorResponse().toString());
            assertEquals(xmlErrorResponse, ex.getErrorResponse().toString());
            assertEquals(ex.getErrorResponse().getStatus(), "1");
        }
    }

    @Test
    public void testErrorResponseWith4xxStatusCode() throws IOException {
        String errorResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "error_response.json")), StandardCharsets.UTF_8);
        Transmitter transmitter = new Transmitter();

        try {
            transmitter.formatResponse(errorResponse, 400, false, null);
        } catch (MoceanErrorException ex) {
            assertEquals(ex.getMessage(), ex.getErrorResponse().toString());
            assertEquals(errorResponse, ex.getErrorResponse().toString());
            assertEquals(ex.getErrorResponse().getStatus(), "1");
            assertEquals(ex.getErrorResponse().getErrMsg(), "Authorization failed");
        }
    }

    @Test
    public void testUrlEncodeUTF8() throws IOException {
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("Testing Only", "Hello World,");
        testMap.put("Another Test", "World, Hello");

        String encodedContent = (new Transmitter()).urlEncodeUTF8(testMap);
        assertEquals(encodedContent, "Another+Test=World%2C+Hello&Testing+Only=Hello+World%2C");
    }
}