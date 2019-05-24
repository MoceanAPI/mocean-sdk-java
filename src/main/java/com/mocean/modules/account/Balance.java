package com.mocean.modules.account;

import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.AbstractClient;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.Transmitter;
import com.mocean.modules.account.mapper.BalanceResponse;
import com.mocean.system.auth.AuthInterface;

import java.io.IOException;
import java.util.HashMap;

public class Balance extends AbstractClient {

    public Balance(AuthInterface objAuth, Transmitter transmitter) {
        super(objAuth, transmitter);
        this.requiredFields = new String[]{"mocean-api-key", "mocean-api-secret"};
    }

    public Balance setRespFormat(String param) {
        this.params.put("mocean-resp-format", param);
        return this;
    }

    public BalanceResponse inquiry() throws MoceanErrorException, IOException {
        return this.send();
    }

    public BalanceResponse inquiry(HashMap<String, String> params) throws MoceanErrorException, IOException {
        this.create(params);
        return this.send();
    }

    private BalanceResponse send() throws MoceanErrorException, IOException {
        this.createFinalParams();
        this.isRequiredFieldsSet();

        String responseStr = this.transmitter.get("/account/balance", this.params);
        return ResponseFactory.createObjectFromRawResponse(responseStr, BalanceResponse.class)
                .setRawResponse(responseStr);
    }

}
