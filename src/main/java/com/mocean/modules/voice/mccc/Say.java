package com.mocean.modules.voice.mccc;

import com.mocean.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Say extends AbstractMccc {
    public Say() {
        this(new HashMap<>());
    }

    public Say(HashMap<String, Object> params) {
        super(params);

        //default value
        if (Utils.isNullOrEmpty(this.requestData.get("language"))) {
            this.requestData.put("language", "en-US");
        }
    }

    public Say setLanguage(String language) {
        super.requestData.put("language", language);
        return this;
    }

    public Say setText(String text) {
        this.requestData.put("text", text);
        return this;
    }

    public Say setBargeIn(Boolean bargeIn) {
        this.requestData.put("barge-in", bargeIn);
        return this;
    }

    @Override
    protected ArrayList<String> requiredKey() {
        return new ArrayList<String>() {{
            add("text");
            add("language");
        }};
    }

    @Override
    protected String action() {
        return "say";
    }
}
