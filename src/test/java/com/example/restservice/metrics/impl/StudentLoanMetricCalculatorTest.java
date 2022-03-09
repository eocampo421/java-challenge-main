package com.example.restservice.metrics.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.restservice.dto.BorrowerDTO;
import com.example.restservice.model.vo.LoanVO;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StudentLoanMetricCalculator.class)
class StudentLoanMetricCalculatorTest {

    @Autowired
    private StudentLoanMetricCalculator studentLoanMetricCalculatorSUT;

    @Test
    void testLoanTypeNotSupported() {
        final LoanVO loanStudent = getLoanVO("consumer");
        final boolean isSupported = studentLoanMetricCalculatorSUT.isSupported(loanStudent);
        assertThat(isSupported).isFalse();
    }

    @Test
    void testLoanStudentMetric() {
        final LoanVO loanConsumer = getLoanVO("student");

        val isSupported = studentLoanMetricCalculatorSUT.isSupported(loanConsumer);
        val studentLoanMetric = studentLoanMetricCalculatorSUT.getLoanMetric(loanConsumer);

        assertThat(isSupported).isTrue();
        assertThat(studentLoanMetric.getMonthlyInterestRate()).isEqualTo(0.005D);
        assertThat(studentLoanMetric.getMonthlyPayment()).isEqualTo(354.56D);
    }

    /////////////////////
    // Private Methods //
    /////////////////////

    private LoanVO getLoanVO(final String loanType) {
        return LoanVO.builder()
            .loanId(4L)
            .requestedAmount(10000D)
            .termYears(2)
            .termMonths(0)
            .annualInterest(6D)
            .type(loanType)
            .borrower(BorrowerDTO.builder()
                .age(18)
                .build())
            .build();
    }
}