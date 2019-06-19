package com.mocean;

import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.Transmitter;
import com.mocean.system.Mocean;
import com.mocean.system.TransmitterConfig;
import com.mocean.system.auth.Basic;

public class TestingUtils {
    public static Mocean getMoceanObj() {
        return TestingUtils.getMoceanObj(new Transmitter(TransmitterConfig.make()));
    }

    public static Mocean getMoceanObj(Transmitter transmitter) {
        try {
            return new Mocean(new Basic("test api key", "test api secret"), transmitter);
        } catch (MoceanErrorException e) {
            e.printStackTrace();
            return null;
        }
    }
}
