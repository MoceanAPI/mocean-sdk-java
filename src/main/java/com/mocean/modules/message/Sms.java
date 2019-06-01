package com.mocean.modules.message;

import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.AbstractClient;
import com.mocean.modules.ResponseFactory;
import com.mocean.modules.Transmitter;
import com.mocean.modules.message.mapper.SmsResponse;
import com.mocean.system.auth.AuthInterface;

import java.io.IOException;
import java.util.HashMap;

public class Sms extends AbstractClient {

    public Boolean flashSms = false;

    public Sms(AuthInterface objAuth, Transmitter transmitter) {
        super(objAuth, transmitter);
        this.requiredFields = new String[]{"mocean-api-key", "mocean-api-secret", "mocean-from", "mocean-to", "mocean-text"};
    }

    public Sms setFrom(String param) {
        this.params.put("mocean-from", param);
        return this;
    }

    public Sms setTo(String param) {
        this.params.put("mocean-to", param);
        return this;
    }

    public Sms setText(String param) {
        this.params.put("mocean-text", param);
        return this;
    }

    public Sms setUdh(String param) {
        this.params.put("mocean-udh", param);
        return this;
    }

    public Sms setCoding(String param) {
        this.params.put("mocean-coding", param);
        return this;
    }

    public Sms setDlrMask(String param) {
        this.params.put("mocean-dlr-mask", param);
        return this;
    }

    public Sms setDlrUrl(String param) {
        this.params.put("mocean-dlr-url", param);
        return this;
    }

    public Sms setSchedule(String param) {
        this.params.put("mocean-schedule", param);
        return this;
    }

    public Sms setMclass(String param) {
        this.params.put("mocean-mclass", param);
        return this;
    }

    public Sms setAltDcs(String param) {
        this.params.put("mocean-alt-dcs", param);
        return this;
    }

    public Sms setCharset(String param) {
        this.params.put("mocean-charset", param);
        return this;
    }

    public Sms setValidity(String param) {
        this.params.put("mocean-validity", param);
        return this;
    }

    public Sms setRespFormat(String param) {
        this.params.put("mocean-resp-format", param);
        return this;
    }

    public Sms addTo(String param) {
        if (this.params.get("mocean-to") != null) {
            this.params.put("mocean-to", this.params.get("mocean-to") + "," + param);
        } else {
            this.params.put("mocean-to", param);
        }
        return this;
    }

    public Sms create(HashMap<String, String> params) {
        super.create(params);
        return this;
    }

    public SmsResponse send() throws MoceanErrorException, IOException {
        if (this.flashSms) {
            this.params.put("mocean-mclass", "1");
            this.params.put("mocean-alt-dcs", "1");
        }

        this.createFinalParams();
        this.isRequiredFieldsSet();

        String responseStr = this.transmitter.post("/sms", this.params);
        this.reset();
        return ResponseFactory.createObjectFromRawResponse(responseStr, SmsResponse.class)
                .setRawResponse(this.transmitter.getRawResponse());
    }
}
