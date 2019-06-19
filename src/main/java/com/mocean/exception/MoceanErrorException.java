package com.mocean.exception;

import com.mocean.modules.ErrorResponse;

public class MoceanErrorException extends Exception {
    private ErrorResponse errorResponse;

    public MoceanErrorException(String errMsg) {
        super(errMsg);
    }

    public MoceanErrorException(ErrorResponse errorResponse) {
        super(errorResponse.getRawResponse());
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
