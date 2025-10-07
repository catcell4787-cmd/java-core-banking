package org.bank.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bank.authservice.enums.Role;
import org.bank.authservice.enums.Status;
import org.bank.authservice.exception.GlobalExceptionHandler;
import org.bank.authservice.model.dto.AccountCredentialsDto;
import org.bank.authservice.model.dto.AccountDto;
import org.bank.authservice.model.dto.AuthTokenDto;
import org.bank.authservice.model.entity.Account;
import org.bank.authservice.repository.AccountRepository;
import org.bank.authservice.security.jwt.JwtService;
import org.bank.authservice.service.AccountService;
import org.bank.authservice.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public ResponseEntity<?> register(AccountCredentialsDto accountCredentialsDto, Role role, Status status) {
        if (accountRepository.existsByEmail(accountCredentialsDto.getEmail())) {
            throw new GlobalExceptionHandler.ConflictException("Email already exists");
        }
        Account account = modelMapper.map(accountCredentialsDto, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setStatus(status);
        roleService.saveRole(account.getEmail(), role);
        accountRepository.save(account);
        return ResponseEntity.ok("Account created successfully");
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public ResponseEntity<AccountDto> findByEmail(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isPresent()) {
            AccountDto accountDto = modelMapper.map(optionalAccount.get(), AccountDto.class);
            return ResponseEntity.ok(accountDto);
        }
        throw new GlobalExceptionHandler.ResourceNotFoundException("Account with email " + email + " not found");
    }

    @Override
    public List<Account> findByRole(String role) {
        return roleService.findByRole(role);
    }

    @Override
    public void deleteAccount(String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow();
        accountRepository.delete(account);
        roleService.deleteRole(email);
    }

    @Override
    public ResponseEntity<?> updateStatus(String email, AccountDto accountDto) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setStatus(accountDto.getStatus());
            return ResponseEntity.ok(accountRepository.save(account));
        }
        throw new GlobalExceptionHandler.ResourceNotFoundException("account with email " + email + " not found");
    }

    @Override
    public AuthTokenDto login(AccountCredentialsDto accountCredentialsDto) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(accountCredentialsDto.getEmail());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (passwordEncoder.matches(accountCredentialsDto.getPassword(), account.getPassword())) {
                return jwtService.generateAuthToken(account.getEmail());
            } else {
                throw new GlobalExceptionHandler.AuthenticationException("Invalid password");
            }
        }
        throw new GlobalExceptionHandler.AuthenticationException("Email is not registered");
    }
}
