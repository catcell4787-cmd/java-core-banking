package org.example.accountservice.service;

import lombok.RequiredArgsConstructor;
import org.example.accountservice.entity.Account;
import org.example.accountservice.handler.GlobalExceptionHandler;
import org.example.accountservice.repository.AccountRepository;
import org.example.accountservice.role.AccountRole;
import org.example.accountservice.role.AccountStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public ResponseEntity<?> findById(UUID id) {
        Account account = accountRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> registerAccount(Account account) {
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new GlobalExceptionHandler.ConflictException("Email already exists");
        }
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new GlobalExceptionHandler.ConflictException("Username already exists");
        }
        account.setAccountStatus(AccountStatus.PENDING);
        account.setAccountRole(AccountRole.CLIENT);
        accountRepository.save(account);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> editById(UUID id, Account newData) {
        Account account = accountRepository.findById(id).orElseThrow();
        if (accountRepository.existsByEmail(newData.getEmail()) && !account.getEmail().equals(newData.getEmail())) {
            throw new GlobalExceptionHandler.ConflictException("Email already exists");
        }
        if (accountRepository.existsByUsername(newData.getUsername()) && !account.getUsername().equals(newData.getUsername())) {
            throw new GlobalExceptionHandler.ConflictException("Username already exists");
        }
        account.setUsername(newData.getUsername());
        account.setEmail(newData.getEmail());
        account.setPassword(newData.getPassword());
        accountRepository.save(account);
        return new ResponseEntity<>(account, HttpStatus.OK);
        }

    @Override
    public ResponseEntity<?> deleteById(UUID id) {
        accountRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<?> deleteAllAccounts() {
        accountRepository.deleteAll();
        return new ResponseEntity<>("Accounts dropped", HttpStatus.OK);
    }
}
