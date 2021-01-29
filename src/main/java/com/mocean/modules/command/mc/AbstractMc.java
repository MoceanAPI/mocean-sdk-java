package com.mocean.modules.command.mc;

import com.mocean.exception.RequiredFieldException;
import com.mocean.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

abstract public class AbstractMc {

    public AbstractMc() {

    }
    protected HashMap<String, Object> requestData;

    protected AbstractMc(HashMap<String, Object> params) {
        this.requestData = params;
    }

    public HashMap<String, Object> getRequestData() throws RequiredFieldException {
        for (String requiredKey : this.requiredKey()) {
            if (Utils.isNullOrEmpty(this.requestData.get(requiredKey))) {
                throw new RequiredFieldException(requiredKey + " is mandatory field, can't be empty. (" + this.getClass().getName() + ")");
            }
        }

        this.requestData.put("action", this.action());
        return this.requestData;
    }

    abstract protected ArrayList<String> requiredKey();

    abstract protected String action();
}
