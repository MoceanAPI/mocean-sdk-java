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
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
        mocean.balance().inquiry(new HashMap<String, String>() {{
            put("mocean-resp-format", "json");
        }});

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    @Test
    public void testJsonResponseObject() throws IOException, MoceanErrorException {
        String jsonResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "balance.json")), StandardCharsets.UTF_8);

        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));
                        assertEquals("/account/balance", invocationOnMock.getArgument(1));

                        return transmitterMock.formatResponse(
                                jsonResponse,
                                HttpURLConnection.HTTP_OK,
                                false,
                                "/account/balance"
                        );
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        BalanceResponse balanceResponse = mocean.balance().inquiry();
        assertEquals(balanceResponse.toString(), jsonResponse);
        this.testObject(balanceResponse);

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    @Test
    public void testXmlResponseObject() throws IOException, MoceanErrorException {
        String xmlResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "balance.xml")), StandardCharsets.UTF_8);

        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));
                        assertEquals("/account/balance", invocationOnMock.getArgument(1));

                        return transmitterMock.formatResponse(
                                xmlResponse,
                                HttpURLConnection.HTTP_OK,
                                true,
                                "/account/balance"
                        );
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        BalanceResponse balanceResponse = mocean.balance().inquiry();
        assertEquals(balanceResponse.toString(), xmlResponse);
        this.testObject(balanceResponse);

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    private void testObject(BalanceResponse balanceResponse) {
        assertEquals(balanceResponse.getStatus(), "0");
        assertEquals(balanceResponse.getValue(), "100.0000");
    }
}