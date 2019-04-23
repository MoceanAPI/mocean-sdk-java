package com.mocean.modules;

import com.mocean.exception.MoceanErrorException;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class Transmitter {

    private final String DOMAIN = "https://rest-api.moceansms.com";
    private final String USER_AGENT = "Mozilla/5.0";
    private HashMap<String, String> params;
    private String uri, response;
    private int responseCode;

    public Transmitter(String uri, String method, HashMap<String, String> params) throws IOException {
        this.uri = uri;
        this.params = params;
        this.params.put("mocean-medium", "JAVA-SDK");
        switch (method.toLowerCase()) {
            case "get":
                this.__get();
                break;
            case "post":
                this.__post();
                break;
            case "put":
                this.__put();
                break;
            case "delete":
                this.__delete();
                break;
        }
    }

    private void __post() throws IOException {
        URL obj = new URL(DOMAIN + this.uri);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(this.urlEncodeUTF8(this.params));
        wr.flush();
        wr.close();

        this.responseCode = con.getResponseCode();

        InputStream resultStream;

        if (this.responseCode < HttpURLConnection.HTTP_BAD_REQUEST) {
            resultStream = con.getInputStream();
        } else {
            resultStream = con.getErrorStream();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(resultStream));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        this.response = response.toString();
    }

    private void __get() throws IOException {
        URL obj = new URL(DOMAIN + this.uri + "?" + this.urlEncodeUTF8(this.params));
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        this.responseCode = con.getResponseCode();

        InputStream resultStream;

        if (this.responseCode < HttpURLConnection.HTTP_BAD_REQUEST) {
            resultStream = con.getInputStream();
        } else {
            resultStream = con.getErrorStream();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(resultStream));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        this.response = response.toString();
    }

    private void __put() {

    }

    private void __delete() {

    }

    public String getResponse() throws MoceanErrorException {
        if (this.responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            throw new MoceanErrorException(
                    ResponseFactory.createObjectFromRawResponse(this.response
                                    .replaceAll("<verify_request>", "")
                                    .replaceAll("</verify_request>", "")
                                    .replaceAll("<verify_check>", "")
                                    .replaceAll("</verify_check>", ""),
                            ErrorResponse.class
                    ).setRawResponse(this.response)
            );
        }
        return this.response;
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
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }
}
