package org.example.accountservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.dto.AuthTokenDto;
import org.example.accountservice.dto.RefreshTokenDto;
import org.example.accountservice.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AccountService accountService;

    @GetMapping("/accounts/list")
    public List<AccountDto> getAllAccounts() {
        return accountService.findAll();
    }

    @PostMapping("/refresh")
    public AuthTokenDto refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) throws Exception {
        return accountService.refreshToken(refreshTokenDto);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearAllAccounts() {
        return accountService.deleteAllAccounts();
    }
}
