package com.mocean.modules.command.mc;

import java.util.ArrayList;
import java.util.HashMap;

public class TgSendPhoto extends AbstractMc{

    public TgSendPhoto() {
        this(new HashMap<>());
    }

    public TgSendPhoto(HashMap<String, Object> params) {
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

    public TgSendPhoto from (String id) {
        return this.from(id,"bot_username");
    }

    public TgSendPhoto from (String id, String type) {
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("id",id);
        params.put("type",type);
        this.requestData.put("from",params);
        return this;
    }

    public TgSendPhoto to (String id) {
        return this.to(id,"chat_id");
    }

    public TgSendPhoto to (String id, String type) {
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("id",id);
        params.put("type",type);
        this.requestData.put("to",params);
        return this;
    }

    public TgSendPhoto content (String url) {
        return this.content(url,"");
    }

    public TgSendPhoto content (String url, String text) {
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("rich_media_url",url);
        params.put("text",text);
        params.put("type","photo");
        this.requestData.put("content",params);
        return this;
    }

}
