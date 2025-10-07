package org.bank.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bank.authservice.client.CardsClient;
import org.bank.authservice.enums.Role;
import org.bank.authservice.enums.Status;
import org.bank.authservice.exception.GlobalExceptionHandler;
import org.bank.authservice.model.dto.AccountCredentialsDto;
import org.bank.authservice.model.dto.AccountDto;
import org.bank.authservice.model.dto.AuthTokenDto;
import org.bank.authservice.model.dto.CardsDto;
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
    private final KafkaService kafkaService;
    private final CardsClient cardsClient;

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
        throw new GlobalExceptionHandler.ResourceNotFoundException("account with email " + email + " not found");
    }

    @Override
    public List<Account> findByRole(String role) {
        return roleService.findByRole(role);
    }

    @Override
    public ResponseEntity<?> deleteAccount(String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow();
        accountRepository.delete(account);
        roleService.deleteRole(email);
        return ResponseEntity.ok().body("Account deleted successfully");
    }

    @Override
    public ResponseEntity<?> registerCard(String email) {
        if (!accountRepository.existsByEmail(email)) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("account with email " + email + " not found");
        }
        kafkaService.sendMessage("register_card", email);
        log.info("Sending : {}", email);
        return ResponseEntity.ok("Card registration request sent");

    }

    @Override
    public AccountDto getCard(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isPresent()) {
            AccountDto accountDto = modelMapper.map(optionalAccount.get(), AccountDto.class);
            ResponseEntity<CardsDto> cardsResponse = cardsClient.getCardsList(email);
            accountDto.setCardsDto(cardsResponse.getBody());
            return accountDto;
        }
        throw new GlobalExceptionHandler.ResourceNotFoundException("account with email " + email + " not found");
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
        Account account = findByCredentials(accountCredentialsDto);
        return jwtService.generateAuthToken(account.getEmail());
    }

    @Override
    public Account findByCredentials(AccountCredentialsDto accountCredentialsDto) {
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
}
