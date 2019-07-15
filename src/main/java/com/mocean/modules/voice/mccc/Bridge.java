package com.mocean.modules.voice.mccc;

import java.util.ArrayList;
import java.util.HashMap;

public class Bridge extends AbstractMccc {
    public Bridge() {
        this(new HashMap<>());
    }

    public Bridge(HashMap<String, Object> params) {
        super(params);
    }

    public Bridge setTo(String to) {
        super.requestData.put("to", to);
        return this;
    }

    @Override
    protected ArrayList<String> requiredKey() {
        return new ArrayList<String>() {{
            add("to");
        }};
    }

    @Override
    protected String action() {
        return "dial";
    }
}
