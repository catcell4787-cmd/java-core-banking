package org.bank.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bank.authservice.enums.Status;
import org.bank.authservice.enums.Role;
import org.bank.authservice.model.dto.AccountCredentialsDto;
import org.bank.authservice.model.dto.AccountDto;
import org.bank.authservice.model.dto.AuthTokenDto;
import org.bank.authservice.model.entity.Account;
import org.bank.authservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
class AuthController {

    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<AuthTokenDto> login(@RequestBody AccountCredentialsDto accountCredentialsDto) {
        try {
            AuthTokenDto authTokenDto = accountService.login(accountCredentialsDto);
            return ResponseEntity.ok(authTokenDto);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(
                accountService.register(
                        accountDto, Role.CLIENT, Status.PENDING));
    }

    @GetMapping("/list")
    public List<Account> getAccount() {
        return accountService.findAll();
    }

    @DeleteMapping("delete/{email}")
    public ResponseEntity<?> deleteAccount(@Valid @PathVariable String email) {
        accountService.deleteAccount(email);
        return ResponseEntity.ok().build();
    }
}
