package com.example.restservice.metrics;

import com.example.restservice.dto.LoanMetricDTO;
import com.example.restservice.model.vo.LoanVO;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import org.springframework.stereotype.Service;

@Service
public interface ILoanMetricCalculator {

    int ONE_YEAR = 12;
    int PERCENTAGE = 100;

    /**
     * Validates if a loan is supported to calculate metrics
     *
     * @param loanVO
     */
    default boolean isSupported(final LoanVO loanVO) {
        // Validate if the loan type is supported
        return true;
    }

    /**
     * To format decimal number
     *
     * @return Decimal Format
     */
    default DecimalFormat getDecimalFormat() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);

        return df;
    }

    /**
     * Calculates the Loan Metric of a Loan entity
     *
     * @param loanVO
     */
    LoanMetricDTO getLoanMetric(final LoanVO loanVO);
}