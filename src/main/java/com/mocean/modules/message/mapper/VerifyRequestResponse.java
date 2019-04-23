package com.mocean.modules.message.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mocean.modules.AbstractResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class VerifyRequestResponse extends AbstractResponse {
    @XmlElement(name = "reqid")
    @JsonProperty("reqid")
    private String reqId;

    public String getReqId() {
        return reqId;
    }

    public VerifyRequestResponse setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
        return this;
    }
}
