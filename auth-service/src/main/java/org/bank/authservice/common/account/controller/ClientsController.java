package org.bank.authservice.common.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bank.authservice.common.account.dto.AccountCredentialsDTO;
import org.bank.authservice.common.account.dto.AccountDTO;
import org.bank.authservice.common.account.entity.Account;
import org.bank.authservice.common.account.service.AccountService;
import org.bank.authservice.enums.Role;
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
        return ResponseEntity.ok(accountService.getFullAccountData(email));
    }

    @PostMapping("/addClient")
    public ResponseEntity<?> addClient(@Valid @RequestBody AccountCredentialsDTO accountCredentialsDto) {
        return ResponseEntity.ok(
                accountService.register(accountCredentialsDto, Role.CLIENT, true)
        );
    }

    @PatchMapping("/{email}/updateStatus")
    public ResponseEntity<?> updateStatus(@PathVariable String email, @Valid @RequestBody AccountDTO accountDto) {
        return ResponseEntity.ok(accountService.updateStatus(email, accountDto));
    }
}
