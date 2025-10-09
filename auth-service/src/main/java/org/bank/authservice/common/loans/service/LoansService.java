package org.bank.authservice.common.loans.service;

import org.bank.authservice.common.loans.dto.LoanDTO;
import org.springframework.http.ResponseEntity;

public interface LoansService {
    ResponseEntity<?> createLoan(String email, LoanDTO loanDTO);
}
