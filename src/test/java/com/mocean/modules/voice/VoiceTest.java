package com.mocean.modules.voice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.Transmitter;
import com.mocean.modules.voice.mapper.VoiceResponse;
import com.mocean.modules.voice.mccc.AbstractMccc;
import com.mocean.modules.voice.mccc.Say;
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
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doAnswer;

public class VoiceTest {
    private Mocean mocean;

    @BeforeEach
    public void setUp() {
        this.mocean = TestingUtils.getMoceanObj();
    }

    @Test
    public void testSetterMethod() throws IOException, RequiredFieldException {
        Voice voice = this.mocean.voice();

        voice.setTo("test to");
        assertNotNull(voice.getParams().get("mocean-to"));
        assertEquals("test to", voice.getParams().get("mocean-to"));

        voice.setCallEventUrl("test call event url");
        assertNotNull(voice.getParams().get("mocean-call-event-url"));
        assertEquals("test call event url", voice.getParams().get("mocean-call-event-url"));

        voice.setRespFormat("json");
        assertNotNull(voice.getParams().get("mocean-resp-format"));
        assertEquals("json", voice.getParams().get("mocean-resp-format"));

        HashMap<String, Object> hashMapParams = new HashMap<String, Object>() {{
            put("action", "say");
        }};
        voice.setCallControlCommands(hashMapParams);
        assertNotNull(voice.getParams().get("mocean-call-control-commands"));
        assertEquals(new ObjectMapper().writeValueAsString(
                new ArrayList<HashMap<String, Object>>() {{
                    add(hashMapParams);
                }}
        ), voice.getParams().get("mocean-call-control-commands"));

        //test overloading method
        voice = this.mocean.voice();
        McccBuilder builderParams = (new McccBuilder()).add(Mccc.say("hello World"));
        voice.setCallControlCommands(builderParams);
        assertNotNull(voice.getParams().get("mocean-call-control-commands"));
        assertEquals(new ObjectMapper().writeValueAsString(builderParams.build()), voice.getParams().get("mocean-call-control-commands"));

        voice = this.mocean.voice();
        AbstractMccc mcccParams = new Say().setText("hello world");
        voice.setCallControlCommands(mcccParams);
        assertNotNull(voice.getParams().get("mocean-call-control-commands"));
        assertEquals(new ObjectMapper().writeValueAsString((new McccBuilder()).add(mcccParams).build()), voice.getParams().get("mocean-call-control-commands"));
    }

    @Test
    public void testCall() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));
                        assertEquals("/voice/dial", invocationOnMock.getArgument(1));

                        return new String(Files.readAllBytes(Paths.get("src", "test", "resources", "voice.json")), StandardCharsets.UTF_8);
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);

        //test is required field set
        try {
            mocean.voice().call();
            fail();
        } catch (RequiredFieldException ex) {
        }

        mocean.voice()
                .call(new HashMap<String, String>() {{
                    put("mocean-to", "testing to");
                }});

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    @Test
    public void testJsonResponseObject() throws IOException, MoceanErrorException {
        String jsonResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "voice.json")), StandardCharsets.UTF_8);

        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));
                        assertEquals("/voice/dial", invocationOnMock.getArgument(1));

                        return transmitterMock.formatResponse(
                                jsonResponse,
                                HttpURLConnection.HTTP_OK,
                                false,
                                "/voice/dial"
                        );
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        VoiceResponse voiceResponse = mocean.voice()
                .setTo("testing to")
                .call();
        assertEquals(voiceResponse.toString(), jsonResponse);
        this.testObject(voiceResponse);

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    @Test
    public void testXmlResponseObject() throws IOException, MoceanErrorException {
        String xmlResponse = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "voice.xml")), StandardCharsets.UTF_8);

        Transmitter transmitterMock = spy(Transmitter.class);
        doAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                        assertEquals("get", invocationOnMock.getArgument(0));
                        assertEquals("/voice/dial", invocationOnMock.getArgument(1));

                        return transmitterMock.formatResponse(
                                xmlResponse,
                                HttpURLConnection.HTTP_OK,
                                true,
                                "/voice/dial"
                        );
                    }
                }
        ).when(transmitterMock).send(anyString(), anyString(), any());

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        VoiceResponse voiceResponse = mocean.voice()
                .setTo("testing to")
                .call();
        assertEquals(voiceResponse.toString(), xmlResponse);
        this.testObject(voiceResponse);

        verify(transmitterMock, times(1)).send(anyString(), anyString(), any());
    }

    private void testObject(VoiceResponse voiceResponse) {
        assertEquals(voiceResponse.getStatus(), "0");
        assertEquals(voiceResponse.getCallUuid(), "xxx-xxx-xxx-xxx");
        assertEquals(voiceResponse.getSessionUuid(), "xxx-xxx-xxx-xxx");
    }
}
