package org.bank.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendEmail(String email) {
        kafkaTemplate.send("register_card", email);
    }
}
