package com.mocean.modules;

import com.mocean.exception.RequiredFieldException;
import com.mocean.system.auth.AuthInterface;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoceanFactory {

    protected HashMap<String, String> params;
    protected String[] requiredFields;

    private AuthInterface objAuth;

    public MoceanFactory(AuthInterface objAuth) {
        this.objAuth = objAuth;
        this.params = objAuth.getParams();
        this.requiredFields = new String[0];
    }

    protected void isRequiredFieldsSet() throws RequiredFieldException {
        for (String value : this.requiredFields) {
            if (this.params.get(value) == null) {
                throw new RequiredFieldException(value + " is mandatory field, can't be empty.");
            }
        }
    }

    protected void createFinalParams() {
        HashMap<String, String> newParams = new HashMap<String, String>();
        Pattern prefixRegex = Pattern.compile("mocean-");

        for (String key : this.params.keySet()) {
            if (this.params.get(key) == null) {
                continue;
            }

            Matcher prefix = prefixRegex.matcher(key);

            if (!prefix.find()) {
                newParams.put("mocean-" + key, this.params.get(key));
            } else {
                newParams.put(key, this.params.get(key));
            }
        }

        this.params = newParams;
    }

    protected MoceanFactory create(HashMap<String, String> params) {
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
