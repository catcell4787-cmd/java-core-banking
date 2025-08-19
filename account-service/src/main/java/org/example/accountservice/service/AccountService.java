package org.example.accountservice.service;

import org.example.accountservice.dto.AccountCredentialsDto;
import org.example.accountservice.dto.AccountDto;
import org.example.accountservice.dto.AuthTokenDto;
import org.example.accountservice.dto.RefreshTokenDto;
import org.example.accountservice.role.AccountRole;
import org.example.accountservice.role.AccountStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.util.List;

public interface AccountService {
    AuthTokenDto login(AccountCredentialsDto accountCredentialsDto) throws AuthenticationException;

    AuthTokenDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;

    List<AccountDto> findAll();

    AccountDto findByEmail(String email);

    ResponseEntity<?> register(AccountDto accountDto, AccountRole role, AccountStatus status);

    ResponseEntity<?> edit(String email, AccountDto accountDto);

    ResponseEntity<?> updateStatus(String email, AccountDto accountDto, AccountStatus status);

    ResponseEntity<?> deleteAllAccounts();
}
