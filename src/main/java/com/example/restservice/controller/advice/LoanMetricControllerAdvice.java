package com.example.restservice.controller.advice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.example.restservice.exception.BusinessException;
import com.example.restservice.exception.ErrorPayload;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class LoanMetricControllerAdvice {

    private static final String ERROR_MSG = "Unexpected error occurred";

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorPayload> genericRuntimeExceptionAdvice(RuntimeException ex) {
        return buildResponseEntityAndLog(ERROR_MSG, ERROR_MSG, ex, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorPayload> businessExceptionAdvice(final BusinessException ex) {
        return buildResponseEntityAndLog(ex.getMessage(), ex.getClientErrorMsg(), ex, ex.getHttpStatus());
    }

    /////////////////////
    // Private Methods //
    /////////////////////

    private ResponseEntity<ErrorPayload> buildResponseEntityAndLog(final String logErrorMsg,
        final String clientErrorMsg, final RuntimeException ex, final HttpStatus status) {

        val uuid = UUID.randomUUID().toString();
        val errorPayload = ErrorPayload.builder()
            .id(uuid)
            .detail(clientErrorMsg)
            .build();

        log.error("{} [Error UUID={}][Http Status={}]", logErrorMsg, uuid, status, ex);

        return ResponseEntity
            .status(status)
            .body(errorPayload);
    }
}