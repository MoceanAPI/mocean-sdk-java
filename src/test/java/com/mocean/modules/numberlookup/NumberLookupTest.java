package com.mocean.modules.numberlookup;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.Transmitter;
import com.mocean.modules.numberlookup.mapper.NumberLookupResponse;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doAnswer;

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
    public void testInquiry() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));
                        assertEquals("/nl", invocationOnMock.getArgument(1));

                        return new String(Files.readAllBytes(Paths.get("src", "test", "resources", "number_lookup.json")), StandardCharsets.UTF_8);
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        mocean.numberLookup()
                .setTo("testing to")
                .inquiry();

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    @Test
    public void testJsonResponseObject() throws IOException, MoceanErrorException {
        String jsonResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "number_lookup.json")), StandardCharsets.UTF_8);

        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));
                        assertEquals("/nl", invocationOnMock.getArgument(1));

                        return transmitterMock.formatResponse(
                                jsonResponse,
                                HttpURLConnection.HTTP_OK,
                                false,
                                "/nl"
                        );
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        NumberLookupResponse numberLookupResponse = mocean.numberLookup()
                .setTo("testing to")
                .inquiry();
        assertEquals(numberLookupResponse.toString(), jsonResponse);
        this.testObject(numberLookupResponse);

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    @Test
    public void testXmlResponseObject() throws IOException, MoceanErrorException {
        String xmlResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "number_lookup.xml")), StandardCharsets.UTF_8);

        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));
                        assertEquals("/nl", invocationOnMock.getArgument(1));

                        return transmitterMock.formatResponse(
                                xmlResponse,
                                HttpURLConnection.HTTP_OK,
                                true,
                                "/nl"
                        );
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        NumberLookupResponse numberLookupResponse = mocean.numberLookup()
                .setTo("testing to")
                .inquiry();
        assertEquals(numberLookupResponse.toString(), xmlResponse);
        this.testObject(numberLookupResponse);

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
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