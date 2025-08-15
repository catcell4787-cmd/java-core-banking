package org.example.accountservice.service;
import org.example.accountservice.dto.AccountDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    List<AccountDto> findAll();
    ResponseEntity<?> findById(UUID id);
    ResponseEntity<?> registerAccount(AccountDto accountDto);
    ResponseEntity<?> editById(UUID id, AccountDto accountDto);
    ResponseEntity<?> deleteById(UUID id);
    ResponseEntity<?> deleteAllAccounts();
}
