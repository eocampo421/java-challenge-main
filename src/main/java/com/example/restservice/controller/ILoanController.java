package com.example.restservice.controller;

import com.example.restservice.constants.SpecsConstants;
import com.example.restservice.dto.LoanDTO;
import com.example.restservice.dto.LoanMetricDTO;
import com.example.restservice.exception.ErrorPayload;
import com.example.restservice.model.vo.LoanVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = SpecsConstants.API_INFO_TITLE, description = SpecsConstants.API_INFO_DESCRIPTION)
public interface ILoanController {

    @Operation(
        summary = "Calculates the loan metric"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Loan metric successfully calculated",
            content = @Content(schema = @Schema(implementation = LoanMetricDTO.class))),
        @ApiResponse(responseCode = "400", description = "Error validating request",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error - Server-side error.",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class)))
    })
    ResponseEntity<LoanMetricDTO> calculateLoanMetric(final LoanDTO loanDTO);

    @Operation(
        summary = "Calculates the loan metric by loanId"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Loan metric successfully calculated",
            content = @Content(schema = @Schema(implementation = LoanMetricDTO.class))),
        @ApiResponse(responseCode = "400", description = "Error validating request",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class))),
        @ApiResponse(responseCode = "404", description = "Loan not found",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error - Server-side error.",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class)))
    })
    ResponseEntity<LoanMetricDTO> calculateLoanMetric(Long loanId);

    @Operation(
        summary = "Retrieves a Loan by loanId"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Loan successfully retrieved",
            content = @Content(schema = @Schema(implementation = LoanVO.class))),
        @ApiResponse(responseCode = "400", description = "Error validating request",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class))),
        @ApiResponse(responseCode = "404", description = "Loan not found",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error - Server-side error.",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class)))
    })
    ResponseEntity<LoanVO> getLoan(Long loanId);

    @Operation(
        summary = "Retrieves the max monthly payment loan"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Loan successfully retrieved",
            content = @Content(schema = @Schema(implementation = LoanVO.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error - Server-side error.",
            content = @Content(schema = @Schema(implementation = ErrorPayload.class)))
    })
    ResponseEntity<LoanVO> getMaxMonthlyPaymentLoan();
}