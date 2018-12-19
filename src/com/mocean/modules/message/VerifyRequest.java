package com.mocean.modules.message;

import com.mocean.system.Client;
import com.mocean.modules.Transmitter;

import java.util.HashMap;

public class VerifyRequest extends com.mocean.modules.MoceanFactory {

    public VerifyRequest(Client client) {
        super(client);
        this.requiredFields = new String[]{"mocean-api-key", "mocean-api-secret", "mocean-to", "mocean-brand"};
    }

    public VerifyRequest setTo(String param) {
        this.params.put("mocean-to", param);
        return this;
    }

    public VerifyRequest setBrand(String param) {
        this.params.put("mocean-brand", param);
        return this;
    }

    public VerifyRequest setFrom(String param) {
        this.params.put("mocean-from", param);
        return this;
    }

    public VerifyRequest setCodeLength(String param) {
        this.params.put("mocean-code-length", param);
        return this;
    }

    public VerifyRequest setTemplate(String param) {
        this.params.put("mocean-template", param);
        return this;
    }

    public VerifyRequest setPinValidity(String param) {
        this.params.put("mocean-pin-validity", param);
        return this;
    }

    public VerifyRequest setNextEventWait(String param) {
        this.params.put("mocean-next-event-wait", param);
        return this;
    }

    public VerifyRequest setRespFormat(String param) {
        this.params.put("mocean-resp-format", param);
        return this;
    }

    public VerifyRequest create(HashMap<String, String> params) {
        super.create(params);
        return this;
    }

    public String send() throws Exception {
        this.createFinalParams();
        this.isRequiredFieldsSet();
        Transmitter httpRequest = new Transmitter("/rest/1/verify/req", "post", this.params);
        return httpRequest.getResponse();
    }

}
