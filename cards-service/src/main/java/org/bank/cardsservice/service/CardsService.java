package org.bank.cardsservice.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bank.cardsservice.event.CardEvent;
import org.bank.cardsservice.model.Card;
import org.springframework.http.ResponseEntity;

public interface CardsService {
    ResponseEntity<?> registerCard(ConsumerRecord<String, String> record);
}
