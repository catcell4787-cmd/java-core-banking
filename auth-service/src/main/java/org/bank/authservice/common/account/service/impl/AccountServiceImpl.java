package org.bank.authservice.common.account.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bank.authservice.enums.Role;
import org.bank.authservice.common.account.redis.RoleService;
import org.bank.authservice.exception.GlobalExceptionHandler;
import org.bank.authservice.common.account.dto.AccountCredentialsDTO;
import org.bank.authservice.common.account.dto.AccountDTO;
import org.bank.authservice.common.account.entity.Account;
import org.bank.authservice.common.account.repository.AccountRepository;
import org.bank.authservice.security.jwt.JwtService;
import org.bank.authservice.common.account.service.AccountService;
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
    public ResponseEntity<?> register(AccountCredentialsDTO accountCredentialsDto, Role role, boolean status) {
        if (accountRepository.existsByEmail(accountCredentialsDto.getEmail())) {
            throw new GlobalExceptionHandler.ConflictException("Email already exists");
        }
        Account account = modelMapper.map(accountCredentialsDto, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setEnabled(status);
        roleService.saveRole(account.getEmail(), role);
        accountRepository.save(account);
        return ResponseEntity.ok("Account created successfully");
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public ResponseEntity<AccountDTO> findByEmail(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isPresent()) {
            AccountDTO accountDto = modelMapper.map(optionalAccount.get(), AccountDTO.class);
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
    public ResponseEntity<?> updateStatus(String email, AccountDTO accountDto) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setEnabled(accountDto.isEnabled());
            accountRepository.save(account);
            accountDto = modelMapper.map(optionalAccount.get(), AccountDTO.class);
            return ResponseEntity.ok(accountDto);
        }
        throw new GlobalExceptionHandler.ResourceNotFoundException("account with email " + email + " not found");
    }

    @Override
    public ResponseEntity<?> login(AccountCredentialsDTO accountCredentialsDto) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(accountCredentialsDto.getEmail());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (!account.isEnabled()) {
                throw new GlobalExceptionHandler.AccountStatusException("Account is not enabled");
            }
            if (passwordEncoder.matches(accountCredentialsDto.getPassword(), account.getPassword())) {
//                log.info("tokens: {}", jwtService.generateAuthToken(account.getEmail()));
                log.info("token : {}", jwtService.getRefreshToken(account.getEmail()));
                return ResponseEntity.ok("Logged in successfully");
            } else {
                throw new GlobalExceptionHandler.AuthenticationException("Invalid password");
            }
        }
        throw new GlobalExceptionHandler.AuthenticationException("Email is not registered");
    }
}
