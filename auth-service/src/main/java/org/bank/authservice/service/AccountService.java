package org.bank.authservice.service;

import org.bank.authservice.enums.Role;
import org.bank.authservice.model.dto.AccountCredentialsDto;
import org.bank.authservice.model.dto.AccountDto;
import org.bank.authservice.model.entity.Account;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.util.List;

public interface AccountService {

    ResponseEntity<?> register(AccountCredentialsDto accountCredentialsDto, Role role, boolean status);

    ResponseEntity<?> login(AccountCredentialsDto accountCredentialsDto) throws AuthenticationException;

    List<Account> findAll();

    ResponseEntity<?> updateStatus(String email, AccountDto accountDto);

    ResponseEntity<AccountDto> findByEmail(String email);

    List<Account> findByRole(String role);

    void deleteAccount(String email);
}
