package com.mocean.modules;

import com.mocean.exception.MoceanErrorException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TransmitterTest {
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