package com.mocean.modules;

import com.mocean.exception.RequiredFieldException;
import com.mocean.system.auth.AuthInterface;
import com.mocean.utils.Utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbstractClient {

    protected HashMap<String, String> params;
    protected String[] requiredFields;

    private AuthInterface objAuth;
    protected Transmitter transmitter;

    public AbstractClient(AuthInterface objAuth, Transmitter transmitter) {
        this.objAuth = objAuth;
        this.transmitter = transmitter;
        this.params = objAuth.getParams();
        this.requiredFields = new String[0];
    }

    protected void isRequiredFieldsSet() throws RequiredFieldException {
        for (String value : this.requiredFields) {
            if (Utils.isNullOrEmpty(this.params.get(value))) {
                throw new RequiredFieldException(value + " is mandatory field, can't be empty.");
            }
        }
    }

    protected void createFinalParams() {
        HashMap<String, String> newParams = new HashMap<String, String>();
        Pattern prefixRegex = Pattern.compile("mocean-");

        for (String key : this.params.keySet()) {
            if (this.params.get(key) != null) {
                Matcher prefix = prefixRegex.matcher(key);

                if (!prefix.find()) {
                    newParams.put("mocean-" + key, this.params.get(key));
                } else {
                    newParams.put(key, this.params.get(key));
                }
            }
        }

        this.params = newParams;
    }

    protected AbstractClient create(HashMap<String, String> params) {
        this.params.putAll(params);
        return this;
    }

    protected void reset() {
        this.params = this.objAuth.getParams();
    }

    public HashMap<String, String> getParams() {
        return this.params;
    }
}
