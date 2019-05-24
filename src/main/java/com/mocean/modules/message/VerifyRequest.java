package com.mocean.modules.message;

import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.ErrorResponse;
import com.mocean.modules.AbstractClient;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.Transmitter;
import com.mocean.modules.message.mapper.VerifyRequestResponse;
import com.mocean.system.auth.AuthInterface;

import java.io.IOException;
import java.util.HashMap;

public class VerifyRequest extends AbstractClient {
    public ChargeType verifyChargeType = ChargeType.CHARGE_PER_CONVERSION;

    public VerifyRequest(AuthInterface objAuth, Transmitter transmitter) {
        super(objAuth, transmitter);
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

    public VerifyRequest sendAs(ChargeType chargeType) {
        this.verifyChargeType = chargeType;
        return this;
    }

    public VerifyRequestResponse send() throws MoceanErrorException, IOException {
        this.createFinalParams();
        this.isRequiredFieldsSet();

        String verifyRequestUrl = "/rest/1/verify/req";
        if (this.verifyChargeType == ChargeType.CHARGE_PER_ATTEMPT) {
            verifyRequestUrl += "/sms";
        }

        String responseStr = this.transmitter.post(verifyRequestUrl, this.params);
        VerifyRequestResponse verifyRequestResponse = ResponseFactory.createObjectFromRawResponse(responseStr
                        .replaceAll("<verify_request>", "")
                        .replaceAll("</verify_request>", ""),
                VerifyRequestResponse.class
        ).setRawResponse(responseStr);

        //temporary due to inconsistent error http status code
        if (!verifyRequestResponse.getStatus().equalsIgnoreCase("0")) {
            throw new MoceanErrorException(
                    ResponseFactory.createObjectFromRawResponse(responseStr
                                    .replaceAll("<verify_request>", "")
                                    .replaceAll("</verify_request>", "")
                                    .replaceAll("<verify_check>", "")
                                    .replaceAll("</verify_check>", ""),
                            ErrorResponse.class
                    ).setRawResponse(responseStr)
            );
        }

        return verifyRequestResponse;
    }

}
