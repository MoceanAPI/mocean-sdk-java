package com.mocean.modules.message;

import com.mocean.system.Client;

import java.util.HashMap;

import com.mocean.modules.Transmitter;

public class MessageStatus extends com.mocean.modules.MoceanFactory {

    public MessageStatus(Client client) {
        super(client);
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

    public String inquiry(HashMap<String, String> params) throws Exception {
        super.create(params);
        return this.send();
    }

    public String inquiry() throws Exception {
        return this.send();
    }

    private String send() throws Exception {
        this.createFinalParams();
        this.isRequiredFieldsSet();
        Transmitter httpRequest = new Transmitter("/rest/1/report/message", "get", this.params);
        return httpRequest.getResponse();
    }

}
