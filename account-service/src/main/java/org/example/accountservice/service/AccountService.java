package org.example.accountservice.service;

import org.example.accountservice.entity.Account;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    List<Account> findAll();

    ResponseEntity<?> findById(UUID id);

    ResponseEntity<?> registerAccount(Account account);

    ResponseEntity<?> editById(UUID id, Account account);

    ResponseEntity<?> deleteById(UUID id);

    ResponseEntity<?> deleteAllAccounts();
}
