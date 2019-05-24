package com.mocean.modules.message;

import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.ErrorResponse;
import com.mocean.modules.AbstractClient;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.Transmitter;
import com.mocean.modules.message.mapper.VerifyValidateResponse;
import com.mocean.system.auth.AuthInterface;

import java.io.IOException;
import java.util.HashMap;

public class VerifyValidate extends AbstractClient {

    public VerifyValidate(AuthInterface objAuth, Transmitter transmitter) {
        super(objAuth, transmitter);
        this.requiredFields = new String[]{"mocean-api-key", "mocean-api-secret", "mocean-reqid", "mocean-code"};
    }

    public VerifyValidate setReqid(String param) {
        this.params.put("mocean-reqid", param);
        return this;
    }

    public VerifyValidate setCode(String param) {
        this.params.put("mocean-code", param);
        return this;
    }

    public VerifyValidate setRespFormat(String param) {
        this.params.put("mocean-resp-format", param);
        return this;
    }

    public VerifyValidate create(HashMap<String, String> params) {
        super.create(params);
        return this;
    }

    public VerifyValidateResponse send() throws MoceanErrorException, IOException {
        this.createFinalParams();
        this.isRequiredFieldsSet();

        String responseStr = this.transmitter.post("/verify/check", this.params);

        return ResponseFactory.createObjectFromRawResponse(responseStr, VerifyValidateResponse.class)
                .setRawResponse(responseStr);
    }

}
