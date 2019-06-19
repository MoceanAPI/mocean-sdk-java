package com.mocean.modules.message;

import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.AbstractClient;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.Transmitter;
import com.mocean.modules.message.mapper.MessageStatusResponse;
import com.mocean.system.auth.AuthInterface;

import java.io.IOException;
import java.util.HashMap;

public class MessageStatus extends AbstractClient {

    public MessageStatus(AuthInterface objAuth, Transmitter transmitter) {
        super(objAuth, transmitter);
        this.requiredFields = new String[]{"mocean-api-key", "mocean-api-secret", "mocean-msgid"};
    }

    public MessageStatus setMsgid(String param) {
        this.params.put("mocean-msgid", param);
        return this;
    }

    public MessageStatus setRespFormat(String param) {
        this.params.put("mocean-resp-format", param);
        return this;
    }

    public MessageStatusResponse inquiry(HashMap<String, String> params) throws MoceanErrorException, IOException {
        super.create(params);
        return this.send();
    }

    public MessageStatusResponse inquiry() throws MoceanErrorException, IOException {
        return this.send();
    }

    private MessageStatusResponse send() throws MoceanErrorException, IOException {
        this.createFinalParams();
        this.isRequiredFieldsSet();

        String responseStr = this.transmitter.get("/report/message", this.params);
        return ResponseFactory.createObjectFromRawResponse(responseStr, MessageStatusResponse.class)
                .setRawResponse(this.transmitter.getRawResponse());
    }

}
