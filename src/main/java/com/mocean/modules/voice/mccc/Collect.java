package com.mocean.modules.voice.mccc;

import com.mocean.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Collect extends AbstractMccc {
    public Collect() {
        this(new HashMap<>());
    }

    public Collect(HashMap<String, Object> params) {
        super(params);

        //default value
        if (Utils.isNullOrEmpty(this.requestData.get("min"))) {
            this.requestData.put("min", 1);
        }

        if (Utils.isNullOrEmpty(this.requestData.get("max"))) {
            this.requestData.put("max", 10);
        }

        if (Utils.isNullOrEmpty(this.requestData.get("terminators"))) {
            this.requestData.put("terminators", "#");
        }

        if (Utils.isNullOrEmpty(this.requestData.get("timeout"))) {
            this.requestData.put("timeout", 5000);
        }
    }

    public Collect setEventUrl(String eventUrl) {
        this.requestData.put("event-url", eventUrl);
        return this;
    }

    public Collect setMin(int min) {
        this.requestData.put("min", min);
        return this;
    }

    public Collect setMax(int max) {
        this.requestData.put("max", max);
        return this;
    }

    public Collect setTerminators(String terminators) {
        this.requestData.put("terminators", terminators);
        return this;
    }

    public Collect setTimeout(int timeout) {
        this.requestData.put("timeout", timeout);
        return this;
    }

    @Override
    protected ArrayList<String> requiredKey() {
        return new ArrayList<String>() {{
            add("event-url");
            add("min");
            add("max");
            add("terminators");
            add("timeout");
        }};
    }

    @Override
    protected String action() {
        return "collect";
    }
}
