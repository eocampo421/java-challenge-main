package com.example.restservice.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ErrorPayload implements Serializable {

    @Schema(description = "An UUID to look for the error message in the logs", required = true)
    String id;

    @Schema(description = "An error message to be shown to the client. It should not contain any technical "
        + "implementation details for security reasons", required = true)
    String detail;
}