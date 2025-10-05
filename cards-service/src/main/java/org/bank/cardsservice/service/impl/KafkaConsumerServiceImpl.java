package org.bank.cardsservice.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImpl {

    @KafkaListener(topics = "register_card", groupId = "cards_group")
    public void listen(ConsumerRecord<String, String> record) {
        String email = record.value();
    }

}
