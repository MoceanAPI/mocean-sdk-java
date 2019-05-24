package com.mocean.modules;

import com.mocean.exception.MoceanErrorException;
import com.mocean.system.TransmitterConfig;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Transmitter {
    private TransmitterConfig transmitterConfig;

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

        return this.formatResponse(response.toString(), responseCode);
    }

    protected String formatResponse(String responseString, int responseCode) throws MoceanErrorException {
        //remove these field for v1, no effect for v2
        String rawResponse = responseString
                .replaceAll("<verify_request>", "")
                .replaceAll("</verify_request>", "")
                .replaceAll("<verify_check>", "")
                .replaceAll("</verify_check>", "");

        if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            throw new MoceanErrorException(
                    ResponseFactory.createObjectFromRawResponse(
                            rawResponse,
                            ErrorResponse.class
                    ).setRawResponse(responseString)
            );
        }

        //these check is for v1 cause v1 http response code is not > 400, no effect for v2
        Map<String, Object> tempParsedObject = ResponseFactory.createObjectFromRawResponse(responseString, Map.class);
        if (tempParsedObject.get("status") != null && !tempParsedObject.get("status").toString().equalsIgnoreCase("0")) {
            throw new MoceanErrorException(
                    ResponseFactory.createObjectFromRawResponse(
                            rawResponse,
                            ErrorResponse.class
                    ).setRawResponse(responseString)
            );
        }

        return rawResponse;
    }

    private String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private String urlEncodeUTF8(HashMap<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (HashMap.Entry<String, String> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }

            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey()),
                    urlEncodeUTF8(entry.getValue())
            ));
        }
        return sb.toString();
    }
}
