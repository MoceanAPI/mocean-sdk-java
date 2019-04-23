package com.mocean.modules.numberlookup;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.numberlookup.mapper.NumberLookupResponse;
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

public class NumberLookupTest {
    private Mocean mocean;

    @BeforeEach
    public void setUp() {
        this.mocean = TestingUtils.getMoceanObj();
    }

    @Test
    public void testSetterMethod() {
        NumberLookup numberLookup = this.mocean.numberLookup();

        numberLookup.setTo("test to");
        assertNotNull(numberLookup.getParams().get("mocean-to"));
        assertEquals("test to", numberLookup.getParams().get("mocean-to"));

        numberLookup.setNlUrl("test nl url");
        assertNotNull(numberLookup.getParams().get("mocean-nl-url"));
        assertEquals("test nl url", numberLookup.getParams().get("mocean-nl-url"));

        numberLookup.setRespFormat("json");
        assertNotNull(numberLookup.getParams().get("mocean-resp-format"));
        assertEquals("json", numberLookup.getParams().get("mocean-resp-format"));
    }

    @Test
    public void testJsonInquiry() {
        try {
            String jsonResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "number_lookup.json")), StandardCharsets.UTF_8);

            NumberLookup numberLookupMock = mock(NumberLookup.class);
            when(numberLookupMock.inquiry())
                    .thenReturn(
                            ResponseFactory
                                    .createObjectFromRawResponse(jsonResponse, NumberLookupResponse.class)
                                    .setRawResponse(jsonResponse)
                    );

            NumberLookupResponse numberLookupResponse = numberLookupMock.inquiry();
            assertEquals(numberLookupResponse.toString(), jsonResponse);
            assertEquals(numberLookupResponse.getStatus(), "0");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testXmlInquiry() {
        try {
            String xmlResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "number_lookup.xml")), StandardCharsets.UTF_8);

            NumberLookup numberLookupMock = mock(NumberLookup.class);
            when(numberLookupMock.inquiry())
                    .thenReturn(
                            ResponseFactory
                                    .createObjectFromRawResponse(xmlResponse, NumberLookupResponse.class)
                                    .setRawResponse(xmlResponse)
                    );

            NumberLookupResponse numberLookupResponse = numberLookupMock.inquiry();
            assertEquals(numberLookupResponse.toString(), xmlResponse);
            assertEquals(numberLookupResponse.getStatus(), "0");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testMalformedResponse() throws IOException {
        try {
            ResponseFactory
                    .createObjectFromRawResponse("malform string", NumberLookupResponse.class)
                    .setRawResponse("malform string");
            fail();
        } catch (MoceanErrorException ignored) {
        }
    }
}