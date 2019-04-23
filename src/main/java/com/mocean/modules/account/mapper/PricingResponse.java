package com.mocean.modules.account.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mocean.modules.AbstractResponse;
import com.mocean.modules.account.mapper.model.Destination;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class PricingResponse extends AbstractResponse {
    @XmlElementWrapper(name = "data")
    @XmlElement(name = "destination")
    @JsonProperty("destinations")
    private Destination[] destinations;

    public Destination[] getDestinations() {
        return destinations;
    }

    @Override
    public PricingResponse setRawResponse(String rawResponse) {
        super.rawResponse = rawResponse;
        return this;
    }
}