package org.bank.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bank.authservice.enums.Role;
import org.bank.authservice.model.dto.AccountCredentialsDto;
import org.bank.authservice.model.dto.AccountDto;
import org.bank.authservice.model.entity.Account;
import org.bank.authservice.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientsController {

    private final AccountService accountService;

    @GetMapping("/list")
    public List<Account> getClients() {
        return accountService.findByRole(Role.CLIENT.toString());
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getClient(@Valid @PathVariable String email) {
        return ResponseEntity.ok(accountService.findByEmail(email));
    }

    @PostMapping("/addClient")
    public ResponseEntity<?> addClient(@Valid @RequestBody AccountCredentialsDto accountCredentialsDto) {
        return ResponseEntity.ok(
                accountService.register(accountCredentialsDto, Role.CLIENT, true)
        );
    }

    @PatchMapping("/{email}/updateStatus")
    public ResponseEntity<?> updateStatus(@PathVariable String email, @Valid @RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountService.updateStatus(email, accountDto));
    }
}
