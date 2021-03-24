package com.mocean.modules.command.mc;

import java.util.ArrayList;
import java.util.HashMap;

public class TgSendDocument extends AbstractMc{

    public TgSendDocument() {
        this(new HashMap<>());
    }

    public TgSendDocument(HashMap<String, Object> params) {
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

    public TgSendDocument from (String id) {
        return this.from(id,"bot_username");
    }

    public TgSendDocument from (String id, String type) {
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("id",id);
        params.put("type",type);
        this.requestData.put("from",params);
        return this;
    }

    public TgSendDocument to (String id) {
        return this.to(id,"chat_id");
    }

    public TgSendDocument to (String id, String type) {
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("id",id);
        params.put("type",type);
        this.requestData.put("to",params);
        return this;
    }

    public TgSendDocument content (String url) {
        return this.content(url,"");
    }

    public TgSendDocument content (String url, String text) {
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("rich_media_url",url);
        params.put("text",text);
        params.put("type","document");
        this.requestData.put("content",params);
        return this;
    }

}
