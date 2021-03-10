package com.mocean.modules.command.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mocean.modules.AbstractResponse;
import com.mocean.modules.command.mapper.model.Message;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendMessageResponse extends AbstractResponse {

    @XmlElement(name = "session_uuid")
    @JsonProperty("session_uuid")
    public String sessionUuid;

    @XmlElementWrapper(name = "messages")
    @XmlElement(name = "message")
    @JsonProperty("messages")
    private Message[] messages;

    public String getSessionUuid() {
        return sessionUuid;
    }

    public Message[] getMessages(){
        return messages;
    }

    @Override
    public SendMessageResponse setRawResponse(String rawResponse) {
        super.rawResponse = rawResponse;
        return this;
    }
}
