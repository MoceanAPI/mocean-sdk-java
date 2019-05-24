package com.mocean.modules.message.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.AbstractResponse;
import com.mocean.modules.message.VerifyRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyRequestResponse extends AbstractResponse {
    private VerifyRequest verifyRequest;

    @XmlElement(name = "reqid")
    @JsonProperty("reqid")
    private String reqId;

    @XmlElement(name = "is_number_reachable")
    @JsonProperty("is_number_reachable")
    private String isNumberReachable;

    @XmlElement(name = "resend_number")
    @JsonProperty("resend_number")
    private String resendNumber;

    @XmlElement(name = "to")
    @JsonProperty("to")
    private String to;

    public String getReqId() {
        return reqId;
    }

    public String getIsNumberReachable() {
        return isNumberReachable;
    }

    public String getResendNumber() {
        return resendNumber;
    }

    public String getTo() {
        return to;
    }

    public VerifyRequestResponse resend() throws MoceanErrorException, IOException {
        this.verifyRequest.setReqId(this.reqId);
        return this.verifyRequest.resend();
    }

    //use for resend method
    public VerifyRequestResponse setVerifyRequest(VerifyRequest verifyRequest) {
        this.verifyRequest = verifyRequest;
        return this;
    }

    public VerifyRequestResponse setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
        return this;
    }
}
