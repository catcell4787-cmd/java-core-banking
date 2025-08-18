package org.example.accountservice.service;

import org.example.accountservice.dto.AccountCredentialsDto;
import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.dto.AuthTokenDto;
import org.example.accountservice.dto.RefreshTokenDto;
import org.example.accountservice.entity.Account;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    AuthTokenDto signIn(AccountCredentialsDto accountCredentialsDto) throws AuthenticationException;

    AuthTokenDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;

    List<AccountDto> findAll();

    ResponseEntity<?> findById(UUID id);

    Account findByEmail(String email);

    ResponseEntity<?> registerAccount(AccountDto account);

    ResponseEntity<?> deleteAllAccounts();
}
