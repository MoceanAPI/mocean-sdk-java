package com.mocean.modules.account;

import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.AbstractClient;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.Transmitter;
import com.mocean.modules.account.mapper.PricingResponse;
import com.mocean.system.auth.AuthInterface;

import java.io.IOException;
import java.util.HashMap;

public class Pricing extends AbstractClient {

    public Pricing(AuthInterface objAuth, Transmitter transmitter) {
        super(objAuth, transmitter);
        this.requiredFields = new String[]{"mocean-api-key", "mocean-api-secret"};
    }

    public Pricing setMcc(String param) {
        this.params.put("mocean-mcc", param);
        return this;
    }

    public Pricing setMnc(String param) {
        this.params.put("mocean-mnc", param);
        return this;
    }

    public Pricing setDelimiter(String param) {
        this.params.put("mocean-delimiter", param);
        return this;
    }

    public Pricing setRespFormat(String param) {
        this.params.put("mocean-resp-format", param);
        return this;
    }

    public PricingResponse inquiry() throws MoceanErrorException, IOException {
        return this.send();
    }

    public PricingResponse inquiry(HashMap<String, String> params) throws MoceanErrorException, IOException {
        this.create(params);
        return this.send();
    }

    private PricingResponse send() throws MoceanErrorException, IOException {
        this.createFinalParams();
        this.isRequiredFieldsSet();

        String responseStr = this.transmitter.get("/account/pricing", this.params);
        return ResponseFactory.createObjectFromRawResponse(responseStr, PricingResponse.class)
                .setRawResponse(this.transmitter.getRawResponse());
    }

}
