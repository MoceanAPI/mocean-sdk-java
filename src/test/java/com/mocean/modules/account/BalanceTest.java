package com.mocean.modules.account;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.Transmitter;
import com.mocean.modules.account.mapper.BalanceResponse;
import com.mocean.system.Mocean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    public void testInquiry() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));
                        assertEquals("/account/balance", invocationOnMock.getArgument(1));

                        return new String(Files.readAllBytes(Paths.get("src", "test", "resources", "balance.json")), StandardCharsets.UTF_8);
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        mocean.balance().inquiry();
    }

    @Test
    public void testJsonResponseObject() {
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
            this.testObject(balanceResponse);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testXmlResponseObject() {
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
            this.testObject(balanceResponse);
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

    private void testObject(BalanceResponse balanceResponse) {
        assertEquals(balanceResponse.getStatus(), "0");
        assertEquals(balanceResponse.getValue(), "100.0000");
    }
}