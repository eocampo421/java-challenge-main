package com.example.restservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanDTO implements Serializable {

    @Schema(description = "The loan Id")
    @JsonProperty(value = "loanId")
    Long loanId;

    @Schema(description = "The requested amount")
    @JsonProperty(value = "requestedAmount")
    Double requestedAmount;

    @Schema(description = "The term months to ")
    @JsonProperty(value = "termMonths")
    Integer termMonths;

    @Schema(description = "The annual interest")
    @JsonProperty(value = "annualInterest")
    Double annualInterest;

    @Schema(description = "The loan type, could be either consumer or student")
    @JsonProperty(value = "type")
    String type;

    @Schema(description = "The borrower")
    @JsonProperty(value = "borrower")
    BorrowerDTO borrower;
}