package com.example.restservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "loan")
public class Loan {

    @Id
    private Long id;

    @Column(name = "requested_amount")
    private Double requestedAmount;

    @Column(name = "term_months")
    private Integer termMonths;

    @Column(name = "annual_interest")
    private Double annualInterest;

    @Column(name = "type")
    private String type;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "loan")
    private Borrower borrower;

    public void setBorrower(final Borrower borrower) {
        borrower.setLoan(this);
        this.borrower = borrower;
    }
}