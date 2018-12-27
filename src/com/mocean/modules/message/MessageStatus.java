package com.mocean.modules.message;

import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.MoceanFactory;

import java.io.IOException;
import java.util.HashMap;

import com.mocean.modules.ResponseHelper;
import com.mocean.modules.Transmitter;
import com.mocean.modules.mapper.MessageStatusResponse;
import com.mocean.system.auth.AuthInterface;

public class MessageStatus extends MoceanFactory {

    public MessageStatus(AuthInterface objAuth) {
        super(objAuth);
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
        Transmitter httpRequest = new Transmitter("/rest/1/report/message", "get", this.params);
        return ResponseHelper.createObjectFromRawResponse(httpRequest.getResponse(), MessageStatusResponse.class)
                .setRawResponse(httpRequest.getResponse());
    }

}
