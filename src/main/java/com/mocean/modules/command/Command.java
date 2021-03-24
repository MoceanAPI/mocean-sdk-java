package com.mocean.modules.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mocean.exception.MoceanErrorException;
import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.*;
import com.mocean.modules.command.mapper.SendMessageResponse;
import com.mocean.modules.command.mc.AbstractMc;
import com.mocean.system.auth.AuthInterface;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Command extends AbstractClient {
    public Command(AuthInterface objAuth, Transmitter transmitter) {
        super(objAuth, transmitter);
        this.requiredFields = new String[]{"mocean-api-key", "mocean-api-secret", "mocean-command"};
    }


    public Command setEventUrl(String eventUrl) {
        this.params.put("mocean-event-url", eventUrl);
        return this;
    }

    public Command setMoceanCommand(McBuilder moceanCommand) throws IOException, RequiredFieldException {
        this.params.put("mocean-command", new ObjectMapper().writeValueAsString(moceanCommand.build()));
        return this;
    }

    public Command setRespFormat(String param) {
        this.params.put("mocean-resp-format", param);
        return this;
    }

    public SendMessageResponse execute() throws RequiredFieldException, MoceanErrorException, IOException {

        this.createFinalParams();
        this.isRequiredFieldsSet();

        String responseStr = this.transmitter.post("/send-message", this.params);
        return ResponseFactory.createObjectFromRawResponse(responseStr, SendMessageResponse.class)
                .setRawResponse(this.transmitter.getRawResponse());
    }

}
