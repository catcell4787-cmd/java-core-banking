package org.example.accountservice.service;

import lombok.RequiredArgsConstructor;
import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.entity.Account;
import org.example.accountservice.mapper.AccountMapper;
import org.example.accountservice.repository.AccountRepository;
import org.example.accountservice.role.AccountRole;
import org.example.accountservice.role.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    private AccountMapper mapper;

    @Override
    public List<AccountDto> findAll() {
        return accountRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public ResponseEntity<?> findById(UUID id) {
        Account account = accountRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(mapper.toDto(account), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> registerAccount(AccountDto accountDto) {
        Account account = mapper.toEntity(accountDto);
        account.setAccountStatus(AccountStatus.PENDING);
        account.setAccountRole(AccountRole.CLIENT);
        accountRepository.save(account);
        return new ResponseEntity<>(mapper.toDto(account), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> editById(UUID id, AccountDto accountDto) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.setEmail(account.getEmail());
        account.setPassword(account.getPassword());
        accountRepository.save(account);
        return new ResponseEntity<>(mapper.toDto(account), HttpStatus.OK);
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
