package com.mocean.system;

public class TransmitterConfig {
    protected String baseUrl = "https://rest.moceanapi.com";
    protected String version = "1";

    public TransmitterConfig() {
    }

    public TransmitterConfig(String baseUrl, String version) {
        this.baseUrl = baseUrl;
        this.version = version;
    }

    public static TransmitterConfig make() {
        return new TransmitterConfig();
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public TransmitterConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public TransmitterConfig setVersion(String version) {
        this.version = version;
        return this;
    }
}
