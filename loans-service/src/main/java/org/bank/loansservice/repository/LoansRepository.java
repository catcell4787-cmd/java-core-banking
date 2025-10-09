package org.bank.loansservice.repository;


import org.bank.loansservice.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoansRepository extends JpaRepository<Loan, Long> {

    boolean existsByCardHolder(String cardHolder);

    Optional<Loan> findByCardHolder(String cardHolder);
}
