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

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }
}
