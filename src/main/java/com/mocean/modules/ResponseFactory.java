package com.mocean.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mocean.exception.MoceanErrorException;

import javax.xml.bind.JAXB;
import java.io.IOException;
import java.io.StringReader;

public class ResponseFactory {
    public static <T> T createObjectFromRawResponse(String rawResponse, Class<T> type) throws MoceanErrorException {
        //first check whether is json
        try {
            return new ObjectMapper().readValue(rawResponse, type);
        } catch (IOException e) {
            //format is not json, try xml now
            try {
                return JAXB.unmarshal(new StringReader(rawResponse), type);
            } catch (Exception ex) {
                throw new MoceanErrorException("unable to parse response, " + rawResponse);
            }
        }
    }
}
