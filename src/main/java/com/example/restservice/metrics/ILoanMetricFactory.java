package com.example.restservice.metrics;

import com.example.restservice.model.enums.LoanType;

public interface ILoanMetricFactory {

    ILoanMetricCalculator getInstance(final LoanType loanType);
}