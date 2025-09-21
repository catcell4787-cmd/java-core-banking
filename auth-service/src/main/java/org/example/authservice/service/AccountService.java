package org.example.authservice.service;

import org.example.authservice.model.dto.AccountCredentialsDto;
import org.example.authservice.model.dto.AccountDto;
import org.example.authservice.model.dto.AuthTokenDto;
import org.example.authservice.model.entity.Account;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.util.List;

public interface AccountService {

    AuthTokenDto login(AccountCredentialsDto accountCredentialsDto) throws AuthenticationException;

    AccountDto findByEmail(String email);

    ResponseEntity<?> register(AccountDto accountDto);

    List<Account> findAll();
}
