package com.mocean.modules.voice.mccc;

import java.util.ArrayList;
import java.util.HashMap;

public class Collect extends AbstractMccc {
    public Collect() {
        this(new HashMap<>());
    }

    public Collect(HashMap<String, Object> params) {
        super(params);
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
            add("timeout");
        }};
    }

    @Override
    protected String action() {
        return "collect";
    }
}
