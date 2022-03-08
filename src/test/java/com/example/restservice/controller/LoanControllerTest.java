package com.example.restservice.controller;

import static code.example.restservice.service.impl.utils.MockFactory.buildValidLoanRequest;
import static code.example.restservice.service.impl.utils.MockFactory.getLoanMetricDTO;
import static code.example.restservice.service.impl.utils.MockFactory.getLoanVO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.restservice.exception.LoanNotFoundException;
import com.example.restservice.model.vo.LoanVO;
import com.example.restservice.service.LoanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoanController.class)
class LoanControllerTest {

    private static final String URL_BASE = "/api/v1/loans/";
    private static final Long LOAN_ID = 1L;

    @MockBean
    private LoanService loanService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void calculateLoanMetric_successful() throws Exception {
        final String jsonRequest = objectMapper.writeValueAsString(buildValidLoanRequest());

        when(loanService.calculateLoanMetric(any(LoanVO.class))).thenReturn(getLoanMetricDTO());

        final ResultActions result = performPostRequest(jsonRequest, "/calculate-metric");
        final MvcResult mvcResult = result.andReturn();

        // Then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(OK.value());
    }

    @Test
    void calculateLoanMetric_RunTimeException() throws Exception {
        final String jsonRequest = objectMapper.writeValueAsString(buildValidLoanRequest());

        when(loanService.calculateLoanMetric(any(LoanVO.class))).thenThrow(
            new RuntimeException("Unexpected error occurred"));

        final ResultActions result = performPostRequest(jsonRequest, "/calculate-metric");

        result
            .andExpect(status().is5xxServerError())
            .andExpect(jsonPath("$.detail").value("Unexpected error occurred"));
    }

    @Test
    void calculateLoanMetricById_successful() throws Exception {

        when(loanService.calculateLoanMetric(any(LoanVO.class))).thenReturn(getLoanMetricDTO());

        final ResultActions result = performGetRequest("/calculate-metric/{loanId}");
        final MvcResult mvcResult = result.andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(OK.value());
    }

    @Test
    void calculateLoanMetricById_RunTimeException() throws Exception {

        when(loanService.calculateLoanMetric(anyLong())).thenThrow(new RuntimeException("Unexpected error occurred"));

        val result = performGetRequest("/calculate-metric/{loanId}");

        result
            .andExpect(status().is5xxServerError())
            .andExpect(jsonPath("$.detail").value("Unexpected error occurred"));
    }

    @Test
    void getLoan_success() throws Exception {

        when(loanService.getLoan(anyLong())).thenReturn(getLoanVO());

        val resultActions = performGetRequest("{loanId}");
        val result = resultActions.andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(OK.value());
    }


    @Test
    void getLoan_loanNotFoundException() throws Exception {

        when(loanService.getLoan(anyLong())).thenThrow(new LoanNotFoundException("The loan was not found"));

        val resultActions = performGetRequest("{loanId}");

        // Then
        resultActions
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.detail").value("Data not found"));
    }

    @Test
    void getLoan_unexpectedError_RuntimeException() throws Exception {

        when(loanService.getLoan(anyLong())).thenThrow(new RuntimeException("Unexpected error occurred"));

        val resultActions = performGetRequest("{loanId}");

        resultActions
            .andExpect(status().is5xxServerError())
            .andExpect(jsonPath("$.detail").value("Unexpected error occurred"));
    }

    private ResultActions performGetRequest(final String urlTemplate) throws Exception {
        return mockMvc.perform(
            get(URL_BASE + urlTemplate, LOAN_ID)
                .accept(MediaType.APPLICATION_JSON)
        );
    }


    private ResultActions performPostRequest(final String jsonRequest, final String path) throws Exception {
        return mockMvc.perform(
            post(URL_BASE + path)
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
    }
}