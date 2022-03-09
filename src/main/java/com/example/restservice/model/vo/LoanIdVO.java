package com.example.restservice.model.vo;

import com.example.restservice.exception.LoanNotValidDataException;
import java.util.Optional;
import lombok.Value;

@Value
public class LoanIdVO {

    private static final String NULL_LOAN_ID = "The loanId cannot be null or empty.";

    Long id;

    public LoanIdVO(final Long loanId) {
        id = Long.valueOf(checkLoanId(loanId));
    }

    /////////////////////
    // Private Methods //
    /////////////////////

    private String checkLoanId(final Long loanId) {
        return String.valueOf(Optional.ofNullable(loanId)
            .orElseThrow(() -> new LoanNotValidDataException(NULL_LOAN_ID)));
    }
}