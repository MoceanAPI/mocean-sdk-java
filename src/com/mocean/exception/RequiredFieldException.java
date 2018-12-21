package com.mocean.exception;

public class RequiredFieldException extends MoceanErrorException {
    public RequiredFieldException(String errMsg) {
        super(errMsg);
    }
}
