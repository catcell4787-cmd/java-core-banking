package org.example.authservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.authservice.dto.AccountCredentialsDto;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.dto.AuthTokenDto;
import org.example.authservice.dto.RefreshTokenDto;
import org.example.authservice.entity.Account;
import org.example.authservice.exception.GlobalExceptionHandler;
import org.example.authservice.repository.AccountRepository;
import org.example.authservice.security.jwt.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    Logger logger = LogManager.getLogger(AccountsServiceImpl.class);

    @Override
    public ResponseEntity<?> register(AccountDto accountDto) {
        if (accountRepository.existsByEmail(accountDto.getEmail())) {
            throw new GlobalExceptionHandler.ConflictException("Email already exists");
        }
        Account account = modelMapper.map(accountDto, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        logger.info("Account registered successfully", account);
        return ResponseEntity.ok(account);
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
            throw new GlobalExceptionHandler.ResourceNotFoundException("Account with email " + email + " not found");
        }
        return accountDto;
    }

    private Account findByCredentials(org.example.authservice.dto.AccountCredentialsDto accountCredentialsDto) {
        Optional<org.example.authservice.entity.Account> optionalAccount = accountRepository.findByEmail(accountCredentialsDto.getEmail());
        if (optionalAccount.isPresent()) {
            org.example.authservice.entity.Account account = optionalAccount.get();
            if (passwordEncoder.matches(accountCredentialsDto.getPassword(), account.getPassword())) {
                return account;
            } else {
                throw new org.example.authservice.exception.GlobalExceptionHandler.AuthenticationException("Invalid password");
            }
        }
        throw new org.example.authservice.exception.GlobalExceptionHandler.AuthenticationException("Invalid email");
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

//    @PostConstruct
//    public void createAdmin() {
//        String admin = "admin";
//        if (!accountRepository.existsByEmail("admin@admin.com")) {
//            Account account = new Account();
//            account.setRole(AccountRole.ADMIN);
//            account.setEmail("admin@admin.com");
//            account.setPassword(passwordEncoder.encode(admin));
//            account.setStatus(AccountStatus.ACTIVE);
//            accountRepository.save(account);
//        }
//    }
}
