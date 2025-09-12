package com.mocean.system.auth;

import java.util.HashMap;

public class Basic implements AuthInterface {

    private HashMap<String, String> params;

    public Basic() {
        this.params = new HashMap<String, String>();
    }

    public Basic(String apiKey, String apiSecret) {
        this.params = new HashMap<String, String>();
        this.params.put("mocean-api-key", apiKey);
        this.params.put("mocean-api-secret", apiSecret);
    }

    public Basic(String apiToken) {
        this.params = new HashMap<String, String>();
        this.params.put("mocean-api-token", apiToken);
    }

    public void setApiKey(String apiKey) {
        this.params.put("mocean-api-key", apiKey);
    }

    public void setApiSecret(String apiSecret) {
        this.params.put("mocean-api-secret", apiSecret);
    }

    public void setApiToken(String apiToken) {
        this.params.put("mocean-api-token", apiToken);
    }

    @Override
    public String getAuthMethod() {
        return "basic";
    }

    @Override
    public HashMap<String, String> getParams() {
        return this.params;
    }
}
