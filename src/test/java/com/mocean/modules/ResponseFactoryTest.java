package com.mocean.modules;

import com.mocean.exception.MoceanErrorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

class ResponseFactoryTest {
    @Test
    public void testMalformedResponse() {
        try {
            ResponseFactory.createObjectFromRawResponse("malform string", GenericModel.class);
            fail();
        } catch (MoceanErrorException ignored) {
        }
    }
}