package com.mocean.modules;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;

public abstract class AbstractResponse {
    @XmlElement(name = "status")
    @JsonProperty("status")
    private String status;

    protected String rawResponse;

    public String getStatus() {
        return status;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public abstract AbstractResponse setRawResponse(String rawResponse);

    @Override
    public String toString() {
        return this.getRawResponse();
    }
}
