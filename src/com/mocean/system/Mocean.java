package com.mocean.system;

import com.mocean.modules.message.*;
import com.mocean.modules.account.*;


public class Mocean {

    private Client objAuth;

    public Mocean(Client objAuth) {
        if (objAuth.getApiKey() == null || objAuth.getApiSecret() == null) {
            throw new java.lang.Error("Api key and api secret for client object can't be empty.");
        } else {
            this.objAuth = objAuth;
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

    public MessageStatus message_status() {
        return new MessageStatus(this.objAuth);
    }

    public Balance balance() {
        return new Balance(this.objAuth);
    }

    public Pricing pricing_list() {
        return new Pricing(this.objAuth);
    }

    public VerifyRequest verify_request() {
        return new VerifyRequest(this.objAuth);
    }

    public VerifyValidate verify_validate() {
        return new VerifyValidate(this.objAuth);
    }
}
