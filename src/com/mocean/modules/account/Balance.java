package com.mocean.modules.account;

import com.mocean.modules.MoceanFactory;
import com.mocean.modules.Transmitter;
import com.mocean.system.auth.AuthInterface;

import java.util.HashMap;

public class Balance extends MoceanFactory {

    public Balance(AuthInterface objAuth) {
        super(objAuth);
        this.requiredFields = new String[]{"mocean-api-key", "mocean-api-secret"};
    }

    public Balance setRespFormat(String param) {
        this.params.put("mocean-resp-format", param);
        return this;
    }

    public String inquiry() throws Exception {
        return this.send();
    }

    public String inquiry(HashMap<String, String> params) throws Exception {
        this.create(params);
        return this.send();
    }

    private String send() throws Exception {
        this.createFinalParams();
        this.isRequiredFieldsSet();

        Transmitter httpRequest = new Transmitter("/rest/1/account/balance", "get", this.params);
        return httpRequest.getResponse();
    }

}
