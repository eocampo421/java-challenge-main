package com.example.restservice.model.vo;

import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.logging.log4j.ThreadContext.isEmpty;

import com.example.restservice.dto.BorrowerDTO;
import com.example.restservice.dto.LoanDTO;
import com.example.restservice.exception.InvalidLoanTypeMetricException;
import com.example.restservice.exception.LoanNotValidDataException;
import com.example.restservice.model.enums.LoanType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value
@Builder
@AllArgsConstructor
public class LoanVO implements Serializable {

    private static final String ANNUAL_INTEREST_NULL = "The annual interest is null";
    private static final String TYPE_BLANK = "The loan type is null or empty";
    private static final String REQUEST_AMOUNT_NULL = "The request is null";
    private static final String BORROWER_NULL = "The BorrowerDTO is null";
    private static final int ONE_YEAR = 12;
    private static final String LOAN_TYPE_NOT_VALID = "The loan type [loanType={%s}] is not either 'student' or 'consumer'";

    Long loanId;
    Double requestedAmount;
    Integer termYears;
    Integer termMonths;
    Double annualInterest;
    String type;
    BorrowerDTO borrower;

    public LoanVO(final LoanDTO loanDTO) {
        this.loanId = new LoanIdVO(loanDTO.getLoanId()).getId();
        this.requestedAmount = this.checkValues(loanDTO.getRequestedAmount(), REQUEST_AMOUNT_NULL);
        this.termMonths = loanDTO.getTermMonths() % ONE_YEAR;
        this.termYears = loanDTO.getTermMonths() / ONE_YEAR;
        this.annualInterest = this.checkValues(loanDTO.getAnnualInterest(), ANNUAL_INTEREST_NULL);
        this.type = checkLoanType(loanDTO.getType());
        this.borrower = Optional.ofNullable(loanDTO.getBorrower())
            .orElseThrow(() -> new LoanNotValidDataException("The borrower cannot be null"));
    }

    /////////////////////
    // Private Methods //
    /////////////////////

    private Double checkValues(final Double value, final String logErrorMsg) {
        if (isNull((value))) {
            throw new LoanNotValidDataException(logErrorMsg);
        }
        return value;
    }

    private String checkLoanType(final String type) {
        if (StringUtils.isEmpty(type)) {
            throw new LoanNotValidDataException(TYPE_BLANK);
        }

        return validLoanType(type.toLowerCase());
    }

    private String validLoanType(final String type) {
        if (!containsValidLoanType(type)) {
            throw new InvalidLoanTypeMetricException(
                String.format(LOAN_TYPE_NOT_VALID, type));
        }
        return type;
    }

    private boolean containsValidLoanType(final String type) {
        return stream(LoanType.values())
            .anyMatch(loanType -> loanType.value().equals(type));
    }
}