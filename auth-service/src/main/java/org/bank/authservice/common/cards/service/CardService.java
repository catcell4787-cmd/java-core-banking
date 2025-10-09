package org.bank.authservice.common.cards.service;

import org.bank.authservice.common.account.dto.AccountDTO;
import org.springframework.http.ResponseEntity;

public interface CardService {
    ResponseEntity<?> registerCard(String email);

    ResponseEntity<AccountDTO> getCard(String email);
}
