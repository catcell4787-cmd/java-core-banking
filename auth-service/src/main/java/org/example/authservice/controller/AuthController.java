package org.example.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.AccountCredentialsDto;
import org.example.authservice.dto.AuthTokenDto;
import org.example.authservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
class AuthController {

    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<AuthTokenDto> signIn(@RequestBody AccountCredentialsDto accountCredentialsDto) {
        try {
            AuthTokenDto authTokenDto = accountService.login(accountCredentialsDto);
            return ResponseEntity.ok(authTokenDto);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
