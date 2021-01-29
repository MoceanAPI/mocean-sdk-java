package com.mocean.modules.command.mc;

import java.util.ArrayList;
import java.util.HashMap;

public class TgSendText extends AbstractMc{

    public TgSendText() {
        this(new HashMap<>());
    }

    public TgSendText(HashMap<String, Object> params) {
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
        return "send-telegram";
    }

    public TgSendText from (String id) {
        return this.from(id,"bot_username");
    }

    public TgSendText from (String id, String type) {
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("id",id);
        params.put("type",type);
        this.requestData.put("from",params);
        return this;
    }

    public TgSendText to (String id) {
        return this.to(id,"chat_id");
    }

    public TgSendText to (String id, String type) {
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("id",id);
        params.put("type",type);
        this.requestData.put("to",params);
        return this;
    }

    public TgSendText content (String text) {
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("text",text);
        params.put("type","text");
        this.requestData.put("content",params);
        return this;
    }
}
