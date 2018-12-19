package com.mocean.modules;

import java.util.HashMap;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Transmitter {

    private final String DOMAIN = "https://rest-api.moceansms.com";
    private final String USER_AGENT = "Mozilla/5.0";
    private HashMap<String, String> params;
    private String uri, response;

    public Transmitter(String uri, String method, HashMap<String, String> params) throws Exception {
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

    private void __post() throws Exception {
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

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        this.response = response.toString();
    }

    private void __get() throws Exception {
        URL obj = new URL(DOMAIN + this.uri + "?" + this.urlEncodeUTF8(this.params));
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();


        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
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

    public String getResponse() {
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
