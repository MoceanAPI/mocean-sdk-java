package com.mocean.modules.voice.mapper.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "call")
@XmlAccessorType(XmlAccessType.FIELD)
public class Call {
    @XmlElement(name = "status")
    @JsonProperty("status")
    private String status;

    @XmlElement(name = "receiver")
    @JsonProperty("receiver")
    private String receiver;

    @XmlElement(name = "session-uuid")
    @JsonProperty("session-uuid")
    private String sessionUuid;

    @XmlElement(name = "call-uuid")
    @JsonProperty("call-uuid")
    private String callUuid;

    public String getStatus() {
        return status;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSessionUuid() {
        return sessionUuid;
    }

    public String getCallUuid() {
        return callUuid;
    }
}
