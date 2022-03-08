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
public class LoanMetricDTO implements Serializable {

    @Schema(description = "The monthly interest rate")
    @JsonProperty(value = "monthlyInterestRate")
    Double monthlyInterestRate;

    @Schema(description = "The monthly payment")
    @JsonProperty(value = "monthlyPayment")
    Double monthlyPayment;
}