package com.mocean.modules.account.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mocean.modules.AbstractResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class BalanceResponse extends AbstractResponse {
    @XmlElement(name = "value")
    @JsonProperty("value")
    private String value;

    public String getValue() {
        return value;
    }

    @Override
    public BalanceResponse setRawResponse(String rawResponse) {
        super.rawResponse = rawResponse;
        return this;
    }
}
