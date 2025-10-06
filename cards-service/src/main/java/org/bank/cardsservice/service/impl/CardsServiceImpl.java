package org.bank.cardsservice.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.bank.cardsservice.enums.Status;
import org.bank.cardsservice.exception.GlobalExceptionHandler;
import org.bank.cardsservice.model.Card;
import org.bank.cardsservice.repository.CardsRepository;
import org.bank.cardsservice.service.CardsService;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardsServiceImpl implements CardsService {

    private final CardsRepository cardsRepository;
    private final KafkaService kafkaService;

    @Override
    @KafkaListener(topics = "register_card", groupId = "cards_group")
    public ResponseEntity<?> registerCard(String getEmail) {
            Card card = new Card();
            card.setCardHolder(getEmail);
            card.setBalance(0);
            card.setStatus(Status.PENDING);
            cardsRepository.save(card);
            log.info("Card registered successfully : {}", card);
            return ResponseEntity.ok(card);
    }
}
