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
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mock.RuleAnswer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class VoiceTest {
    @Test
    public void testSetterMethod() throws IOException, RequiredFieldException {
        Voice voice = TestingUtils.getMoceanObj().voice();

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
        voice = TestingUtils.getMoceanObj().voice();
        McccBuilder builderParams = (new McccBuilder()).add(Mccc.say("hello World"));
        voice.setCallControlCommands(builderParams);
        assertNotNull(voice.getParams().get("mocean-call-control-commands"));
        assertEquals(new ObjectMapper().writeValueAsString(builderParams.build()), voice.getParams().get("mocean-call-control-commands"));

        voice = TestingUtils.getMoceanObj().voice();
        AbstractMccc mcccParams = new Say().setText("hello world");
        voice.setCallControlCommands(mcccParams);
        assertNotNull(voice.getParams().get("mocean-call-control-commands"));
        assertEquals(new ObjectMapper().writeValueAsString((new McccBuilder()).add(mcccParams).build()), voice.getParams().get("mocean-call-control-commands"));
    }

    @Test
    public void testRequiredFieldNotSet() {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                return TestingUtils.getResponse("voice.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);

        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mocean.voice().call();
            }
        });
    }

    @Test
    public void testJsonCall() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/voice/dial"));
                return TestingUtils.getResponse("voice.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        VoiceResponse voiceResponse = mocean.voice()
                .call(new HashMap<String, String>() {{
                    put("mocean-to", "testing to");
                }});
        assertEquals(voiceResponse.toString(), TestingUtils.getResponseString("voice.json"));
        this.testObject(voiceResponse);
    }

    @Test
    public void testXmlCall() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/voice/dial"));
                return TestingUtils.getResponse("voice.xml", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        VoiceResponse voiceResponse = mocean.voice()
                .setTo("testing to")
                .setRespFormat("xml")
                .call();
        assertEquals(voiceResponse.toString(), TestingUtils.getResponseString("voice.xml"));
        this.testObject(voiceResponse);
    }

    private void testObject(VoiceResponse voiceResponse) {
        assertEquals(voiceResponse.getStatus(), "0");
        assertEquals(voiceResponse.getCallUuid(), "xxx-xxx-xxx-xxx");
        assertEquals(voiceResponse.getSessionUuid(), "xxx-xxx-xxx-xxx");
    }
}
