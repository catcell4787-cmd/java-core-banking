package org.bank.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.bank.authservice.enums.Status;
import org.bank.authservice.enums.Role;
import org.bank.authservice.model.dto.AccountDto;
import org.bank.authservice.model.entity.Account;
import org.bank.authservice.service.AccountService;
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
    public ResponseEntity<?> hireManager(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(
                accountService.register(accountDto, Role.MANAGER, Status.ACTIVE));
    }

    @PatchMapping("/{email}/updateStatus")
    public ResponseEntity<?> updateStatus(@PathVariable String email,  @RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(
                accountService.updateStatus(email, accountDto));
    }
}
