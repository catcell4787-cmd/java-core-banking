package org.bank.cardsservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.bank.cardsservice.model.Card;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg) {
        kafkaTemplate.send("register_card", msg);
    }
}
