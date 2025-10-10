package org.bank.authservice.common.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bank.authservice.common.account.dto.AccountCredentialsDTO;
import org.bank.authservice.common.account.service.AccountService;
import org.bank.authservice.enums.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
class AuthController {

    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AccountCredentialsDTO accountCredentialsDto) throws AuthenticationException {
        return ResponseEntity.ok(accountService.login(accountCredentialsDto));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AccountCredentialsDTO accountCredentialsDto) {
        return ResponseEntity.ok(accountService.register(accountCredentialsDto, Role.CLIENT, false));
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }
}
