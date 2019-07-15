package com.mocean.modules.voice;

import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.voice.mccc.AbstractMccc;

import java.util.ArrayList;
import java.util.HashMap;

public class McccBuilder {
    protected ArrayList<AbstractMccc> mccc;

    public McccBuilder() {
        this.mccc = new ArrayList<>();
    }

    public McccBuilder add(AbstractMccc mccc) {
        this.mccc.add(mccc);
        return this;
    }

    public ArrayList<HashMap<String, Object>> build() throws RequiredFieldException {
        ArrayList<HashMap<String, Object>> converted = new ArrayList<>();
        for (AbstractMccc mccc : this.mccc) {
            converted.add(mccc.getRequestData());
        }
        return converted;
    }
}
