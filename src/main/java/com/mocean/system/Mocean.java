package com.mocean.system;

import com.mocean.exception.MoceanErrorException;
import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.account.Balance;
import com.mocean.modules.account.Pricing;
import com.mocean.modules.message.MessageStatus;
import com.mocean.modules.message.Sms;
import com.mocean.modules.message.VerifyRequest;
import com.mocean.modules.message.VerifyValidate;
import com.mocean.modules.numberlookup.NumberLookup;
import com.mocean.system.auth.AuthInterface;
import com.mocean.utils.Utils;


public class Mocean {

    private AuthInterface objAuth;

    public Mocean(AuthInterface objAuth) throws MoceanErrorException, RequiredFieldException {
        this.objAuth = objAuth;

        if (objAuth.getAuthMethod().equalsIgnoreCase("basic")) {
            if (Utils.isNullOrEmpty(objAuth.getParams().get("mocean-api-key")) || Utils.isNullOrEmpty(objAuth.getParams().get("mocean-api-secret"))) {
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

    public NumberLookup numberLookup() {
        return new NumberLookup(this.objAuth);
    }
}
