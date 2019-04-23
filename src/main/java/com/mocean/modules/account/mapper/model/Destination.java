package com.mocean.modules.account.mapper.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "destination")
@XmlAccessorType(XmlAccessType.FIELD)
public class Destination {
    @XmlElement(name = "country")
    @JsonProperty("country")
    private String country;

    @XmlElement(name = "operator")
    @JsonProperty("operator")
    private String operator;

    @XmlElement(name = "mcc")
    @JsonProperty("mcc")
    private String mcc;

    @XmlElement(name = "mnc")
    @JsonProperty("mnc")
    private String mnc;

    @XmlElement(name = "price")
    @JsonProperty("price")
    private String price;

    @XmlElement(name = "currency")
    @JsonProperty("currency")
    private String currency;

    public String getCountry() {
        return country;
    }

    public String getOperator() {
        return operator;
    }

    public String getMcc() {
        return mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public String getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }
}