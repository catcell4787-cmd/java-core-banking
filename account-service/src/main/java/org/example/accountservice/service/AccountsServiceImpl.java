package org.example.accountservice.service;

import lombok.RequiredArgsConstructor;
import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.entity.Account;
import org.example.accountservice.exception.GlobalExceptionHandler;
import org.example.accountservice.repository.AccountRepository;
import org.example.accountservice.role.AccountRole;
import org.example.accountservice.role.AccountStatus;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AccountDto> findAll() {
        return accountRepository.findAll().stream().map(account -> modelMapper.map(account, AccountDto.class))
                .toList();
    }

    @Override
    public ResponseEntity<?> findById(UUID id) {
        if (accountRepository.existsById(id)) {
            return ResponseEntity.ok(accountRepository.findById(id));
        } else {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Account with id " + id + " not found");
        }
    }

    @Override
    public ResponseEntity<?> findByEmail(String email) {
        AccountDto user = modelMapper.map(accountRepository.findByEmail(email), AccountDto.class);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Account with email " + email + " not found");
        }
    }

    @Override
    public ResponseEntity<?> registerAccount(AccountDto accountDto) {
        Account newAccount = modelMapper.map(accountDto, Account.class);
        if (accountRepository.existsByEmail(newAccount.getEmail())) {
            throw new GlobalExceptionHandler.ConflictException("Email already exists");
        }
        newAccount.setAccountStatus(AccountStatus.PENDING);
        newAccount.setAccountRole(AccountRole.CLIENT);
        accountRepository.save(newAccount);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<?> deleteAllAccounts() {
        accountRepository.deleteAll();
        return new ResponseEntity<>("Accounts dropped", HttpStatus.OK);
    }
}
