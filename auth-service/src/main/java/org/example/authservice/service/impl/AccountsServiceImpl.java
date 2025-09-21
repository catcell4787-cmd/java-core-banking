package org.example.authservice.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authservice.enums.AccountStatus;
import org.example.authservice.exception.GlobalExceptionHandler;
import org.example.authservice.model.dto.AccountCredentialsDto;
import org.example.authservice.model.dto.AccountDto;
import org.example.authservice.model.dto.AuthTokenDto;
import org.example.authservice.model.entity.Account;
import org.example.authservice.model.entity.Role;
import org.example.authservice.repository.AccountRepository;
import org.example.authservice.security.jwt.JwtService;
import org.example.authservice.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> register(AccountDto accountDto) {
        if (accountRepository.existsByEmail(accountDto.getEmail())) {
            throw new GlobalExceptionHandler.ConflictException("Email already exists");
        }
        Account account = modelMapper.map(accountDto, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setStatus(AccountStatus.PENDING);
        Role role = new Role();
        role.setRole("CLIENT");
        account.setRole(List.of(role));
        accountRepository.save(account);
        log.warn("account registered successfully: {}", account);
        return ResponseEntity.ok(account);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public AuthTokenDto login(AccountCredentialsDto accountCredentialsDto) {
        Account account = findByCredentials(accountCredentialsDto);
        return jwtService.generateAuthToken(account.getEmail());
    }

    @Override
    public AccountDto findByEmail(String email) {
        AccountDto accountDto = modelMapper.map(accountRepository.findByEmail(email), AccountDto.class);
        if (accountDto == null) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("account with email " + email + " not found");
        }
        return accountDto;
    }

    private Account findByCredentials(AccountCredentialsDto accountCredentialsDto) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(accountCredentialsDto.getEmail());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (passwordEncoder.matches(accountCredentialsDto.getPassword(), account.getPassword())) {
                return account;
            } else {
                throw new GlobalExceptionHandler.AuthenticationException("Invalid password");
            }
        }
        throw new GlobalExceptionHandler.AuthenticationException("Invalid email");
    }

    @PostConstruct
    public void createAdmin() {
        String admin = "admin";
        if (!accountRepository.existsByEmail("admin@admin.com")) {
            Account account = new Account();
            account.setEmail("admin@admin.com");
            account.setPassword(passwordEncoder.encode(admin));
            account.setStatus(AccountStatus.ACTIVE);
            Role role = new Role();
            role.setRole("ADMIN");
            account.setRole(List.of(role));
            accountRepository.save(account);
        }
    }
}
