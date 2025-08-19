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

    @PostMapping("/signup")
    public ResponseEntity<?> addAccount(@RequestBody @Valid AccountDto accountDto) {
        return accountService.signUp(accountDto, AccountRole.MANAGER, AccountStatus.ACTIVE);
    }
}
