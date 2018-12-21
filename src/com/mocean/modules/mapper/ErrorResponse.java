package com.mocean.modules.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;

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
