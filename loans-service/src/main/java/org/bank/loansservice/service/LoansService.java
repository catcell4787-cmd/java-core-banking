package org.bank.loansservice.service;

import org.bank.loansservice.model.dto.LoanDTO;
import org.springframework.http.ResponseEntity;

public interface LoansService {
    LoanDTO createLoan(String email, LoanDTO loanDTO);
    LoanDTO getLoan(String email);
}
