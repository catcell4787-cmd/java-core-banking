package org.example.accountservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.accountservice.dto.AccountCredentialsDto;
import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.dto.AuthTokenDto;
import org.example.accountservice.role.AccountRole;
import org.example.accountservice.role.AccountStatus;
import org.example.accountservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
class AuthController {

    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@Valid @RequestBody AccountDto accountDto) {
        return accountService.register(accountDto, AccountRole.CLIENT, AccountStatus.PENDING);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenDto> signIn(@RequestBody AccountCredentialsDto accountCredentialsDto) {
        try {
            AuthTokenDto authTokenDto = accountService.login(accountCredentialsDto);
            return ResponseEntity.ok(authTokenDto);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
