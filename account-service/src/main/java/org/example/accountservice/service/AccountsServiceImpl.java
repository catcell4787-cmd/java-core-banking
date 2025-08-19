package org.example.accountservice.service;

import jakarta.annotation.PostConstruct;
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

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> signUp(AccountDto accountDto, AccountRole role, AccountStatus status) {
        if (accountRepository.existsByEmail(accountDto.getEmail())) {
            throw new GlobalExceptionHandler.ConflictException("Email already exists");
        }
        Account account = modelMapper.map(accountDto, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setStatus(status);
        account.setRole(role);
        accountRepository.save(account);
        return ResponseEntity.ok(account);
    }

    @Override
    public AuthTokenDto signIn(AccountCredentialsDto accountCredentialsDto)  {
        Account account = findByCredentials(accountCredentialsDto);
        return jwtService.generateAuthToken(account.getEmail());
    }

    @Override
    public List<AccountDto> findAll() {
        return accountRepository.findAll().stream().map(account -> modelMapper.map(account, AccountDto.class))
                .toList();
    }

    @Override
    public AccountDto findByEmail(String email) {
        AccountDto accountDto = modelMapper.map(accountRepository.findByEmail(email), AccountDto.class);
        if  (accountDto == null) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Account with email " + email + " not found");
        }
        return accountDto;
    }

    @Override
    public AuthTokenDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            AccountDto account = findByEmail(jwtService.getEmailFromToken(refreshToken));
            return jwtService.refreshBaseToken(account.getEmail(), refreshToken);
        }
        throw new AuthenticationException("Invalid refresh token");
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
            account.setRole(AccountRole.ADMIN);
            account.setFirstname(admin);
            account.setLastname(admin);
            account.setEmail("admin@admin.com");
            account.setPassword(passwordEncoder.encode(admin));
            account.setStatus(AccountStatus.ACTIVE);
            accountRepository.save(account);
        }
    }
}
