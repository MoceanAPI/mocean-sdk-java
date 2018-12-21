package com.mocean.modules.account;

import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.MoceanFactory;
import com.mocean.modules.ResponseHelper;
import com.mocean.modules.Transmitter;
import com.mocean.modules.mapper.BalanceResponse;
import com.mocean.system.auth.AuthInterface;

import java.io.IOException;
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

        Transmitter httpRequest = new Transmitter("/rest/1/account/balance", "get", this.params);
        return ResponseHelper.createObjectFromRawResponse(httpRequest.getResponse(), BalanceResponse.class)
                .setRawResponse(httpRequest.getResponse());
    }

}
