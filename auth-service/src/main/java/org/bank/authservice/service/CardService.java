package org.bank.authservice.service;

import org.bank.authservice.model.dto.AccountDto;
import org.springframework.http.ResponseEntity;

public interface CardService {
    ResponseEntity<?> registerCard(String email);

    ResponseEntity<AccountDto> getCard(String email);
}
