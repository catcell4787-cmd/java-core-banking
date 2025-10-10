package org.bank.authservice.common.cards.service;

import org.springframework.http.ResponseEntity;

public interface CardService {
    ResponseEntity<?> registerCard(String email);
    ResponseEntity<?> getCard(String email);
}
