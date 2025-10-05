package org.bank.authservice.service;

import org.bank.authservice.enums.Status;
import org.bank.authservice.enums.Role;
import org.bank.authservice.event.CardEvent;
import org.bank.authservice.model.dto.AccountCredentialsDto;
import org.bank.authservice.model.dto.AccountDto;
import org.bank.authservice.model.dto.AuthTokenDto;
import org.bank.authservice.model.entity.Account;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.util.List;

public interface AccountService {

    AuthTokenDto login(AccountCredentialsDto accountCredentialsDto) throws AuthenticationException;

    ResponseEntity<?> register(AccountDto accountDto, Role role, Status status);

    List<Account> findAll();

    Account findByCredentials(AccountCredentialsDto accountCredentialsDto);

    ResponseEntity<?> updateStatus(String email, AccountDto accountDto);

    Account findByEmail(String email);

    List<Account> findByRole(String role);

    ResponseEntity<?> deleteAccount(String email);

    ResponseEntity<?> registerCard(Account account);
}
