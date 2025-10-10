package org.bank.authservice.common.account.service;

import org.bank.authservice.common.account.dto.FullDataAccountDTO;
import org.bank.authservice.enums.Role;
import org.bank.authservice.common.account.dto.AccountCredentialsDTO;
import org.bank.authservice.common.account.dto.AccountDTO;
import org.bank.authservice.common.account.entity.Account;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.util.List;

public interface AccountService {

    ResponseEntity<?> register(AccountCredentialsDTO accountCredentialsDto, Role role, boolean status);

    ResponseEntity<?> login(AccountCredentialsDTO accountCredentialsDto) throws AuthenticationException;

    List<Account> findAll();

    ResponseEntity<?> updateStatus(String email, AccountDTO accountDto);

    FullDataAccountDTO findByEmail(String email);

    List<Account> findByRole(String role);

    void deleteAccount(String email);
}
