package com.mocean.modules.message;

import com.mocean.modules.MoceanFactory;
import com.mocean.modules.Transmitter;
import com.mocean.system.auth.AuthInterface;

import java.util.HashMap;

public class VerifyValidate extends MoceanFactory {

    public VerifyValidate(AuthInterface objAuth) {
        super(objAuth);
        this.requiredFields = new String[]{"mocean-api-key", "mocean-api-secret", "mocean-reqid", "mocean-otp-code"};
    }

    public VerifyValidate setReqid(String param) {
        this.params.put("mocean-reqid", param);
        return this;
    }

    public VerifyValidate setOtpCode(String param) {
        this.params.put("mocean-otp-code", param);
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

    public String send() throws Exception {
        this.createFinalParams();
        this.isRequiredFieldsSet();

        Transmitter httpRequest = new Transmitter("/rest/1/verify/check", "post", this.params);
        return httpRequest.getResponse();
    }

}
