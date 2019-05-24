package com.mocean.modules;

import com.mocean.exception.MoceanErrorException;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransmitterTest {
    @Test
    public void testGetMethod() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));

                        return "testing only";
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        assertEquals("testing only", transmitterMock.get("test uri", new HashMap<>()));
    }

    @Test
    public void testPostMethod() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("post", invocationOnMock.getArgument(0));

                        return "testing only";
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        assertEquals("testing only", transmitterMock.post("test uri", new HashMap<>()));
    }
}