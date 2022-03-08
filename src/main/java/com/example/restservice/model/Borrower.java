package com.example.restservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "borrower")
public class Borrower {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "annual_income")
    private Double annualIncome;

    @Column(name = "delinquent_debt")
    private Boolean delinquentDebt;

    @Column(name = "annual_debt")
    private Double annualDebt;

    @Column(name = "credit_history")
    private Integer creditHistory;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Loan loan;
}