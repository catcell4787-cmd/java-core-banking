package org.example.accountservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.role.AccountRole;
import org.example.accountservice.role.AccountStatus;
import org.example.accountservice.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/managers")
public class ManagerController {
    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<?> addAccount(@RequestBody @Valid AccountDto accountDto) {
        return accountService.register(accountDto, AccountRole.MANAGER, AccountStatus.ACTIVE);
    }

    @PatchMapping("/updateStatus/{email}")
    public ResponseEntity<?> updateAccountStatus(@PathVariable String email, @RequestBody AccountDto accountDto, AccountStatus status) {
        return accountService.updateStatus(email, accountDto, status);
    }
}
