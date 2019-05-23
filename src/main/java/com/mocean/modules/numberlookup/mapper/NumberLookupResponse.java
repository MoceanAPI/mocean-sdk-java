package com.mocean.modules.numberlookup.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mocean.modules.AbstractResponse;
import com.mocean.modules.numberlookup.mapper.model.Carrier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class NumberLookupResponse extends AbstractResponse {
    @XmlElement(name = "msgid")
    @JsonProperty("msgid")
    private String msgId;

    @XmlElement(name = "to")
    @JsonProperty("to")
    private String to;

    @XmlElement(name = "current_carrier")
    @JsonProperty("current_carrier")
    private Carrier currentCarrier;

    @XmlElement(name = "original_carrier")
    @JsonProperty("original_carrier")
    private Carrier originalCarrier;

    @XmlElement(name = "ported")
    @JsonProperty("ported")
    private String ported;

    @XmlElement(name = "reachable")
    @JsonProperty("reachable")
    private String reachable;

    public String getMsgId() {
        return msgId;
    }

    public String getTo() {
        return to;
    }

    public Carrier getCurrentCarrier() {
        return currentCarrier;
    }

    public Carrier getOriginalCarrier() {
        return originalCarrier;
    }

    public String getPorted() {
        return ported;
    }

    public String getReachable() {
        return reachable;
    }

    @Override
    public NumberLookupResponse setRawResponse(String rawResponse) {
        super.rawResponse = rawResponse;
        return this;
    }
}