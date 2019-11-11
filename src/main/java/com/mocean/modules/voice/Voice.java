package com.mocean.modules.voice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mocean.exception.MoceanErrorException;
import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.AbstractClient;
import com.mocean.modules.ErrorResponse;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.Transmitter;
import com.mocean.modules.voice.mapper.HangupResponse;
import com.mocean.modules.voice.mapper.RecordingResponse;
import com.mocean.modules.voice.mapper.VoiceResponse;
import com.mocean.modules.voice.mc.AbstractMc;
import com.mocean.system.auth.AuthInterface;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Voice extends AbstractClient {
    public Voice(AuthInterface objAuth, Transmitter transmitter) {
        super(objAuth, transmitter);
        this.requiredFields = new String[]{"mocean-api-key", "mocean-api-secret", "mocean-to"};
    }

    public Voice setTo(String to) {
        this.params.put("mocean-to", to);
        return this;
    }

    public Voice setEventUrl(String eventUrl) {
        this.params.put("mocean-event-url", eventUrl);
        return this;
    }

    public Voice setMoceanCommand(HashMap<String, Object> moceanCommand) throws IOException {
        this.params.put("mocean-command", new ObjectMapper().writeValueAsString(
                new ArrayList<HashMap<String, Object>>() {{
                    add(moceanCommand);
                }}
        ));
        return this;
    }

    public Voice setMoceanCommand(AbstractMc moceanCommand) throws IOException, RequiredFieldException {
        McBuilder builder = new McBuilder() {{
            add(moceanCommand);
        }};
        return this.setMoceanCommand(builder);
    }

    public Voice setMoceanCommand(McBuilder moceanCommand) throws IOException, RequiredFieldException {
        this.params.put("mocean-command", new ObjectMapper().writeValueAsString(moceanCommand.build()));
        return this;
    }

    public Voice setRespFormat(String param) {
        this.params.put("mocean-resp-format", param);
        return this;
    }

    public VoiceResponse call() throws MoceanErrorException, IOException {
        return this.send();
    }

    public VoiceResponse call(HashMap<String, String> params) throws MoceanErrorException, IOException {
        this.create(params);
        return this.send();
    }

    public HangupResponse hangup(String callUuid) throws MoceanErrorException, IOException {
        //override requiredField for hangup
        this.requiredFields = new String[]{"mocean-api-key", "mocean-api-secret"};

        this.createFinalParams();
        this.isRequiredFieldsSet();

        String responseStr = this.transmitter.post("/voice/hangup/" + callUuid, new HashMap<>());
        return ResponseFactory.createObjectFromRawResponse(responseStr, HangupResponse.class)
                .setRawResponse(this.transmitter.getRawResponse());
    }

    public RecordingResponse recording(String callUuid) throws MoceanErrorException, IOException {
        //override requiredField for recording
        this.requiredFields = new String[]{"mocean-api-key", "mocean-api-secret", "mocean-call-uuid"};

        this.create(new HashMap<String, String>() {{
            put("mocean-call-uuid", callUuid);
        }});
        this.createFinalParams();
        this.isRequiredFieldsSet();

        String uri = "/voice/rec";

        Response response = this.transmitter.send("get", uri, this.params);
        if ("audio/mpeg".equalsIgnoreCase(response.header("content-type"))) {
            byte[] byteBody = response.body().bytes();
            response.close();

            return new RecordingResponse(byteBody, callUuid + ".mp3");
        }

        //this method will throw exception if there's error
        throw new MoceanErrorException(
                ResponseFactory.createObjectFromRawResponse(
                        response.body().string(),
                        ErrorResponse.class
                ).setRawResponse(response.body().string())
        );
    }

    private VoiceResponse send() throws MoceanErrorException, IOException {
        this.createFinalParams();
        this.isRequiredFieldsSet();

        String responseStr = this.transmitter.post("/voice/dial", this.params);
        return ResponseFactory.createObjectFromRawResponse(responseStr, VoiceResponse.class)
                .setRawResponse(this.transmitter.getRawResponse());
    }
}
