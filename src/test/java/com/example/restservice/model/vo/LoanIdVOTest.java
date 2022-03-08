package com.example.restservice.model.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.example.restservice.exception.LoanNotValidDataException;
import org.junit.jupiter.api.Test;

class LoanIdVOTest {

    @Test
    void testWhenLoanIdIsValid(){
        LoanIdVO loanIdVO = new LoanIdVO(4L);
        Long actualLoanId = loanIdVO.getId();
        Long expectedLoanId = 4L;

        assertThat(actualLoanId).isEqualTo(expectedLoanId);
    }

    @Test
    void testWhenLoanIdNull_throwException(){
        LoanNotValidDataException exception = assertThrows(LoanNotValidDataException.class,
            () -> new LoanIdVO(null));
        String expectedMessage = "Error validating request data";
        String actualMessage = exception.getClientErrorMsg();

        assertThat(actualMessage).contains(expectedMessage);
    }
}