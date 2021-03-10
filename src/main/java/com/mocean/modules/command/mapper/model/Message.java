package com.mocean.modules.command.mapper.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {
    @XmlElement(name = "action")
    @JsonProperty("action")
    private String action;

    @XmlElement(name = "message_id")
    @JsonProperty("message_id")
    private String messageId;

    @XmlElement(name = "mc_position")
    @JsonProperty("mc_position")
    private String mcPosition;

    @XmlElement(name = "total_message_segments")
    @JsonProperty("total_message_segments")
    private String totalMessageSegments;

    public String getAction() {
        return action;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMcPosition() {
        return mcPosition;
    }

    public String getTotalMessageSegments() {
        return totalMessageSegments;
    }
}
