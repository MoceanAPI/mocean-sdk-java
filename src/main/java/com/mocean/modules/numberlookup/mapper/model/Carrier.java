package com.mocean.modules.numberlookup.mapper.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;

public class Carrier {
    @XmlElement(name = "country")
    @JsonProperty("country")
    private String country;

    @XmlElement(name = "name")
    @JsonProperty("name")
    private String name;

    @XmlElement(name = "network_code")
    @JsonProperty("network_code")
    private String networkCode;

    @XmlElement(name = "mcc")
    @JsonProperty("mcc")
    private String mcc;

    @XmlElement(name = "mnc")
    @JsonProperty("mnc")
    private String mnc;

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public String getNetworkCode() {
        return networkCode;
    }

    public String getMcc() {
        return mcc;
    }

    public String getMnc() {
        return mnc;
    }
}
