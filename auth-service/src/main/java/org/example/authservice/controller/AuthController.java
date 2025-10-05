package org.example.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.authservice.enums.AccountStatus;
import org.example.authservice.model.dto.AccountCredentialsDto;
import org.example.authservice.model.dto.AccountDto;
import org.example.authservice.model.dto.AuthTokenDto;
import org.example.authservice.model.entity.Account;
import org.example.authservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
class AuthController {

    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<AuthTokenDto> login(@RequestBody AccountCredentialsDto accountCredentialsDto) {
        try {
            AuthTokenDto authTokenDto = accountService.login(accountCredentialsDto);
            return ResponseEntity.ok(authTokenDto);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(
                accountService.register(
                        accountDto, "CLIENT", AccountStatus.PENDING));
    }

    @GetMapping("/list")
    public List<Account> getAccount() {
        return accountService.findAll();
    }
}
