package com.mocean.modules.command.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mocean.modules.AbstractResponse;
import com.mocean.modules.command.mapper.model.MoceanCommandResp;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendMessageResponse extends AbstractResponse {

    @XmlElement(name = "session_uuid")
    @JsonProperty("session_uuid")
    public String sessionUuid;

    @XmlElement(name = "mocean_command_resp")
    @JsonProperty("mocean_command_resp")
    public MoceanCommandResp[] moceanCommandResp;

    @Override
    public SendMessageResponse setRawResponse(String rawResponse) {
        super.rawResponse = rawResponse;
        return this;
    }

    public String sessionUuid() {
        return sessionUuid;
    }

    public MoceanCommandResp[] moceanCommandResp(){
        return moceanCommandResp;
    }
}
