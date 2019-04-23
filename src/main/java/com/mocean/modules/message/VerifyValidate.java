package com.mocean.modules.message;

import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.ErrorResponse;
import com.mocean.modules.MoceanFactory;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.Transmitter;
import com.mocean.modules.message.mapper.VerifyValidateResponse;
import com.mocean.system.auth.AuthInterface;

import java.io.IOException;
import java.util.HashMap;

public class VerifyValidate extends MoceanFactory {

    public VerifyValidate(AuthInterface objAuth) {
        super(objAuth);
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

        Transmitter httpRequest = new Transmitter("/rest/1/verify/check", "post", this.params);
        VerifyValidateResponse verifyValidateResponse = ResponseFactory.createObjectFromRawResponse(httpRequest.getResponse()
                        .replaceAll("<verify_check>", "")
                        .replaceAll("</verify_check>", ""),
                VerifyValidateResponse.class
        ).setRawResponse(httpRequest.getResponse());

        //temporary due to inconsistent error http status code
        if (!verifyValidateResponse.getStatus().equalsIgnoreCase("0")) {
            throw new MoceanErrorException(
                    ResponseFactory.createObjectFromRawResponse(httpRequest.getResponse()
                                    .replaceAll("<verify_request>", "")
                                    .replaceAll("</verify_request>", "")
                                    .replaceAll("<verify_check>", "")
                                    .replaceAll("</verify_check>", ""),
                            ErrorResponse.class
                    ).setRawResponse(httpRequest.getResponse())
            );
        }

        return verifyValidateResponse;
    }

}
