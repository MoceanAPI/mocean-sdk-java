package com.mocean.modules.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class VerifyValidateResponse extends AbstractResponse {
    @XmlElement(name = "reqid")
    @JsonProperty("reqid")
    private String reqId;

    @XmlElement(name = "price")
    @JsonProperty("price")
    private String price;

    @XmlElement(name = "currency")
    @JsonProperty("currency")
    private String currency;

    public String getReqId() {
        return reqId;
    }

    public String getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public VerifyValidateResponse setRawResponse(String rawResponse) {
        super.rawResponse = rawResponse;
        return this;
    }
}
