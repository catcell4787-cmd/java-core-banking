package org.example.accountservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.accountservice.entity.Account;
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
    public List<Account> findAll() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        return accountService.findById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody Account account) {
        return accountService.registerAccount(account);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateAccount(@Valid @PathVariable UUID id, @RequestBody Account account) {
        return accountService.editById(id, account);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable UUID id) {
        return accountService.deleteById(id);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearAllAccounts() {
        return accountService.deleteAllAccounts();
    }
}
