package org.example.accountservice.service;

import org.example.accountservice.dto.AccountDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    List<AccountDto> findAll();

    ResponseEntity<?> findById(UUID id);

    ResponseEntity<?> findByEmail(String email);

    ResponseEntity<?> registerAccount(AccountDto account);

    ResponseEntity<?> deleteAllAccounts();
}
