package com.example.restservice.metrics.impl;

import static com.example.restservice.model.enums.LoanType.STUDENT;
import static java.lang.Math.pow;
import static org.apache.commons.lang3.Range.between;

import com.example.restservice.dto.LoanMetricDTO;
import com.example.restservice.exception.LoanNotValidDataException;
import com.example.restservice.metrics.ILoanMetricCalculator;
import com.example.restservice.model.vo.LoanVO;
import java.util.Optional;
import lombok.val;
import org.springframework.stereotype.Component;

@Component("STUDENT")
public class StudentLoanMetricCalculator implements ILoanMetricCalculator {

    private static final int MINIMUM_AGE = 18;
    private static final int MAXIMUM_AGE = 29;
    private static final double CHARGE = 0.8;

    @Override
    public boolean isSupported(final LoanVO loanVO) {
        return isStudentType(loanVO.getType()) && isAgeAllowed(loanVO.getBorrower().getAge());
    }

    @Override
    public LoanMetricDTO getLoanMetric(final LoanVO loanVO) {
        val monthlyInterestRate = (loanVO.getAnnualInterest() / ONE_YEAR) / PERCENTAGE;
        val base = 1 + monthlyInterestRate;
        val exponent = (-1) * (loanVO.getTermMonths() + (loanVO.getTermYears() * ONE_YEAR));

        val monthlyPayment = CHARGE * (loanVO.getRequestedAmount() * monthlyInterestRate) /
            (1 - pow(base, exponent));

        val monthlyPaymentFormatted = Double.parseDouble(getDecimalFormat().format(monthlyPayment));
        return getLoanMetricDTO(monthlyInterestRate, monthlyPaymentFormatted);
    }

    /////////////////////
    // Private Methods //
    /////////////////////

    private LoanMetricDTO getLoanMetricDTO(final Double monthlyInterestRate, final Double monthlyPayment) {
        return LoanMetricDTO.builder()
            .monthlyInterestRate(monthlyInterestRate)
            .monthlyPayment(monthlyPayment)
            .build();
    }

    private boolean isStudentType(final String type) {
        return STUDENT.value().equals(type);
    }

    private boolean isAgeAllowed(final Integer age) {
        return Optional.ofNullable(age)
            .map(isInRange -> between(MINIMUM_AGE, MAXIMUM_AGE).contains(age))
            .filter(isAgeAllowed -> isAgeAllowed)
            .orElseThrow(() -> new LoanNotValidDataException(""));
    }
}