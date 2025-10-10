package org.bank.authservice.common.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bank.authservice.enums.Role;
import org.bank.authservice.common.account.dto.AccountCredentialsDTO;
import org.bank.authservice.common.account.entity.Account;
import org.bank.authservice.common.account.service.AccountService;
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
    public ResponseEntity<?> login(@Valid @RequestBody AccountCredentialsDTO accountCredentialsDto) throws AuthenticationException {
        return ResponseEntity.ok(accountService.login(accountCredentialsDto));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AccountCredentialsDTO accountCredentialsDto) {
        return ResponseEntity.ok(accountService.register(accountCredentialsDto, Role.CLIENT, false));
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getAccount(@PathVariable String email) {
        return ResponseEntity.ok(accountService.findByEmail(email));
    }

    @GetMapping("/list")
    public List<Account> getAccount() {
        return accountService.findAll();
    }

    @DeleteMapping("delete/{email}")
    public ResponseEntity<?> deleteAccount(@Valid @PathVariable String email) {
        accountService.deleteAccount(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }
}
