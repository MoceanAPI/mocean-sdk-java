package com.mocean.modules.message.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mocean.modules.AbstractResponse;
import com.mocean.modules.message.mapper.model.Message;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmsResponse extends AbstractResponse {
    @XmlElementWrapper(name = "messages")
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
