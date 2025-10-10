package org.bank.authservice.common.loans.controller;

import lombok.RequiredArgsConstructor;
import org.bank.authservice.common.loans.dto.LoanDTO;
import org.bank.authservice.common.loans.service.LoansService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class LoansController {

    private final LoansService loansService;

    @PostMapping("/{email}/loans/createLoan")
    public ResponseEntity<?> createLoan(@PathVariable String email, @RequestBody LoanDTO loanDTO) {
        return ResponseEntity.ok(loansService.createLoan(email, loanDTO));
    }

    @GetMapping("/{email}/loans/getLoans")
    public ResponseEntity<?> getLoans(@PathVariable String email) {
        return ResponseEntity.ok(loansService.getLoan(email));
    }

}
