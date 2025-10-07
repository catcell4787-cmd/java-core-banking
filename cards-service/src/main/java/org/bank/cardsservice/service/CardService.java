package org.bank.cardsservice.service;

import org.bank.cardsservice.model.dto.CardDto;
import org.springframework.http.ResponseEntity;

public interface CardService {
    ResponseEntity<?> registerCard(String record);
    CardDto getCard(String email);
}
