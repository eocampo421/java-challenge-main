package com.example.restservice.service.impl;

import static java.lang.Double.MIN_VALUE;

import com.example.restservice.dto.BorrowerDTO;
import com.example.restservice.dto.LoanDTO;
import com.example.restservice.dto.LoanMetricDTO;
import com.example.restservice.exception.InvalidLoanTypeMetricException;
import com.example.restservice.exception.LoanNotFoundException;
import com.example.restservice.metrics.ILoanMetricCalculator;
import com.example.restservice.metrics.ILoanMetricFactory;
import com.example.restservice.model.Borrower;
import com.example.restservice.model.Loan;
import com.example.restservice.model.enums.LoanType;
import com.example.restservice.model.vo.LoanVO;
import com.example.restservice.repository.ILoanRepository;
import com.example.restservice.service.LoanService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class LoanServiceImpl implements LoanService {

    private static final String NO_LOAN_FOUND_MSG = "No loan found with [loanId={%s}]";
    private static final String LOAN_TYPE_NOT_ALLOW = "The loan type [loanType = {%s}] is not allows.";

    private final ILoanRepository loanRepository;
    private final ILoanMetricFactory loanMetricFactory;

    public LoanServiceImpl(final ILoanRepository loanRepository, ILoanMetricFactory loanMetricFactory) {
        this.loanRepository = loanRepository;
        this.loanMetricFactory = loanMetricFactory;
    }

    @Override
    public LoanMetricDTO calculateLoanMetric(final LoanVO loanVO) {

        val loanMetricFactoryInstance = getLoanMetricFactoryInstance(loanVO.getType());
        isLoanMetricSupported(loanVO, loanMetricFactoryInstance);
        val loanMetricDTO = loanMetricFactoryInstance.getLoanMetric(loanVO);

        return getLoanMetricDTO(loanMetricDTO);
    }

    private void isLoanMetricSupported(final LoanVO loanVO, final ILoanMetricCalculator loanMetricFactoryInstance) {
        if (!loanMetricFactoryInstance.isSupported(loanVO)) {
            throw new InvalidLoanTypeMetricException(
                String.format(LOAN_TYPE_NOT_ALLOW, loanVO.getType()));
        }
    }

    @Override
    public LoanMetricDTO calculateLoanMetric(final Long loanId) {
        val loanVO = getLoan(loanId);

        return calculateLoanMetric(loanVO);
    }

    @Override
    public LoanVO getLoan(final Long id) {
        val loanOpt = loanRepository.findById(id);
        if (!loanOpt.isPresent()) {
            throw new LoanNotFoundException(String.format(NO_LOAN_FOUND_MSG, id));
        }
        val loan = loanOpt.get();

        return toLoanVO(loan);
    }

    @Override
    public LoanVO getMaxMonthlyPaymentLoan() {
        val loanMetricWithLoanMap = new HashMap<LoanMetricDTO, LoanVO>();

        List<Loan> loans = loanRepository.findAll();
        loans.stream()
            .map(this::toLoanVO)
            .forEach(loanVO -> {
                val loanMetricFactoryInstance = getLoanMetricFactoryInstance(loanVO.getType());
                loanMetricWithLoanMap.put(loanMetricFactoryInstance.getLoanMetric(loanVO), loanVO);
            });

        return getMaxLoan(loanMetricWithLoanMap);
    }

    /////////////////////
    // Private Methods //
    /////////////////////

    private LoanVO getMaxLoan(final HashMap<LoanMetricDTO, LoanVO> loanMetricWithLoanMap) {
        double maxMonthlyPayment = MIN_VALUE;
        LoanVO loanVO = null;

        for (val entry : loanMetricWithLoanMap.entrySet()) {
            val loanMetricDTO = entry.getKey();
            if (loanMetricDTO.getMonthlyPayment() > maxMonthlyPayment) {
                maxMonthlyPayment = loanMetricDTO.getMonthlyPayment();

                loanVO = loanMetricWithLoanMap.get(loanMetricDTO);
            }
        }
        return loanVO;
    }

    private ILoanMetricCalculator getLoanMetricFactoryInstance(final String type) {
        return loanMetricFactory.getInstance(LoanType.fromString(type));
    }

    private LoanDTO getLoadDTO(final Loan loan) {
        return LoanDTO.builder()
            .loanId(loan.getId())
            .requestedAmount(loan.getRequestedAmount())
            .termMonths(loan.getTermMonths())
            .annualInterest(loan.getAnnualInterest())
            .type(loan.getType())
            .borrower(toBorrowerDTO(loan.getBorrower()))
            .build();
    }

    private LoanVO toLoanVO(final Loan loan) {
        val loanDTO = getLoadDTO(loan);

        return new LoanVO(loanDTO);
    }

    private BorrowerDTO toBorrowerDTO(final Borrower borrower) {
        return BorrowerDTO.builder()
            .name(borrower.getName())
            .age(borrower.getAge())
            .annualIncome(borrower.getAnnualIncome())
            .delinquentDebt(borrower.getDelinquentDebt())
            .annualDebt(borrower.getAnnualDebt())
            .creditHistory(borrower.getCreditHistory())
            .build();
    }

    private LoanMetricDTO getLoanMetricDTO(final LoanMetricDTO loanMetricDTO) {
        return LoanMetricDTO.builder()
            .monthlyInterestRate(loanMetricDTO.getMonthlyInterestRate())
            .monthlyPayment(loanMetricDTO.getMonthlyPayment())
            .build();
    }
}