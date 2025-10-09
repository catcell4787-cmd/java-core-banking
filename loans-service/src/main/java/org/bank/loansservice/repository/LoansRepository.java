package org.bank.loansservice.repository;


import org.bank.loansservice.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoansRepository extends JpaRepository<Loan, Long> {
    boolean findByCardNumber(String cardNumber);

    boolean existsByCardNumber(String cardNumber);
}
