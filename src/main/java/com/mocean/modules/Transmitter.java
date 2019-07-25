package com.mocean.modules;

import com.mocean.exception.MoceanErrorException;
import com.mocean.system.TransmitterConfig;
import com.mocean.utils.Utils;
import okhttp3.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Transmitter {
    private TransmitterConfig transmitterConfig;
    private OkHttpClient okHttpClient;
    private String rawResponse;

    public Transmitter() {
        this(TransmitterConfig.make());
    }

    public Transmitter(OkHttpClient okHttpClient) {
        this(TransmitterConfig.make(), okHttpClient);
    }

    public Transmitter(TransmitterConfig transmitterConfig) {
        this(transmitterConfig, new OkHttpClient());
    }

    public Transmitter(TransmitterConfig transmitterConfig, OkHttpClient okHttpClient) {
        this.transmitterConfig = transmitterConfig;
        this.okHttpClient = okHttpClient;
    }

    public String get(String uri, HashMap<String, String> params) throws IOException, MoceanErrorException {
        return this.send("get", uri, params);
    }

    public String post(String uri, HashMap<String, String> params) throws IOException, MoceanErrorException {
        return this.send("post", uri, params);
    }

    public String send(String method, String uri, HashMap<String, String> params) throws IOException, MoceanErrorException {
        params.put("mocean-medium", "JAVA-SDK");

        //use json if default not set
        if (!params.containsKey("mocean-resp-format")) {
            params.put("mocean-resp-format", "json");
        }

        Request.Builder requestBuilder = new Request.Builder();

        if (method.equalsIgnoreCase("get")) {
            requestBuilder.url(this.transmitterConfig.getBaseUrl() + "/rest/" + this.transmitterConfig.getVersion() + uri + "?" + this.urlEncodeUTF8(params));
        } else {
            requestBuilder.url(this.transmitterConfig.getBaseUrl() + "/rest/" + this.transmitterConfig.getVersion() + uri);

            FormBody.Builder formBuilder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formBuilder.add(entry.getKey(), entry.getValue());
            }

            requestBuilder.post(formBuilder.build());
        }

        Request request = requestBuilder.build();
        Response response = this.okHttpClient.newCall(request).execute();
        int responseCode = response.code();
        String responseString = response.body().string();
        response.close();

        return this.formatResponse(responseString, responseCode, params.get("mocean-resp-format").equalsIgnoreCase("xml"), uri);
    }

    public String formatResponse(String responseString, int responseCode, Boolean isXml, String uri) throws MoceanErrorException {
        this.rawResponse = responseString;

        //remove these field for v1, no effect for v2
        String clonedResponseString = responseString
                .replaceAll("<verify_request>", "")
                .replaceAll("</verify_request>", "")
                .replaceAll("<verify_check>", "")
                .replaceAll("</verify_check>", "");

        if (isXml && this.transmitterConfig.getVersion().equalsIgnoreCase("1") && !Utils.isNullOrEmpty(uri)) {
            if ("/account/pricing".equals(uri)) {
                clonedResponseString = clonedResponseString
                        .replaceAll("<data>", "<destinations>")
                        .replaceAll("</data>", "</destinations>");
            } else if ("/sms".equals(uri)) {
                clonedResponseString = clonedResponseString
                        .replaceAll("<result>", "<result><messages>")
                        .replaceAll("</result>", "</messages></result>");
            }
        }

        if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            throw new MoceanErrorException(
                    ResponseFactory.createObjectFromRawResponse(
                            clonedResponseString,
                            ErrorResponse.class
                    ).setRawResponse(this.rawResponse)
            );
        }

        //these check is for v1 cause v1 http response code is not > 400, no effect for v2
        GenericModel tempParsedObject = ResponseFactory.createObjectFromRawResponse(clonedResponseString, GenericModel.class);
        if (tempParsedObject.getStatus() != null && !tempParsedObject.getStatus().equalsIgnoreCase("0")) {
            throw new MoceanErrorException(
                    ResponseFactory.createObjectFromRawResponse(
                            clonedResponseString,
                            ErrorResponse.class
                    ).setRawResponse(this.rawResponse)
            );
        }

        return clonedResponseString;
    }

    public String urlEncodeUTF8(HashMap<String, String> map) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (HashMap.Entry<String, String> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }

            sb.append(String.format("%s=%s",
                    URLEncoder.encode(entry.getKey(), "UTF8"),
                    URLEncoder.encode(entry.getValue(), "UTF8")
            ));
        }
        return sb.toString();
    }

    public TransmitterConfig getTransmitterConfig() {
        return transmitterConfig;
    }

    public Transmitter setTransmitterConfig(TransmitterConfig transmitterConfig) {
        this.transmitterConfig = transmitterConfig;
        return this;
    }

    public String getRawResponse() {
        return rawResponse;
    }
}
