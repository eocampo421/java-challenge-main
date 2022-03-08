package com.example.restservice.repository;

import com.example.restservice.model.Loan;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoanRepository extends CrudRepository<Loan, Long> {

    List<Loan> findAll();
}