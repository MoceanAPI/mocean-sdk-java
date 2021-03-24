package com.mocean.modules.command.mc;

import java.util.ArrayList;
import java.util.HashMap;

public class SendSMS extends AbstractMc{

    public SendSMS() {
        this(new HashMap<>());
    }

    public SendSMS(HashMap<String, Object> params) {
        super(params);
    }

    @Override
    protected ArrayList<String> requiredKey() {
        return new ArrayList<String>(){{
            add("from");
            add("to");
            add("content");
        }};
    }

    @Override
    protected String action() {
        return "send-sms";
    }

    public SendSMS from (String id) {
        return this.from(id,"phone_num");
    }

    public SendSMS from (String id, String type) {
        this.removeParams("from");
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("id",id);
        params.put("type",type);
        this.requestData.put("from",params);
        return this;
    }

    public SendSMS to (String id) {
        return this.to(id,"phone_num");
    }

    public SendSMS to (String id, String type) {
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("id",id);
        params.put("type",type);
        this.requestData.put("to",params);
        return this;
    }

    public SendSMS content (String text) {
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("text",text);
        params.put("type","text");
        this.requestData.put("content",params);
        return this;
    }
}
