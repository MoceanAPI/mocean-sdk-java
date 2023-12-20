package com.mocean.modules.message.mapper.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {
    @XmlElement(name = "status")
    @JsonProperty("status")
    private String status;

    @XmlElement(name = "receiver")
    @JsonProperty("receiver")
    private String receiver;

    @XmlElement(name = "msgid")
    @JsonProperty("msgid")
    private String msgId;

    @XmlElement(name = "total_segments")
    @JsonProperty("total_segments")
    private String totalSegments;

    @XmlElement(name = "segment_no")
    @JsonProperty("segment_no")
    private String segmentNo;

    public String getStatus() {
        return status;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMsgId() {
        return msgId;
    }

    public String getTotalSegments() {
        return totalSegments;
    }

    public String getSegmentNo() {
        return segmentNo;
    }
}
