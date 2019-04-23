package com.mocean.modules.message.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mocean.modules.AbstractResponse;
import com.mocean.modules.message.mapper.Model.Message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class SmsResponse extends AbstractResponse {
    @XmlElement(name = "message")
    @JsonProperty("messages")
    private Message[] messages;

    public Message[] getMessages() {
        return messages;
    }

    @Override
    public SmsResponse setRawResponse(String rawResponse) {
        super.rawResponse = rawResponse;
        return this;
    }
}
