package com.mocean.modules.voice.mccc;

import java.util.ArrayList;
import java.util.HashMap;

public class Record extends AbstractMccc {
    public Record() {
        this(new HashMap<>());
    }

    public Record(HashMap<String, Object> params) {
        super(params);
    }

    @Override
    protected ArrayList<String> requiredKey() {
        return new ArrayList<>();
    }

    @Override
    protected String action() {
        return "record";
    }
}
