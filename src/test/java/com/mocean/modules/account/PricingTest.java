package com.mocean.modules.account;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.Transmitter;
import com.mocean.modules.account.mapper.PricingResponse;
import com.mocean.system.Mocean;
import com.mocean.utils.Utils;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mock.RuleAnswer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PricingTest {
    @Test
    public void testSetterMethod() {
        Pricing pricing = TestingUtils.getMoceanObj().pricing();

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
    public void testJsonInquiry() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("get"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/account/pricing"));
                return TestingUtils.getResponse("price.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        PricingResponse pricingResponse = mocean.pricing()
                .inquiry(new HashMap<String, String>() {{
                    put("mocean-resp-format", "json");
                }});
        assertEquals(pricingResponse.toString(), TestingUtils.getResponseString("price.json"));
        this.testObject(pricingResponse);
    }

    @Test
    public void testXmlInquiry() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("get"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("1", "/account/pricing"));
                return TestingUtils.getResponse("price.xml", 200);
            }
        }));

        transmitterMock.setTransmitterConfig(transmitterMock.getTransmitterConfig().setVersion("1"));
        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        PricingResponse pricingResponse = mocean.pricing()
                .setRespFormat("xml")
                .inquiry();
        assertEquals(pricingResponse.toString(), TestingUtils.getResponseString("price.xml"));
        this.testObject(pricingResponse);

        //v2 test
        Transmitter transmitterV2Mock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                return TestingUtils.getResponse("price_v2.xml", 200);
            }
        }));

        transmitterV2Mock.setTransmitterConfig(transmitterV2Mock.getTransmitterConfig().setVersion("2"));
        mocean = TestingUtils.getMoceanObj(transmitterV2Mock);
        pricingResponse = mocean.pricing().inquiry(new HashMap<String, String>() {{
            put("mocean-resp-format", "xml");
        }});
        assertEquals(pricingResponse.toString(), TestingUtils.getResponseString("price_v2.xml"));
        this.testObject(pricingResponse);
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