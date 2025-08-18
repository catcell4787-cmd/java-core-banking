package org.example.accountservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.accountservice.dto.AccountCredentialsDto;
import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.dto.AuthTokenDto;
import org.example.accountservice.dto.RefreshTokenDto;
import org.example.accountservice.entity.Account;
import org.example.accountservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
class AccountController {

    private final AccountService accountService;

    @GetMapping("/all")
    public List<AccountDto> findAll() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        return accountService.findById(id);
    }

    @GetMapping("/email/{email}")
    public Account findByEmail(@PathVariable String email) {
        return accountService.findByEmail(email);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody AccountDto account) {
        return accountService.registerAccount(account);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthTokenDto> signIn(@RequestBody AccountCredentialsDto accountCredentialsDto) {
        try {
            AuthTokenDto authTokenDto = accountService.signIn(accountCredentialsDto);
            return ResponseEntity.ok(authTokenDto);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/refresh")
    public AuthTokenDto refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) throws Exception {
        return accountService.refreshToken(refreshTokenDto);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearAllAccounts() {
        return accountService.deleteAllAccounts();
    }
}
