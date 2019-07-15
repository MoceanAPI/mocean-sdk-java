package com.mocean.modules.voice.mccc;

import java.util.ArrayList;
import java.util.HashMap;

public class Play extends AbstractMccc {
    public Play() {
        this(new HashMap<>());
    }

    public Play(HashMap<String, Object> params) {
        super(params);
    }

    public Play setFiles(String files) {
        super.requestData.put("file", files);
        return this;
    }

    public Play setBargeIn(Boolean bargeIn) {
        this.requestData.put("barge-in", bargeIn);
        return this;
    }

    @Override
    protected ArrayList<String> requiredKey() {
        return new ArrayList<String>() {{
            add("file");
        }};
    }

    @Override
    protected String action() {
        return "play";
    }
}
