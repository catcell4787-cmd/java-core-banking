package org.example.accountservice.service;

import org.example.accountservice.dto.AccountCredentialsDto;
import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.dto.AuthTokenDto;
import org.example.accountservice.dto.RefreshTokenDto;
import org.example.accountservice.entity.Account;
import org.example.accountservice.role.AccountRole;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.util.List;

public interface AccountService {
    AuthTokenDto signIn(AccountCredentialsDto accountCredentialsDto) throws AuthenticationException;

    AuthTokenDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;

    List<AccountDto> findAll();

    Account findByEmail(String email);

    ResponseEntity<?> registerAccount(AccountDto account, AccountRole role);

    ResponseEntity<?> deleteAllAccounts();
}
