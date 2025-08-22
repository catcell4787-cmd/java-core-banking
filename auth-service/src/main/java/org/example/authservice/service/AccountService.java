package org.example.authservice.service;

import org.example.authservice.dto.AccountCredentialsDto;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.dto.AuthTokenDto;
import org.example.authservice.dto.RefreshTokenDto;
import org.example.authservice.role.AccountRole;
import org.example.authservice.role.AccountStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;

public interface AccountService {
    ResponseEntity<?> register(AccountDto accountDto, AccountRole role, AccountStatus status);

    AuthTokenDto login(AccountCredentialsDto accountCredentialsDto) throws AuthenticationException;

    AccountDto findByEmail(String email);

    AuthTokenDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;


}
