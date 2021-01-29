package com.mocean.modules.command.mc;

import java.util.ArrayList;
import java.util.HashMap;

public class TgRequestContact extends AbstractMc{

    public TgRequestContact() {
        this(new HashMap<>());
        this.createTgKeyboard();
    }

    public TgRequestContact(HashMap<String, Object> params) {
        super(params);
        this.createTgKeyboard();
    }

    private void createTgKeyboard () {
        HashMap<String, String> tgKeyboard = new HashMap<String, String>();
        tgKeyboard.put("button_text","Press here to share contact");
        tgKeyboard.put("button_request","contact");
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

    public TgRequestContact from (String id) {
        return this.from(id,"bot_username");
    }

    public TgRequestContact from (String id, String type) {
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("id",id);
        params.put("type",type);
        this.requestData.put("from",params);
        return this;
    }

    public TgRequestContact to (String id) {
        return this.to(id,"chat_id");
    }

    public TgRequestContact to (String id, String type) {
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("id",id);
        params.put("type",type);
        this.requestData.put("to",params);
        return this;
    }

    public TgRequestContact content (String url) {
        return this.content(url,"");
    }

    public TgRequestContact content (String url, String text) {
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("rich_media_url",url);
        params.put("text",text);
        params.put("type","animation");
        this.requestData.put("content",params);
        return this;
    }

    public TgRequestContact button (String text) {
        HashMap<String, String> tgKeyboard = new HashMap<String, String>();
        tgKeyboard.put("button_text",text);
        tgKeyboard.put("button_request","contact");
        return this;
    }

}
