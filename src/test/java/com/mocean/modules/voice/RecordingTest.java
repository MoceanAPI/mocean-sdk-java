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

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecordingTest {
    @Test
    public void testJsonCall() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertEquals(request.url().queryParameter("mocean-call-uuid"), "xxx-xxx-xxx-xxx");
                assertTrue(request.method().equalsIgnoreCase("get"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/voice/rec"));
                return TestingUtils.getResponse("recording.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        mocean.voice().recording("xxx-xxx-xxx-xxx");
    }

    @Test
    public void testGetter() {
        byte[] testBuffer = new byte[]{'b', 'u', 'f', 'f', 'e', 'r'};
        String testFilename = "test.mp3";
        RecordingResponse recordingResponse = new RecordingResponse(testBuffer, testFilename);

        assertEquals(recordingResponse.getRecordingBuffer(), testBuffer);
        assertEquals(recordingResponse.getFilename(), testFilename);
    }
}
