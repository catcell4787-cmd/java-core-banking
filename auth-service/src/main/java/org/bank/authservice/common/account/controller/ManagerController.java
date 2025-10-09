package org.bank.authservice.common.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bank.authservice.enums.Role;
import org.bank.authservice.common.account.dto.AccountCredentialsDTO;
import org.bank.authservice.common.account.dto.AccountDTO;
import org.bank.authservice.common.account.entity.Account;
import org.bank.authservice.common.account.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/managers")
public class ManagerController {

    private final AccountService accountService;

    @GetMapping("/list")
    public List<Account> listManagers() {
        return accountService.findByRole(Role.MANAGER.toString());
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getManager(@PathVariable String email) {
        return ResponseEntity.ok(accountService.findByEmail(email));
    }

    @PostMapping("/hire")
    public ResponseEntity<?> hireManager(@RequestBody @Valid AccountCredentialsDTO accountCredentialsDto) {
        return ResponseEntity.ok(
                accountService.register(accountCredentialsDto, Role.MANAGER, true));
    }

    @PatchMapping("/{email}/updateStatus")
    public ResponseEntity<?> updateStatus(@PathVariable String email, @Valid @RequestBody AccountDTO accountDto) {
        return ResponseEntity.ok(
                accountService.updateStatus(email, accountDto));
    }
}
