package com.mocean.modules.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

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
