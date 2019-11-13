package com.mocean.modules.voice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.Transmitter;
import com.mocean.modules.voice.mapper.HangupResponse;
import com.mocean.modules.voice.mapper.VoiceResponse;
import com.mocean.modules.voice.mc.AbstractMc;
import com.mocean.modules.voice.mc.Say;
import com.mocean.system.Mocean;
import com.mocean.utils.Utils;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mock.RuleAnswer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VoiceTest {
    @Test
    public void testSetterMethod() throws IOException, RequiredFieldException {
        Voice voice = TestingUtils.getMoceanObj().voice();

        voice.setTo("test to");
        assertNotNull(voice.getParams().get("mocean-to"));
        assertEquals("test to", voice.getParams().get("mocean-to"));

        voice.setEventUrl("test event url");
        assertNotNull(voice.getParams().get("mocean-event-url"));
        assertEquals("test event url", voice.getParams().get("mocean-event-url"));

        voice.setRespFormat("json");
        assertNotNull(voice.getParams().get("mocean-resp-format"));
        assertEquals("json", voice.getParams().get("mocean-resp-format"));

        HashMap<String, Object> hashMapParams = new HashMap<String, Object>() {{
            put("action", "say");
        }};
        voice.setMoceanCommand(hashMapParams);
        assertNotNull(voice.getParams().get("mocean-command"));
        assertEquals(new ObjectMapper().writeValueAsString(
                new ArrayList<HashMap<String, Object>>() {{
                    add(hashMapParams);
                }}
        ), voice.getParams().get("mocean-command"));

        //test overloading method
        voice = TestingUtils.getMoceanObj().voice();
        McBuilder builderParams = (new McBuilder()).add(Mc.say("hello World"));
        voice.setMoceanCommand(builderParams);
        assertNotNull(voice.getParams().get("mocean-command"));
        assertEquals(new ObjectMapper().writeValueAsString(builderParams.build()), voice.getParams().get("mocean-command"));

        voice = TestingUtils.getMoceanObj().voice();
        AbstractMc mcParams = new Say().setText("hello world");
        voice.setMoceanCommand(mcParams);
        assertNotNull(voice.getParams().get("mocean-command"));
        assertEquals(new ObjectMapper().writeValueAsString((new McBuilder()).add(mcParams).build()), voice.getParams().get("mocean-command"));
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

    @Test
    public void testJsonHangup() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                HashMap<String, String> mapBody = TestingUtils.rewindBody((FormBody) request.body());
                assertEquals(mapBody.get("mocean-call-uuid"), "xxx-xxx-xxx-xxx");
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/voice/hangup"));
                return TestingUtils.getResponse("hangup.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        HangupResponse hangupResponse = mocean.voice()
                .hangup("xxx-xxx-xxx-xxx");
        assertEquals(hangupResponse.toString(), TestingUtils.getResponseString("hangup.json"));
        assertEquals(hangupResponse.getStatus(), "0");
    }

    @Test
    public void testXmlHangup() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                HashMap<String, String> mapBody = TestingUtils.rewindBody((FormBody) request.body());
                assertEquals(mapBody.get("mocean-call-uuid"), "xxx-xxx-xxx-xxx");
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/voice/hangup"));
                return TestingUtils.getResponse("hangup.xml", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        HangupResponse hangupResponse = mocean.voice()
                .hangup("xxx-xxx-xxx-xxx");
        assertEquals(hangupResponse.toString(), TestingUtils.getResponseString("hangup.xml"));
        assertEquals(hangupResponse.getStatus(), "0");
    }

    private void testObject(VoiceResponse voiceResponse) {
        assertTrue(Utils.isArray(voiceResponse.getCalls()));
        assertEquals(voiceResponse.getCalls()[0].getStatus(), "0");
        assertEquals(voiceResponse.getCalls()[0].getReceiver(), "60123456789");
        assertEquals(voiceResponse.getCalls()[0].getCallUuid(), "xxx-xxx-xxx-xxx");
        assertEquals(voiceResponse.getCalls()[0].getSessionUuid(), "xxx-xxx-xxx-xxx");
    }
}
