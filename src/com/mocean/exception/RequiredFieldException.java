package com.mocean.exception;

public class RequiredFieldException extends Exception {
    public RequiredFieldException(String errMsg) {
        super(errMsg);
    }
}
