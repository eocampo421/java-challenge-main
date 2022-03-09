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
public class BorrowerDTO implements Serializable {

    @Schema(description = "The borrower's name")
    @JsonProperty(value = "name")
    String name;

    @Schema(description = "The borrower's age")
    @JsonProperty(value = "age")
    Integer age;

    @Schema(description = "The borrower's annual income")
    @JsonProperty(value = "annualIncome")
    Double annualIncome;

    @Schema(description = "If the borrower has a delinquent debt")
    @JsonProperty(value = "delinquentDebt")
    Boolean delinquentDebt;

    @Schema(description = "The annual debt")
    @JsonProperty(value = "annualDebt")
    Double annualDebt;

    @Schema(description = "The borrower's credit history")
    @JsonProperty(value = "creditHistory")
    Integer creditHistory;
}