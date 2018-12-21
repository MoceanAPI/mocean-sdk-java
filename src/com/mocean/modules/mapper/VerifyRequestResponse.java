package com.mocean.modules.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;

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
