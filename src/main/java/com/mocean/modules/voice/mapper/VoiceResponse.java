package com.mocean.modules.voice.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mocean.modules.AbstractResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoiceResponse extends AbstractResponse {
    @XmlElement(name = "session-uuid")
    @JsonProperty("session-uuid")
    private String sessionUuid;

    @XmlElement(name = "call-uuid")
    @JsonProperty("call-uuid")
    private String callUuid;

    public String getSessionUuid() {
        return sessionUuid;
    }

    public String getCallUuid() {
        return callUuid;
    }

    @Override
    public VoiceResponse setRawResponse(String rawResponse) {
        super.rawResponse = rawResponse;
        return this;
    }
}
