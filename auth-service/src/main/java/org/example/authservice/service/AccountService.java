package org.example.authservice.service;

import org.example.authservice.dto.AccountCredentialsDto;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.dto.AuthTokenDto;
import org.example.authservice.dto.RefreshTokenDto;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;

public interface AccountService {

    AuthTokenDto login(AccountCredentialsDto accountCredentialsDto) throws AuthenticationException;

    AccountDto findByEmail(String email);

    AuthTokenDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;

    ResponseEntity<?> register(AccountDto accountDto);


}
