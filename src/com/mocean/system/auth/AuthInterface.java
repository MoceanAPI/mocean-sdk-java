package com.mocean.system.auth;

import java.util.HashMap;

public interface AuthInterface {
    public String getAuthMethod();

    public HashMap<String, String> getParams();
}
