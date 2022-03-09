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
@ContextConfiguration(classes = ConsumerLoanMetricCalculator.class)
class ConsumerLoanMetricCalculatorTest {

    @Autowired
    ConsumerLoanMetricCalculator consumerLoanMetricCalculatorSUT;

    @Test
    void testLoanTypeNotSupported() {
        final LoanVO loanConsumer = getLoanVO("student");
        final boolean isSupported = consumerLoanMetricCalculatorSUT.isSupported(loanConsumer);
        assertThat(isSupported).isFalse();
    }

    @Test
    void testLoanConsumerMetric() {
        final LoanVO loanConsumer = getLoanVO("consumer");

        val isSupported = consumerLoanMetricCalculatorSUT.isSupported(loanConsumer);
        val consumerLoanMetric = consumerLoanMetricCalculatorSUT.getLoanMetric(loanConsumer);

        assertThat(isSupported).isTrue();
        assertThat(consumerLoanMetric.getMonthlyInterestRate()).isEqualTo(0.005D);
        assertThat(consumerLoanMetric.getMonthlyPayment()).isEqualTo(443.20D);
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
                .build())
            .build();
    }
}