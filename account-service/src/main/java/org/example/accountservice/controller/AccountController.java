package org.example.accountservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        return accountService.findByEmail(email);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody AccountDto account) {
        return accountService.registerAccount(account);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearAllAccounts() {
        return accountService.deleteAllAccounts();
    }
}
