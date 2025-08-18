package org.example.accountservice.service;

import lombok.RequiredArgsConstructor;
import org.example.accountservice.dto.AccountCredentialsDto;
import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.dto.AuthTokenDto;
import org.example.accountservice.dto.RefreshTokenDto;
import org.example.accountservice.entity.Account;
import org.example.accountservice.exception.GlobalExceptionHandler;
import org.example.accountservice.repository.AccountRepository;
import org.example.accountservice.role.AccountRole;
import org.example.accountservice.role.AccountStatus;
import org.example.accountservice.security.jwt.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthTokenDto signIn(AccountCredentialsDto accountCredentialsDto)  {
        Account account = findByCredentials(accountCredentialsDto);
        return jwtService.generateAuthToken(account.getEmail());
    }

    @Override
    public AuthTokenDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            Account account = findByEmail(jwtService.getEmailFromToken(refreshToken));
            return jwtService.refreshBaseToken(account.getEmail(), refreshToken);
        }
        throw new AuthenticationException("Invalid refresh token");
    }

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
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(()
                -> new GlobalExceptionHandler.ResourceNotFoundException("Account with email " + email + " not found"));
    }

    @Override
    public ResponseEntity<?> registerAccount(AccountDto accountDto) {
        Account newAccount = modelMapper.map(accountDto, Account.class);
        if (accountRepository.existsByEmail(newAccount.getEmail())) {
            throw new GlobalExceptionHandler.ConflictException("Email already exists");
        }
        newAccount.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        newAccount.setStatus(AccountStatus.PENDING);
        newAccount.setRole(AccountRole.CLIENT);
        accountRepository.save(newAccount);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<?> deleteAllAccounts() {
        accountRepository.deleteAll();
        return new ResponseEntity<>("Accounts dropped", HttpStatus.OK);
    }

    private Account findByCredentials(AccountCredentialsDto accountCredentialsDto) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(accountCredentialsDto.getEmail());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (passwordEncoder.matches(accountCredentialsDto.getPassword(), account.getPassword())) {
                return account;
            }
        } else {
            throw new GlobalExceptionHandler.AuthenticationException("Invalid email");
        }
        throw new GlobalExceptionHandler.AuthenticationException("Invalid password");
    }
}
