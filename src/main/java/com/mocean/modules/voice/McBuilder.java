package com.mocean.modules.voice;

import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.voice.mc.AbstractMc;

import java.util.ArrayList;
import java.util.HashMap;

public class McBuilder {
    protected ArrayList<AbstractMc> mc;

    public McBuilder() {
        this.mc = new ArrayList<>();
    }

    public McBuilder add(AbstractMc mc) {
        this.mc.add(mc);
        return this;
    }

    public ArrayList<HashMap<String, Object>> build() throws RequiredFieldException {
        ArrayList<HashMap<String, Object>> converted = new ArrayList<>();
        for (AbstractMc mc : this.mc) {
            converted.add(mc.getRequestData());
        }
        return converted;
    }
}
