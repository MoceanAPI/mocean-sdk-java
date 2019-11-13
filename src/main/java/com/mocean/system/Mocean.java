package com.mocean.system;

import com.mocean.exception.MoceanErrorException;
import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.Transmitter;
import com.mocean.modules.account.Balance;
import com.mocean.modules.account.Pricing;
import com.mocean.modules.message.MessageStatus;
import com.mocean.modules.message.Sms;
import com.mocean.modules.message.VerifyRequest;
import com.mocean.modules.message.VerifyValidate;
import com.mocean.modules.numberlookup.NumberLookup;
import com.mocean.modules.voice.Voice;
import com.mocean.system.auth.AuthInterface;
import com.mocean.utils.Utils;


public class Mocean {
    public static final String SDK_VERSION = "1.0.0";
    private AuthInterface objAuth;
    private Transmitter transmitter;

    public Mocean(AuthInterface objAuth) throws MoceanErrorException {
        this(objAuth, new Transmitter(TransmitterConfig.make()));
    }

    public Mocean(AuthInterface objAuth, Transmitter transmitter) throws MoceanErrorException {
        this.objAuth = objAuth;
        this.transmitter = transmitter;

        if (objAuth.getAuthMethod().equalsIgnoreCase("basic")) {
            if (Utils.isNullOrEmpty(objAuth.getParams().get("mocean-api-key")) || Utils.isNullOrEmpty(objAuth.getParams().get("mocean-api-secret"))) {
                throw new RequiredFieldException("Api key and api secret for client object can't be empty.");
            }
        } else {
            throw new MoceanErrorException("Unsupported Auth Method");
        }
    }

    public Sms sms() {
        return new Sms(this.objAuth, this.transmitter);
    }

    public Sms flashSms() {
        Sms sms = new Sms(this.objAuth, this.transmitter);
        sms.flashSms = true;
        return sms;
    }

    public MessageStatus messageStatus() {
        return new MessageStatus(this.objAuth, this.transmitter);
    }

    public Balance balance() {
        return new Balance(this.objAuth, this.transmitter);
    }

    public Pricing pricing() {
        return new Pricing(this.objAuth, this.transmitter);
    }

    public VerifyRequest verifyRequest() {
        return new VerifyRequest(this.objAuth, this.transmitter);
    }

    public VerifyValidate verifyValidate() {
        return new VerifyValidate(this.objAuth, this.transmitter);
    }

    public NumberLookup numberLookup() {
        return new NumberLookup(this.objAuth, this.transmitter);
    }

    public Voice voice() {
        return new Voice(this.objAuth, this.transmitter);
    }
}
