package com.mocean.modules.account;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.Transmitter;
import com.mocean.modules.account.mapper.BalanceResponse;
import com.mocean.system.Mocean;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mock.RuleAnswer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class BalanceTest {
    @Test
    public void testSetterMethod() {
        Balance balance = TestingUtils.getMoceanObj().balance();
        balance.setRespFormat("json");
        assertNotNull(balance.getParams().get("mocean-resp-format"));
        assertEquals("json", balance.getParams().get("mocean-resp-format"));
    }

    @Test
    public void testJsonInquiry() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("get"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/account/balance"));
                return TestingUtils.getResponse("balance.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        BalanceResponse balanceResponse = mocean.balance()
                .inquiry(new HashMap<String, String>() {{
                    put("mocean-resp-format", "json");
                }});
        assertEquals(balanceResponse.toString(), TestingUtils.getResponseString("balance.json"));
        this.testObject(balanceResponse);
    }

    @Test
    public void testXmlInquiry() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("get"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/account/balance"));
                return TestingUtils.getResponse("balance.xml", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        BalanceResponse balanceResponse = mocean.balance()
                .setRespFormat("xml")
                .inquiry();
        assertEquals(balanceResponse.toString(), TestingUtils.getResponseString("balance.xml"));
        this.testObject(balanceResponse);
    }

    private void testObject(BalanceResponse balanceResponse) {
        assertEquals(balanceResponse.getStatus(), "0");
        assertEquals(balanceResponse.getValue(), "100.0000");
    }
}