package com.mocean.system;

import com.mocean.exception.MoceanErrorException;
import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.message.*;
import com.mocean.modules.account.*;
import com.mocean.system.auth.AuthInterface;


public class Mocean {

    private AuthInterface objAuth;

    public Mocean(AuthInterface objAuth) throws MoceanErrorException, RequiredFieldException {
        this.objAuth = objAuth;

        if (objAuth.getAuthMethod().equalsIgnoreCase("basic")) {
            if (objAuth.getParams().get("mocean-api-key") == null || objAuth.getParams().get("mocean-api-secret") == null) {
                throw new RequiredFieldException("Api key and api secret for client object can't be empty.");
            }
        } else {
            throw new MoceanErrorException("Unsupported Auth Method");
        }
    }

    public Sms sms() {
        return new Sms(this.objAuth);
    }

    public Sms flashSms() {
        Sms sms = new Sms(this.objAuth);
        sms.flashSms = true;
        return sms;
    }

    public MessageStatus messageStatus() {
        return new MessageStatus(this.objAuth);
    }

    public Balance balance() {
        return new Balance(this.objAuth);
    }

    public Pricing pricing() {
        return new Pricing(this.objAuth);
    }

    public VerifyRequest verifyRequest() {
        return new VerifyRequest(this.objAuth);
    }

    public VerifyValidate verifyValidate() {
        return new VerifyValidate(this.objAuth);
    }
}
