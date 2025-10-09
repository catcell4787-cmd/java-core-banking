package org.bank.loansservice.controller;

import lombok.RequiredArgsConstructor;
import org.bank.loansservice.model.dto.LoanDTO;
import org.bank.loansservice.service.LoansService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/loans")
public class LoansController {

    private final LoansService loansService;

    @PostMapping("/{email}/createLoan")
    public ResponseEntity<?> createLoan(@PathVariable String email, @RequestBody LoanDTO loanDTO) {
        return ResponseEntity.ok(loansService.createLoan(email, loanDTO));
    }

    @GetMapping("/{email}/getLoans")
    public ResponseEntity<?> getLoans(@PathVariable String email) {
        return ResponseEntity.ok(loansService.getLoan(email));
    }
}
