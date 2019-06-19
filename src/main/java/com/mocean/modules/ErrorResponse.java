package com.mocean.modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse extends AbstractResponse {
    @XmlElement(name = "err_msg")
    @JsonProperty("err_msg")
    private String errMsg;

    public String getErrMsg() {
        return errMsg;
    }

    @Override
    public ErrorResponse setRawResponse(String rawResponse) {
        super.rawResponse = rawResponse;
        return this;
    }
}
