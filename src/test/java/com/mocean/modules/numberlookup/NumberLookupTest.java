package com.mocean.modules.numberlookup;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.Transmitter;
import com.mocean.modules.numberlookup.mapper.NumberLookupResponse;
import com.mocean.system.Mocean;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mock.RuleAnswer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class NumberLookupTest {
    @Test
    public void testSetterMethod() {
        NumberLookup numberLookup = TestingUtils.getMoceanObj().numberLookup();

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
    public void testRequiredFieldNotSet() {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                return TestingUtils.getResponse("number_lookup.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);

        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mocean.numberLookup().inquiry();
            }
        });
    }

    @Test
    public void testJsonInquiry() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/nl"));
                return TestingUtils.getResponse("number_lookup.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        NumberLookupResponse numberLookupResponse = mocean.numberLookup()
                .inquiry(new HashMap<String, String>() {{
                    put("mocean-to", "testing to");
                }});
        assertEquals(numberLookupResponse.toString(), TestingUtils.getResponseString("number_lookup.json"));
        this.testObject(numberLookupResponse);
    }

    @Test
    public void testXmlResponseObject() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/nl"));
                return TestingUtils.getResponse("number_lookup.xml", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        NumberLookupResponse numberLookupResponse = mocean.numberLookup()
                .setTo("testing to")
                .setRespFormat("xml")
                .inquiry();
        assertEquals(numberLookupResponse.toString(), TestingUtils.getResponseString("number_lookup.xml"));
        this.testObject(numberLookupResponse);
    }

    private void testObject(NumberLookupResponse numberLookupResponse) {
        assertEquals(numberLookupResponse.getStatus(), "0");
        assertEquals(numberLookupResponse.getMsgId(), "CPASS_restapi_C00000000000000.0002");
        assertEquals(numberLookupResponse.getTo(), "60123456789");
        assertEquals(numberLookupResponse.getPorted(), "ported");
        assertEquals(numberLookupResponse.getReachable(), "reachable");
        assertEquals(numberLookupResponse.getCurrentCarrier().getCountry(), "MY");
        assertEquals(numberLookupResponse.getCurrentCarrier().getName(), "U Mobile");
        assertEquals(numberLookupResponse.getCurrentCarrier().getNetworkCode(), "50218");
        assertEquals(numberLookupResponse.getCurrentCarrier().getMcc(), "502");
        assertEquals(numberLookupResponse.getCurrentCarrier().getMnc(), "18");
        assertEquals(numberLookupResponse.getOriginalCarrier().getCountry(), "MY");
        assertEquals(numberLookupResponse.getOriginalCarrier().getName(), "Maxis Mobile");
        assertEquals(numberLookupResponse.getOriginalCarrier().getNetworkCode(), "50212");
        assertEquals(numberLookupResponse.getOriginalCarrier().getMcc(), "502");
        assertEquals(numberLookupResponse.getOriginalCarrier().getMnc(), "12");
    }
}