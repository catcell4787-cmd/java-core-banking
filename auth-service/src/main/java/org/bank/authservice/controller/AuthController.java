package org.bank.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bank.authservice.enums.Role;
import org.bank.authservice.enums.Status;
import org.bank.authservice.model.dto.AccountCredentialsDto;
import org.bank.authservice.model.entity.Account;
import org.bank.authservice.service.AccountService;
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
    public ResponseEntity<?> login(@Valid @RequestBody AccountCredentialsDto accountCredentialsDto) throws AuthenticationException {
        return ResponseEntity.ok(accountService.login(accountCredentialsDto));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AccountCredentialsDto accountCredentialsDto) {
        return ResponseEntity.ok(accountService.register(accountCredentialsDto, Role.CLIENT, Status.PENDING));
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
}
