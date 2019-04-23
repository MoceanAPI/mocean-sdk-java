package com.mocean.modules.message.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mocean.modules.AbstractResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageStatusResponse extends AbstractResponse {
    @XmlElement(name = "message_status")
    @JsonProperty("message_status")
    private String messageStatus;

    @XmlElement(name = "msgid")
    @JsonProperty("msgid")
    private String msgId;

    @XmlElement(name = "credit_deducted")
    @JsonProperty("credit_deducted")
    private String creditDeducted;

    public String getMessageStatus() {
        return messageStatus;
    }

    public String getMsgId() {
        return msgId;
    }

    public String getCreditDeducted() {
        return creditDeducted;
    }

    @Override
    public MessageStatusResponse setRawResponse(String rawResponse) {
        super.rawResponse = rawResponse;
        return this;
    }
}
