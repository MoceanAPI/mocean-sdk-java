package com.mocean.modules;

import com.mocean.exception.MoceanErrorException;
import com.mocean.system.TransmitterConfig;
import com.mocean.utils.Utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class Transmitter {
    private TransmitterConfig transmitterConfig;
    private String rawResponse;

    public Transmitter() {
        this(TransmitterConfig.make());
    }

    public Transmitter(TransmitterConfig transmitterConfig) {
        this.transmitterConfig = transmitterConfig;
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

        URL url;
        if (method.equalsIgnoreCase("get")) {
            url = new URL(this.transmitterConfig.getBaseUrl() + "/rest/" + this.transmitterConfig.getVersion() + uri + "?" + this.urlEncodeUTF8(params));
        } else {
            url = new URL(this.transmitterConfig.getBaseUrl() + "/rest/" + this.transmitterConfig.getVersion() + uri);
        }

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method.toUpperCase());

        if (method.equalsIgnoreCase("post")) {
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(this.urlEncodeUTF8(params));
            wr.flush();
            wr.close();
        }

        int responseCode = connection.getResponseCode();

        InputStream resultStream;
        if (responseCode < HttpURLConnection.HTTP_BAD_REQUEST) {
            resultStream = connection.getInputStream();
        } else {
            resultStream = connection.getErrorStream();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(resultStream));
        StringBuilder response = new StringBuilder();

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return this.formatResponse(response.toString(), responseCode, params.get("mocean-resp-format").equalsIgnoreCase("xml"), uri);
    }

    public String formatResponse(String responseString, int responseCode, Boolean isXml, String uri) throws MoceanErrorException {
        this.rawResponse = responseString;

        //remove these field for v1, no effect for v2
        responseString = responseString
                .replaceAll("<verify_request>", "")
                .replaceAll("</verify_request>", "")
                .replaceAll("<verify_check>", "")
                .replaceAll("</verify_check>", "");

        if (isXml && this.transmitterConfig.getVersion().equalsIgnoreCase("1") && !Utils.isNullOrEmpty(uri)) {
            if (uri.equals("/account/pricing")) {
                responseString = responseString
                        .replaceAll("<data>", "<destinations>")
                        .replaceAll("</data>", "</destinations>");
            } else if (uri.equals("/sms")) {
                responseString = responseString
                        .replaceAll("<result>", "<result><messages>")
                        .replaceAll("</result>", "</messages></result>");
            }
        }

        if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            throw new MoceanErrorException(
                    ResponseFactory.createObjectFromRawResponse(
                            responseString,
                            ErrorResponse.class
                    ).setRawResponse(this.rawResponse)
            );
        }

        //these check is for v1 cause v1 http response code is not > 400, no effect for v2
        GenericModel tempParsedObject = ResponseFactory.createObjectFromRawResponse(responseString, GenericModel.class);
        if (tempParsedObject.getStatus() != null && !tempParsedObject.getStatus().equalsIgnoreCase("0")) {
            throw new MoceanErrorException(
                    ResponseFactory.createObjectFromRawResponse(
                            responseString,
                            ErrorResponse.class
                    ).setRawResponse(this.rawResponse)
            );
        }

        return responseString;
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
