package code.example.restservice.service.impl.utils;

import static java.util.stream.Collectors.toList;

import com.example.restservice.dto.BorrowerDTO;
import com.example.restservice.dto.LoanDTO;
import com.example.restservice.dto.LoanMetricDTO;
import com.example.restservice.model.Loan;
import com.example.restservice.model.vo.LoanVO;
import java.util.List;

public class MockFactory {

    public static LoanVO getLoanVO() {
        return new LoanVO(getLoan());
    }

    private static LoanDTO getLoan() {
        return LoanDTO.builder()
            .loanId(1L)
            .requestedAmount(1000D)
            .termMonths(0)
            .annualInterest(6D)
            .type("student")
            .borrower(getBorrower())
            .build();
    }

    private static BorrowerDTO getBorrower() {
        return BorrowerDTO.builder()
            .name("Juan")
            .age(25)
            .delinquentDebt(false)
            .annualDebt(2000D)
            .annualIncome(10000D)
            .build();
    }

    public static LoanDTO buildValidLoanRequest() {
        return getLoan();
    }

    public static LoanMetricDTO getLoanMetricDTO() {
        return LoanMetricDTO.builder()
            .monthlyInterestRate(0.005D)
            .monthlyPayment(443.20D)
            .build();
    }

    public static LoanMetricDTO getLoanMetricDTO(final Double monthlyInterestRate, final Double monthlyPayment) {
        return LoanMetricDTO.builder()
            .monthlyInterestRate(monthlyInterestRate)
            .monthlyPayment(monthlyPayment)
            .build();
    }

    public static LoanDTO getLoanDTO(final BorrowerDTO borrowerDTO, final String loanType) {
        return LoanDTO.builder()
            .loanId(4L)
            .requestedAmount(10000D)
            .termMonths(0)
            .annualInterest(6D)
            .type(loanType)
            .borrower(borrowerDTO)
            .build();
    }

    public static BorrowerDTO getBorrowerDTO(final Integer age){
        return BorrowerDTO.builder()
            .name("Juan")
            .age(age).
            build();
    }

    public static List<LoanVO> getLoanVOs(final List<Loan> loans) {
        return loans.stream()
            .map(loan -> LoanVO.builder()
                .loanId(loan.getId())
                .type(loan.getType())
                .build())
            .collect(toList());
    }
}