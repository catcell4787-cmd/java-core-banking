package org.bank.cardsservice.service;

import org.bank.cardsservice.model.dto.CardDto;

public interface CardService {
    CardDto registerCard(String record);
    CardDto getCard(String email);
}
