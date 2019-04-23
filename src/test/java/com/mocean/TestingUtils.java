package com.mocean;

import com.mocean.exception.MoceanErrorException;
import com.mocean.system.Mocean;
import com.mocean.system.auth.Basic;

public class TestingUtils {
    public static Mocean getMoceanObj() {
        try {
            return new Mocean(new Basic("test api key", "test api secret"));
        } catch (MoceanErrorException e) {
            e.printStackTrace();
            return null;
        }
    }
}
