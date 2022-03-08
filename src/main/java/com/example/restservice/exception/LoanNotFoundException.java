package com.example.restservice.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class LoanNotFoundException extends BusinessException {

    private static final String ID_WAS_NOT_FOUND_MSG = "Data not found";

    public LoanNotFoundException(final String logErrorMsg) {
        super(logErrorMsg, ID_WAS_NOT_FOUND_MSG, NOT_FOUND);
    }
}