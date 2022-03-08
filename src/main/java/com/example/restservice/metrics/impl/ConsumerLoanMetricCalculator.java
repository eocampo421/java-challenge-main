package com.example.restservice.metrics.impl;

import static com.example.restservice.model.enums.LoanType.CONSUMER;
import static java.lang.Math.pow;

import com.example.restservice.dto.LoanMetricDTO;
import com.example.restservice.metrics.ILoanMetricCalculator;
import com.example.restservice.model.vo.LoanVO;
import lombok.val;
import org.springframework.stereotype.Component;

@Component("CONSUMER")
public class ConsumerLoanMetricCalculator implements ILoanMetricCalculator {

    @Override
    public boolean isSupported(final LoanVO loanVO) {
        return CONSUMER.value().equals(loanVO.getType());
    }

    @Override
    public LoanMetricDTO getLoanMetric(final LoanVO loanVO) {
        val monthlyInterestRate = (loanVO.getAnnualInterest() / ONE_YEAR) / PERCENTAGE;
        val base = 1 + monthlyInterestRate;
        val exponent = (-1) * (loanVO.getTermMonths() + (loanVO.getTermYears() * ONE_YEAR));

        val monthlyPayment = (loanVO.getRequestedAmount() * monthlyInterestRate) /
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
}