package com.mocean.modules.voice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mocean.exception.MoceanErrorException;
import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.AbstractClient;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.Transmitter;
import com.mocean.modules.voice.mapper.HangupResponse;
import com.mocean.modules.voice.mapper.VoiceResponse;
import com.mocean.modules.voice.mc.AbstractMc;
import com.mocean.system.auth.AuthInterface;

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

    private VoiceResponse send() throws MoceanErrorException, IOException {
        this.createFinalParams();
        this.isRequiredFieldsSet();

        String responseStr = this.transmitter.post("/voice/dial", this.params);
        return ResponseFactory.createObjectFromRawResponse(responseStr, VoiceResponse.class)
                .setRawResponse(this.transmitter.getRawResponse());
    }
}
