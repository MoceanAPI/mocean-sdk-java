package com.mocean.modules.voice.mc;

import java.util.ArrayList;
import java.util.HashMap;

public class Sleep extends AbstractMc {
    public Sleep() {
        this(new HashMap<>());
    }

    public Sleep(HashMap<String, Object> params) {
        super(params);
    }

    public Sleep setDuration(int duration) {
        super.requestData.put("duration", duration);
        return this;
    }

    @Override
    protected ArrayList<String> requiredKey() {
        return new ArrayList<String>() {{
            add("duration");
        }};
    }

    @Override
    protected String action() {
        return "sleep";
    }
}
