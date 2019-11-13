package com.mocean.modules.voice;

import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.Transmitter;
import com.mocean.modules.voice.mapper.RecordingResponse;
import com.mocean.system.Mocean;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mock.RuleAnswer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecordingTest {
    @Test
    public void testJsonCall() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertEquals(request.url().queryParameter("mocean-call-uuid"), "xxx-xxx-xxx-xxx");
                assertTrue(request.method().equalsIgnoreCase("get"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/voice/rec"));
                Response.Builder response = TestingUtils.getResponse("recording.json", 200);
                response.removeHeader("content-type");
                response.addHeader("content-type", "audio/mpeg");
                return response;
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        RecordingResponse recordingResponse = mocean.voice().recording("xxx-xxx-xxx-xxx");
        assertEquals(recordingResponse.getFilename(), "xxx-xxx-xxx-xxx.mp3");
        assertNotNull(recordingResponse.getRecordingBuffer());
    }

    @Test
    public void testErrorCall() {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertEquals(request.url().queryParameter("mocean-call-uuid"), "xxx-xxx-xxx-xxx");
                assertTrue(request.method().equalsIgnoreCase("get"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/voice/rec"));
                return TestingUtils.getResponse("error_response.json", 400);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        assertThrows(MoceanErrorException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mocean.voice().recording("xxx-xxx-xxx-xxx");
            }
        });
    }
}
