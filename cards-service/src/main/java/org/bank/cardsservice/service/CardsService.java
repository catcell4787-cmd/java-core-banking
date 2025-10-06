package org.bank.cardsservice.service;

import org.springframework.http.ResponseEntity;

public interface CardsService {
    ResponseEntity<?> registerCard(String record);
}
