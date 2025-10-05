package org.bank.cardsservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bank.cardsservice.enums.Status;
import org.bank.cardsservice.event.CardEvent;
import org.bank.cardsservice.model.Card;
import org.bank.cardsservice.repository.CardsRepository;
import org.bank.cardsservice.service.CardsService;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardsServiceImpl implements CardsService {

    private final CardsRepository cardsRepository;

    @Override
    @KafkaListener(topics = "register_card", groupId = "cards_group")
    public ResponseEntity<?> registerCard(ConsumerRecord<String, String> record) {
        if (cardsRepository.existsByCardHolder(record.value())) {
            throw new RuntimeException(record.value() + " already exists");
        }
        Card card = new Card();
        card.setCardHolder(record.value());
        card.setBalance(0);
        card.setStatus(Status.PENDING);
        cardsRepository.save(card);
        return ResponseEntity.ok(card);
    }
}
