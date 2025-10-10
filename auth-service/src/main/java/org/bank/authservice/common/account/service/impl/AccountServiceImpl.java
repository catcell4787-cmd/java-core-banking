package org.bank.authservice.common.account.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bank.authservice.common.account.dto.AccountCredentialsDTO;
import org.bank.authservice.common.account.dto.AccountDTO;
import org.bank.authservice.common.account.dto.FullDataAccountDTO;
import org.bank.authservice.common.account.entity.Account;
import org.bank.authservice.common.account.redis.service.RoleService;
import org.bank.authservice.common.account.repository.AccountRepository;
import org.bank.authservice.common.account.service.AccountService;
import org.bank.authservice.common.cards.dto.CardDTO;
import org.bank.authservice.common.loans.dto.LoanDTO;
import org.bank.authservice.enums.Role;
import org.bank.authservice.exception.GlobalExceptionHandler;
import org.bank.authservice.feign.CardsFeignClient;
import org.bank.authservice.feign.LoansFeignClient;
import org.bank.authservice.security.jwt.JwtService;
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
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;

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
    public FullDataAccountDTO findByEmail(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isPresent()) {
            return modelMapper.map(optionalAccount.get(), FullDataAccountDTO.class);
        }
        throw new GlobalExceptionHandler.ResourceNotFoundException("Account with email " + email + " not found");
    }

    public ResponseEntity<?> getFullAccountData(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isPresent()) {
            try {
                FullDataAccountDTO fullDataAccountDTO = modelMapper.map(optionalAccount.get(), FullDataAccountDTO.class);
                ResponseEntity<CardDTO> cardsResponse = cardsFeignClient.getCardByCardHolder(email);
                ResponseEntity<LoanDTO> loansResponse = loansFeignClient.getLoanByCardHolder(email);
                fullDataAccountDTO.setCards(cardsResponse.getBody());
                fullDataAccountDTO.setLoans(loansResponse.getBody());
                return ResponseEntity.ok(fullDataAccountDTO);
            } catch (FeignException e) {
                throw new GlobalExceptionHandler.ResourceNotFoundException("Card is not registered");
            }
        }
        throw new GlobalExceptionHandler.ResourceNotFoundException("Account with email " + email + " not found");
    }

    @Override
    public List<Account> findByRole(String role) {
        return roleService.findByRole(role);
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
                String token = jwtService.getRefreshToken(account.getEmail());
                System.out.println(token);
                return ResponseEntity.ok("Logged in successfully");
            } else {
                throw new GlobalExceptionHandler.AuthenticationException("Invalid password");
            }
        }
        throw new GlobalExceptionHandler.AuthenticationException("Email is not registered");
    }
}
