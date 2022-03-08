package com.example.restservice.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import com.example.restservice.dto.LoanDTO;
import com.example.restservice.dto.LoanMetricDTO;
import com.example.restservice.model.vo.LoanIdVO;
import com.example.restservice.model.vo.LoanVO;
import com.example.restservice.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/loans")
public class LoanController implements ILoanController {

    private final LoanService loanService;

    public LoanController(final LoanService loanService) {
        this.loanService = loanService;
    }

    @Override
    @PostMapping(value = "/calculate-metric", consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LoanMetricDTO> calculateLoanMetric(@RequestBody final LoanDTO loanDTO) {
        val loanVO = new LoanVO(loanDTO);
        val response = loanService.calculateLoanMetric(loanVO);

        return new ResponseEntity<>(response, OK);
    }

    @Override
    @GetMapping(value = "calculate-metric/{loanId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LoanMetricDTO> calculateLoanMetric(@PathVariable final Long loanId) {
        val loanIdVO = new LoanIdVO(loanId);
        log.info("Calculating the loan metric to the: [loanId={}]", loanIdVO.getId());
        val response = loanService.calculateLoanMetric(loanIdVO.getId());

        return new ResponseEntity<>(response, OK);
    }

    @Override
    @GetMapping(value = "/{loanId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LoanVO> getLoan(@PathVariable final Long loanId) {
        LoanIdVO loanIdVO = new LoanIdVO(loanId);
        log.info("Getting the loan");
        var response = loanService.getLoan(loanIdVO.getId());

        return new ResponseEntity<>(response, OK);
    }

    @Override
    @GetMapping(value = "max-monthly-payment", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LoanVO> getMaxMonthlyPaymentLoan() {
        val response = loanService.getMaxMonthlyPaymentLoan();

        return new ResponseEntity<>(response, OK);
    }
}