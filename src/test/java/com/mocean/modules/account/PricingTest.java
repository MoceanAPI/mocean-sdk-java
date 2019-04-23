package com.mocean.modules.account;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.account.mapper.PricingResponse;
import com.mocean.system.Mocean;
import com.mocean.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PricingTest {
    private Mocean mocean;

    @BeforeEach
    public void setUp() {
        this.mocean = TestingUtils.getMoceanObj();
    }

    @Test
    public void testSetterMethod() {
        Pricing pricing = this.mocean.pricing();

        pricing.setMcc("test mcc");
        assertNotNull(pricing.getParams().get("mocean-mcc"));
        assertEquals("test mcc", pricing.getParams().get("mocean-mcc"));

        pricing.setMnc("test mnc");
        assertNotNull(pricing.getParams().get("mocean-mnc"));
        assertEquals("test mnc", pricing.getParams().get("mocean-mnc"));

        pricing.setDelimiter("test delimiter");
        assertNotNull(pricing.getParams().get("mocean-delimiter"));
        assertEquals("test delimiter", pricing.getParams().get("mocean-delimiter"));

        pricing.setRespFormat("json");
        assertNotNull(pricing.getParams().get("mocean-resp-format"));
        assertEquals("json", pricing.getParams().get("mocean-resp-format"));
    }

    @Test
    public void testJsonInquiry() {
        try {
            String jsonResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "price.json")), StandardCharsets.UTF_8);

            Pricing pricingMock = mock(Pricing.class);
            when(pricingMock.inquiry())
                    .thenReturn(
                            ResponseFactory
                                    .createObjectFromRawResponse(jsonResponse, PricingResponse.class)
                                    .setRawResponse(jsonResponse)
                    );

            PricingResponse pricingResponse = pricingMock.inquiry();
            assertEquals(pricingResponse.toString(), jsonResponse);
            assertEquals(pricingResponse.getStatus(), "0");
            assertTrue(Utils.isArray(pricingResponse.getDestinations()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testXmlInquiry() {
        try {
            String xmlResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "price.xml")), StandardCharsets.UTF_8);

            Pricing pricingMock = mock(Pricing.class);
            when(pricingMock.inquiry())
                    .thenReturn(
                            ResponseFactory
                                    .createObjectFromRawResponse(xmlResponse, PricingResponse.class)
                                    .setRawResponse(xmlResponse)
                    );

            PricingResponse pricingResponse = pricingMock.inquiry();
            assertEquals(pricingResponse.toString(), xmlResponse);
            assertEquals(pricingResponse.getStatus(), "0");
            assertTrue(Utils.isArray(pricingResponse.getDestinations()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testMalformedResponse() throws IOException {
        try {
            ResponseFactory
                    .createObjectFromRawResponse("malform string", PricingResponse.class)
                    .setRawResponse("malform string");
            fail();
        } catch (MoceanErrorException ignored) {
        }
    }
}