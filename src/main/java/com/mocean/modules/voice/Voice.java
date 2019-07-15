package com.mocean.modules.voice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mocean.exception.MoceanErrorException;
import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.AbstractClient;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.Transmitter;
import com.mocean.modules.voice.mapper.VoiceResponse;
import com.mocean.modules.voice.mccc.AbstractMccc;
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

    public Voice setCallEventUrl(String callEventUrl) {
        this.params.put("mocean-call-event-url", callEventUrl);
        return this;
    }

    public Voice setCallControlCommands(HashMap<String, Object> callControlCommands) throws IOException {
        this.params.put("mocean-call-control-commands", new ObjectMapper().writeValueAsString(
                new ArrayList<HashMap<String, Object>>() {{
                    add(callControlCommands);
                }}
        ));
        return this;
    }

    public Voice setCallControlCommands(AbstractMccc callControlCommands) throws IOException, RequiredFieldException {
        McccBuilder builder = new McccBuilder() {{
            add(callControlCommands);
        }};
        return this.setCallControlCommands(builder);
    }

    public Voice setCallControlCommands(McccBuilder callControlCommands) throws IOException, RequiredFieldException {
        this.params.put("mocean-call-control-commands", new ObjectMapper().writeValueAsString(callControlCommands.build()));
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

    private VoiceResponse send() throws MoceanErrorException, IOException {
        this.createFinalParams();
        this.isRequiredFieldsSet();

        String responseStr = this.transmitter.get("/voice/dial", this.params);
        return ResponseFactory.createObjectFromRawResponse(responseStr, VoiceResponse.class)
                .setRawResponse(this.transmitter.getRawResponse());
    }
}
