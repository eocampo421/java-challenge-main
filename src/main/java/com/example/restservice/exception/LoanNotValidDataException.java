package com.example.restservice.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class LoanNotValidDataException extends BusinessException {

    private static final String CLIENT_ERROR_MSG = "Error validating request data";

    public LoanNotValidDataException(final String logErrorMsg) {
        super(logErrorMsg, CLIENT_ERROR_MSG, BAD_REQUEST);
    }
}