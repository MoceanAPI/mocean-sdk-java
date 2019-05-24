package com.mocean.modules.account;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.Transmitter;
import com.mocean.modules.account.mapper.PricingResponse;
import com.mocean.system.Mocean;
import com.mocean.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    public void testInquiry() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));
                        assertEquals("/account/pricing", invocationOnMock.getArgument(1));

                        return new String(Files.readAllBytes(Paths.get("src", "test", "resources", "price.json")), StandardCharsets.UTF_8);
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        mocean.pricing().inquiry();
    }

    @Test
    public void testJsonResponseObject() {
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
            this.testObject(pricingResponse);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testXmlResponseObject() {
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
            this.testObject(pricingResponse);
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

    private void testObject(PricingResponse pricingResponse) {
        assertEquals(pricingResponse.getStatus(), "0");
        assertEquals(pricingResponse.getDestinations().length, 25);
        assertEquals(pricingResponse.getDestinations()[0].getCountry(), "Default");
        assertEquals(pricingResponse.getDestinations()[0].getOperator(), "Default");
        assertEquals(pricingResponse.getDestinations()[0].getMcc(), "Default");
        assertEquals(pricingResponse.getDestinations()[0].getMnc(), "Default");
        assertEquals(pricingResponse.getDestinations()[0].getPrice(), "2.0000");
        assertEquals(pricingResponse.getDestinations()[0].getCurrency(), "MYR");
        assertTrue(Utils.isArray(pricingResponse.getDestinations()));
    }
}