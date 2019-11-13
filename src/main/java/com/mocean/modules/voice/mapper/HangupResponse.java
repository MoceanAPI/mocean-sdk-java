package com.mocean.modules.voice.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mocean.modules.AbstractResponse;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HangupResponse extends AbstractResponse {
    @Override
    public HangupResponse setRawResponse(String rawResponse) {
        super.rawResponse = rawResponse;
        return this;
    }
}
