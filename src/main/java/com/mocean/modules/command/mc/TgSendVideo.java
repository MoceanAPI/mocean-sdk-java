package com.mocean.modules.command.mc;

import java.util.ArrayList;
import java.util.HashMap;

public class TgSendVideo extends AbstractMc{

    public TgSendVideo() {
        this(new HashMap<>());
    }

    public TgSendVideo(HashMap<String, Object> params) {
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

    public TgSendVideo from (String id) {
        return this.from(id,"bot_username");
    }

    public TgSendVideo from (String id, String type) {
        this.removeParams("from");
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("id",id);
        params.put("type",type);
        this.requestData.put("from",params);
        return this;
    }

    public TgSendVideo to (String id) {
        return this.to(id,"chat_id");
    }

    public TgSendVideo to (String id, String type) {
        this.removeParams("to");
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("id",id);
        params.put("type",type);
        this.requestData.put("to",params);
        return this;
    }

    public TgSendVideo content (String url) {
        return this.content(url,"");
    }

    public TgSendVideo content (String url, String text) {
        this.removeParams("content");
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("rich_media_url",url);
        params.put("text",text);
        params.put("type","video");
        this.requestData.put("content",params);
        return this;
    }

}
