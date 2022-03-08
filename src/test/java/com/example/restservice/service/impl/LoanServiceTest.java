package com.example.restservice.service.impl;

import static code.example.restservice.service.impl.utils.MockFactory.getLoanMetricDTO;
import static code.example.restservice.service.impl.utils.MockFactory.getLoanVOs;
import static com.example.restservice.util.LoanGeneratonUtil.getRandomLoans;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.example.restservice.dto.LoanMetricDTO;
import com.example.restservice.metrics.ILoanMetricCalculator;
import com.example.restservice.metrics.ILoanMetricFactory;
import com.example.restservice.model.Loan;
import com.example.restservice.model.enums.LoanType;
import com.example.restservice.model.vo.LoanVO;
import com.example.restservice.repository.ILoanRepository;
import java.util.List;
import java.util.Optional;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LoanServiceImpl.class)
class LoanServiceTest {

    @MockBean
    private ILoanRepository loanRepository;

    @MockBean
    private ILoanMetricFactory loanMetricFactory;

    @MockBean
    private ILoanMetricCalculator loanMetricCalculator;

    @Autowired
    private LoanServiceImpl loanServiceSUT;

    private List<Loan> loans;

    @BeforeEach
    void setUp() {
        loans = getRandomLoans(3L);
    }

    @Test
    void getLoanById_success() {
        when(loanRepository.findById(anyLong())).thenReturn(Optional.of(loans.get(2)));
        final LoanVO loan = loanServiceSUT.getLoan(3L);

        assertThat(loan.getLoanId()).isEqualTo(3L);
    }

    @Test
    void calculateLoanMetric_success() {
        val loanVOs = getLoanVOs(loans);

        when(loanMetricFactory.getInstance(any(LoanType.class))).thenReturn(loanMetricCalculator);
        doReturn(getLoanMetricDTO(0.05D, 52.85D)).when(loanMetricCalculator).getLoanMetric(any(LoanVO.class));
        when(loanMetricCalculator.isSupported(any(LoanVO.class))).thenReturn(true);

        final LoanMetricDTO loanMetricDTO = loanServiceSUT.calculateLoanMetric(loanVOs.get(1));

        assertThat(loanMetricDTO.getMonthlyInterestRate()).isEqualTo(0.05D);
        assertThat(loanMetricDTO.getMonthlyPayment()).isEqualTo(52.85D);
    }
}