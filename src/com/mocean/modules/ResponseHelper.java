package com.mocean.modules;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.JAXB;
import java.io.IOException;
import java.io.StringReader;

public class ResponseHelper {
    public static <T> T createObjectFromRawResponse(String rawResponse, Class<T> type) {
        //first check whether is json
        try {
            return new ObjectMapper().readValue(rawResponse, type);
        } catch (IOException e) {
            //format is not json, try xml now
            return JAXB.unmarshal(new StringReader(rawResponse), type);
        }
    }
}
