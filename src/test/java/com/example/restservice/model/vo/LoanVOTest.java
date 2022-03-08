package com.example.restservice.model.vo;

import static code.example.restservice.service.impl.utils.MockFactory.getLoanDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.restservice.dto.BorrowerDTO;
import com.example.restservice.exception.InvalidLoanTypeMetricException;
import com.example.restservice.exception.LoanNotValidDataException;
import lombok.val;
import org.junit.jupiter.api.Test;

class LoanVOTest {

    @Test
    void testWhenLoanDataIsValid() {
        val borrowerDTO = BorrowerDTO.builder()
            .build();

        val loanDTO = getLoanDTO(borrowerDTO, "consumer");

        LoanVO loanVO = new LoanVO(loanDTO);
        assertThat(loanVO.getLoanId()).isEqualTo(4L);
        assertThat(loanVO.getRequestedAmount()).isEqualTo(10000D);
        assertThat(loanVO.getAnnualInterest()).isEqualTo(6D);
        assertThat(loanVO.getBorrower()).isNotNull();
    }

    @Test
    void testWhenBorrowerNull_throwException() {
        val exception = assertThrows(LoanNotValidDataException.class,
            () -> new LoanVO(getLoanDTO(null, "student")));
        val expectedMessage = "Error validating request data";
        val actualMessage = exception.getClientErrorMsg();

        assertThat(actualMessage).contains(expectedMessage);
    }

    @Test
    void testWhenLoanTypeIsNotValid_throwException() {
        val exception = assertThrows(InvalidLoanTypeMetricException.class,
            () -> new LoanVO(getLoanDTO(null, "senior")));
        val expectedMessage = "Error validating request data";
        val actualMessage = exception.getClientErrorMsg();

        assertThat(actualMessage).contains(expectedMessage);
    }
}