package com.mocean.modules.message.mapper;

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
public class VerifyValidateResponse extends AbstractResponse {
    @XmlElement(name = "reqid")
    @JsonProperty("reqid")
    private String reqId;

    @XmlElement(name = "msgid")
    @JsonProperty("msgid")
    private String msgId;

    @XmlElement(name = "price")
    @JsonProperty("price")
    private String price;

    @XmlElement(name = "currency")
    @JsonProperty("currency")
    private String currency;

    public String getReqId() {
        return reqId;
    }

    public String getMsgId() {
        return msgId;
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
