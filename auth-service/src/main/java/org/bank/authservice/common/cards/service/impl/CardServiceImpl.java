package org.bank.authservice.common.cards.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bank.authservice.feign.CardsFeignClient;
import org.bank.authservice.exception.GlobalExceptionHandler;
import org.bank.authservice.common.cards.dto.CardDTO;
import org.bank.authservice.common.account.entity.Account;
import org.bank.authservice.common.account.repository.AccountRepository;
import org.bank.authservice.common.cards.service.CardService;
import org.bank.authservice.kafka.KafkaService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {

    private final AccountRepository accountRepository;
    private final KafkaService kafkaService;
    private final CardsFeignClient cardsFeignClient;

    @Override
    public ResponseEntity<?> registerCard(String email) {
        if (!accountRepository.existsByEmail(email)) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Account with email " + email + " not found");
        }
        kafkaService.sendMessage("register_card", email);
        log.info("Sending : {}", email);
        try {
            cardsFeignClient.registerCard(email);
        } catch (FeignException e) {
            throw new GlobalExceptionHandler.ConflictException("Card is already registered");
        }
        return ResponseEntity.ok("Card registration request sent");

    }

    @Override
    public ResponseEntity<?> getCard(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isPresent()) {
            try {
                ResponseEntity<CardDTO> cardsResponse = cardsFeignClient.getCardsList(email);
                return ResponseEntity.ok(cardsResponse.getBody());
            } catch (FeignException e) {
                throw new GlobalExceptionHandler.ResourceNotFoundException("Card is not registered");
            }
        }
        throw new GlobalExceptionHandler.ResourceNotFoundException("Account with email " + email + " not found");
    }
}
