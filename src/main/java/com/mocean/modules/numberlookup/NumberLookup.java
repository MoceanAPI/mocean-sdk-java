package com.mocean.modules.numberlookup;

import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.AbstractClient;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.Transmitter;
import com.mocean.modules.numberlookup.mapper.NumberLookupResponse;
import com.mocean.system.auth.AuthInterface;

import java.io.IOException;
import java.util.HashMap;

public class NumberLookup extends AbstractClient {

    public NumberLookup(AuthInterface objAuth, Transmitter transmitter) {
        super(objAuth, transmitter);
        this.requiredFields = new String[]{"mocean-api-key", "mocean-api-secret", "mocean-to"};
    }

    public NumberLookup setTo(String to) {
        this.params.put("mocean-to", to);
        return this;
    }

    public NumberLookup setRespFormat(String param) {
        this.params.put("mocean-resp-format", param);
        return this;
    }

    public NumberLookup setNlUrl(String nlUrl) {
        this.params.put("mocean-nl-url", nlUrl);
        return this;
    }

    public NumberLookupResponse inquiry() throws MoceanErrorException, IOException {
        return this.send();
    }

    public NumberLookupResponse send(HashMap<String, String> params) throws MoceanErrorException, IOException {
        this.create(params);
        return this.send();
    }

    private NumberLookupResponse send() throws MoceanErrorException, IOException {
        this.createFinalParams();
        this.isRequiredFieldsSet();

        String responseStr = this.transmitter.get("/nl", this.params);
        return ResponseFactory.createObjectFromRawResponse(responseStr, NumberLookupResponse.class)
                .setRawResponse(this.transmitter.getRawResponse());
    }
}
