package com.mocean.modules.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {
    @XmlElement(name = "status")
    @JsonProperty("status")
    private String status;

    @XmlElement(name = "receiver")
    @JsonProperty("receiver")
    private String receiver;

    @XmlElement(name = "msgid")
    @JsonProperty("msgid")
    private String msgId;

    public String getStatus() {
        return status;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMsgId() {
        return msgId;
    }
}
