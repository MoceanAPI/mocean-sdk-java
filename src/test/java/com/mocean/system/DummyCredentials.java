package com.mocean.system;

import com.mocean.system.auth.AuthInterface;

import java.util.HashMap;

public class DummyCredentials implements AuthInterface {
    @Override
    public String getAuthMethod() {
        return "dummy";
    }

    @Override
    public HashMap<String, String> getParams() {
        return new HashMap<>();
    }
}
