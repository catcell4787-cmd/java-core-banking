package org.bank.authservice.common.loans.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.bank.authservice.common.account.entity.Account;
import org.bank.authservice.common.account.repository.AccountRepository;
import org.bank.authservice.common.loans.dto.LoanDTO;
import org.bank.authservice.common.loans.service.LoansService;
import org.bank.authservice.exception.GlobalExceptionHandler;
import org.bank.authservice.feign.LoansFeignClient;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoansServiceImpl implements LoansService {

    private final LoansFeignClient loansFeignClient;
    private final AccountRepository accountRepository;

    @Override
    public ResponseEntity<?> createLoan(String email, LoanDTO loanDTO) {
        if (!accountRepository.existsByEmail(email)) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Account with email " + email + " not found");
        }
        try {
            loansFeignClient.createLoan(email, loanDTO);
        } catch (FeignException e) {
            throw new GlobalExceptionHandler.ConflictException(e.getMessage());
        }
        return ResponseEntity.ok().body("Loan has been successfully requested");
    }

    @Override
    public ResponseEntity<?> getLoan(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isPresent()) {
            try {
                ResponseEntity<LoanDTO> loansResponse = loansFeignClient.getLoanByCardHolder(email);
                return ResponseEntity.ok(loansResponse.getBody());
            } catch (FeignException e) {
                throw new GlobalExceptionHandler.ConflictException(e.getMessage());
            }
        }
        throw new GlobalExceptionHandler.ResourceNotFoundException("Account with email " + email + " not found");
    }
}
