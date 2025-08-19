package org.example.accountservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.role.AccountRole;
import org.example.accountservice.role.AccountStatus;
import org.example.accountservice.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final AccountService accountService;

    @GetMapping("/list")
    public List<AccountDto> getAllClients() {
        return accountService.findAllByRole(AccountRole.CLIENT);
    }

    @GetMapping("/{email}")
    public AccountDto findByEmail(@PathVariable String email) {
        return accountService.findByEmail(email);
    }

    @PutMapping("/edit/{email}")
    public ResponseEntity<?> editAccount(@PathVariable String email, @Valid @RequestBody AccountDto accountDto) {
        return accountService.edit(email, accountDto);
    }

    @PatchMapping("/updateStatus/{email}")
    public ResponseEntity<?> updateAccountStatus(@PathVariable String email, @RequestBody AccountDto accountDto, AccountStatus status) {
        return accountService.updateStatus(email, accountDto, status);
    }
}
