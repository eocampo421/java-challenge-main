package com.example.restservice.service;

import com.example.restservice.dto.LoanMetricDTO;
import com.example.restservice.model.vo.LoanVO;

public interface LoanService {

    LoanMetricDTO calculateLoanMetric(final LoanVO loanVO);

    LoanMetricDTO calculateLoanMetric(final Long loanId);

    LoanVO getLoan(final Long id);

    LoanVO getMaxMonthlyPaymentLoan();
}