package com.mocean.modules.voice.mccc;

import java.util.ArrayList;
import java.util.HashMap;

public class Dial extends AbstractMccc {
    public Dial() {
        this(new HashMap<>());
    }

    public Dial(HashMap<String, Object> params) {
        super(params);
    }

    public Dial setTo(String to) {
        super.requestData.put("to", to);
        return this;
    }

    public Dial setFrom(String from) {
        super.requestData.put("from", from);
        return this;
    }

    public Dial setDialSequentially(Boolean dialSequentially) {
        super.requestData.put("dial-sequentially", dialSequentially);
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
