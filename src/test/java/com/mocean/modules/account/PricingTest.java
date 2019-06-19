package com.mocean.modules.account;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.Transmitter;
import com.mocean.modules.account.mapper.PricingResponse;
import com.mocean.system.Mocean;
import com.mocean.utils.Utils;
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
        mocean.pricing().inquiry(new HashMap<String, String>() {{
            put("mocean-resp-format", "json");
        }});

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    @Test
    public void testJsonResponseObject() throws IOException, MoceanErrorException {
        String jsonResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "price.json")), StandardCharsets.UTF_8);

        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));
                        assertEquals("/account/pricing", invocationOnMock.getArgument(1));

                        return transmitterMock.formatResponse(
                                jsonResponse,
                                HttpURLConnection.HTTP_OK,
                                false,
                                "/account/pricing"
                        );
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        PricingResponse pricingResponse = mocean.pricing().inquiry();
        assertEquals(pricingResponse.toString(), jsonResponse);
        this.testObject(pricingResponse);

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    @Test
    public void testXmlResponseObject() throws IOException, MoceanErrorException {
        String xmlResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "price.xml")), StandardCharsets.UTF_8);

        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));
                        assertEquals("/account/pricing", invocationOnMock.getArgument(1));

                        return transmitterMock.formatResponse(
                                xmlResponse,
                                HttpURLConnection.HTTP_OK,
                                true,
                                "/account/pricing"
                        );
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        transmitterMock.setTransmitterConfig(transmitterMock.getTransmitterConfig().setVersion("1"));
        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        PricingResponse pricingResponse = mocean.pricing().inquiry();
        assertEquals(pricingResponse.toString(), xmlResponse);
        this.testObject(pricingResponse);

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());


        //v2 test
        String xmlResponseV2 = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "price_v2.xml")), StandardCharsets.UTF_8);

        Transmitter transmitterV2Mock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));
                        assertEquals("/account/pricing", invocationOnMock.getArgument(1));

                        return transmitterV2Mock.formatResponse(
                                xmlResponseV2,
                                HttpURLConnection.HTTP_OK,
                                true,
                                "/account/pricing"
                        );
                    }
                }
        ).when(transmitterV2Mock).send(anyString(), anyString(), any());

        transmitterV2Mock.setTransmitterConfig(transmitterV2Mock.getTransmitterConfig().setVersion("2"));
        mocean = TestingUtils.getMoceanObj(transmitterV2Mock);
        pricingResponse = mocean.pricing().inquiry();
        assertEquals(pricingResponse.toString(), xmlResponseV2);
        this.testObject(pricingResponse);

        verify(transmitterV2Mock, times(1)).send(anyString(), anyString(), any());
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