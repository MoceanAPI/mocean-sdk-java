package com.mocean.modules.account;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.account.mapper.BalanceResponse;
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

public class BalanceTest {
    private Mocean mocean;

    @BeforeEach
    public void setUp() {
        this.mocean = TestingUtils.getMoceanObj();
    }

    @Test
    public void testSetterMethod() {
        Balance balance = this.mocean.balance();
        balance.setRespFormat("json");
        assertNotNull(balance.getParams().get("mocean-resp-format"));
        assertEquals("json", balance.getParams().get("mocean-resp-format"));
    }

    @Test
    public void testJsonInquiry() {
        try {
            String jsonResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "balance.json")), StandardCharsets.UTF_8);

            Balance balanceMock = mock(Balance.class);
            when(balanceMock.inquiry())
                    .thenReturn(
                            ResponseFactory
                                    .createObjectFromRawResponse(jsonResponse, BalanceResponse.class)
                                    .setRawResponse(jsonResponse)
                    );

            BalanceResponse balanceResponse = balanceMock.inquiry();
            assertEquals(balanceResponse.toString(), jsonResponse);
            assertEquals(balanceResponse.getStatus(), "0");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testXmlInquiry() {
        try {
            String xmlResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "balance.xml")), StandardCharsets.UTF_8);

            Balance balanceMock = mock(Balance.class);
            when(balanceMock.inquiry())
                    .thenReturn(
                            ResponseFactory
                                    .createObjectFromRawResponse(xmlResponse, BalanceResponse.class)
                                    .setRawResponse(xmlResponse)
                    );

            BalanceResponse balanceResponse = balanceMock.inquiry();
            assertEquals(balanceResponse.toString(), xmlResponse);
            assertEquals(balanceResponse.getStatus(), "0");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testMalformedResponse() throws IOException {
        try {
            ResponseFactory
                    .createObjectFromRawResponse("malform string", BalanceResponse.class)
                    .setRawResponse("malform string");
            fail();
        } catch (MoceanErrorException ignored) {
        }
    }
}