package org.example.authservice.service;

import org.example.authservice.enums.AccountStatus;
import org.example.authservice.enums.Role;
import org.example.authservice.model.dto.AccountCredentialsDto;
import org.example.authservice.model.dto.AccountDto;
import org.example.authservice.model.dto.AuthTokenDto;
import org.example.authservice.model.entity.Account;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.util.List;

public interface AccountService {

    AuthTokenDto login(AccountCredentialsDto accountCredentialsDto) throws AuthenticationException;

    ResponseEntity<?> register(AccountDto accountDto, Role role, AccountStatus status);

    List<Account> findAll();

    Account findByCredentials(AccountCredentialsDto accountCredentialsDto);

    ResponseEntity<?> updateStatus(String email, AccountDto accountDto);

    Account findByEmail(String email);

    List<Account> findByRole(String role);

    ResponseEntity<?> deleteAccount(String email);
}
