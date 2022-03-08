package com.example.restservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final String clientErrorMsg;
    private final HttpStatus httpStatus;

    public BusinessException(final String logErrorMsg, final String clientErrorMsg, final HttpStatus httpStatus) {
        super(logErrorMsg);
        this.clientErrorMsg = clientErrorMsg;
        this.httpStatus = httpStatus;
    }
}