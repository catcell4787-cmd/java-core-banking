package org.bank.cardsservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bank.cardsservice.enums.Status;
import org.bank.cardsservice.model.Card;
import org.bank.cardsservice.model.dto.CardDto;
import org.bank.cardsservice.repository.CardRepository;
import org.bank.cardsservice.service.CardService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;

    @Override
    @KafkaListener(topics = "register_card", groupId = "cards_group")
    public ResponseEntity<?> registerCard(String getEmail) {
            Card card = new Card();
            card.setCardHolder(getEmail);
            card.setBalance(0);
            card.setStatus(Status.PENDING);
            cardRepository.save(card);
            log.info("Card registered successfully : {}", card);
            return ResponseEntity.ok(card);
    }

    @Override
    public CardDto getCard(String email) {
        Card card = cardRepository.findByCardHolder(email);
        return modelMapper.map(card, CardDto.class);
    }
}
