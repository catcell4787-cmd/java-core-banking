package org.bank.cardsservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bank.cardsservice.enums.Status;
import org.bank.cardsservice.exception.GlobalExceptionHandler;
import org.bank.cardsservice.model.dto.CardDto;
import org.bank.cardsservice.model.entity.Card;
import org.bank.cardsservice.repository.CardRepository;
import org.bank.cardsservice.service.CardService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;

    @Override
//    @KafkaListener(topics = "register_card", groupId = "cards_group")
    public CardDto registerCard(String getEmail) {
        if (cardRepository.existsByCardHolder(getEmail)) {
            throw new GlobalExceptionHandler.ConflictException("Card is already registered");
        }
            Card card = new Card();
            card.setCardHolder(getEmail);
            card.setCardBalance(0);
            card.setCardStatus(Status.PENDING);
            cardRepository.save(card);
            log.info("Card registered successfully : {}", card);
            return modelMapper.map(card, CardDto.class);
    }

    @Override
    public CardDto getCard(String email) {
        Optional<Card> optionalCard = cardRepository.findByCardHolder(email);
        if (optionalCard.isPresent()) {
            return modelMapper.map(optionalCard.get(), CardDto.class);
        }
        throw new GlobalExceptionHandler.ResourceNotFoundException("You have no any cards registered");
    }
}
