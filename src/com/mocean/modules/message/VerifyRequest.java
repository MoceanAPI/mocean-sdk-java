package com.mocean.modules.message;

import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.MoceanFactory;
import com.mocean.modules.ResponseHelper;
import com.mocean.modules.Transmitter;
import com.mocean.modules.mapper.ErrorResponse;
import com.mocean.modules.mapper.VerifyRequestResponse;
import com.mocean.system.auth.AuthInterface;

import java.io.IOException;
import java.util.HashMap;

public class VerifyRequest extends MoceanFactory {

    public VerifyRequest(AuthInterface objAuth) {
        super(objAuth);
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

    public VerifyRequestResponse send() throws MoceanErrorException, IOException {
        this.createFinalParams();
        this.isRequiredFieldsSet();
        Transmitter httpRequest = new Transmitter("/rest/1/verify/req", "post", this.params);
        VerifyRequestResponse verifyRequestResponse = ResponseHelper.createObjectFromRawResponse(httpRequest.getResponse()
                        .replaceAll("<verify_request>", "")
                        .replaceAll("</verify_request>", ""),
                VerifyRequestResponse.class
        ).setRawResponse(httpRequest.getResponse());

        //temporary due to inconsistent error http status code
        if (!verifyRequestResponse.getStatus().equalsIgnoreCase("0")) {
            throw new MoceanErrorException(
                    ResponseHelper.createObjectFromRawResponse(httpRequest.getResponse()
                                    .replaceAll("<verify_request>", "")
                                    .replaceAll("</verify_request>", "")
                                    .replaceAll("<verify_check>", "")
                                    .replaceAll("</verify_check>", ""),
                            ErrorResponse.class
                    ).setRawResponse(httpRequest.getResponse())
            );
        }

        return verifyRequestResponse;
    }

}
