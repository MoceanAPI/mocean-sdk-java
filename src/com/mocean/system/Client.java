package com.mocean.system;

import com.mocean.system.auth.AuthInterface;
import java.util.HashMap;

public class Client implements AuthInterface {

    private HashMap<String, String> params;

    public Client() {
        this.params = new HashMap<String, String>();
    }

    public Client(String apiKey, String apiSecret) {
        this.params = new HashMap<String, String>();
        this.params.put("mocean-api-key", apiKey);
        this.params.put("mocean-api-secret", apiSecret);
    }

    public void setApiKey(String param) {
        this.params.put("mocean-api-key", param);
    }

    public void setApiSecret(String param) {
        this.params.put("mocean-api-secret", param);
    }

    public String getApiKey() {
        return this.params.get("mocean-api-key");
    }

    public String getApiSecret() {
        return this.params.get("mocean-api-secret");
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
