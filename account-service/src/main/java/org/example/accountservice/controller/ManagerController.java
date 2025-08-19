package org.example.accountservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.accountservice.entity.Account;
import org.example.accountservice.role.AccountRole;
import org.example.accountservice.role.AccountStatus;
import org.example.accountservice.service.AccountService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/managers")
public class ManagerController {
    private final AccountService accountService;

    @PostMapping("/signup")
    public Account addAccount(@RequestBody @Valid Account account) {
        return accountService.signUp(account, AccountRole.MANAGER, AccountStatus.ACTIVE);
    }
}
