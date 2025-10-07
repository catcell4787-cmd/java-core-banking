package org.bank.authservice.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bank.authservice.client.CardsClient;
import org.bank.authservice.exception.GlobalExceptionHandler;
import org.bank.authservice.model.dto.AccountDto;
import org.bank.authservice.model.dto.CardsDto;
import org.bank.authservice.model.entity.Account;
import org.bank.authservice.repository.AccountRepository;
import org.bank.authservice.service.CardService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {

    private final AccountRepository accountRepository;
    private final KafkaService kafkaService;
    private final ModelMapper modelMapper;
    private final CardsClient cardsClient;

    @Override
    public ResponseEntity<?> registerCard(String email) {
        if (!accountRepository.existsByEmail(email)) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("account with email " + email + " not found");
        }
        kafkaService.sendMessage("register_card", email);
        log.info("Sending : {}", email);
        return ResponseEntity.ok("Card registration request sent");

    }

    @Override
    public ResponseEntity<AccountDto> getCard(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isPresent()) {
            AccountDto accountDto = modelMapper.map(optionalAccount.get(), AccountDto.class);
            try {
                ResponseEntity<CardsDto> cardsResponse = cardsClient.getCardsList(email);
                accountDto.setCardsDto(cardsResponse.getBody());
                return ResponseEntity.ok(accountDto);
            } catch (FeignException e) {
                throw new GlobalExceptionHandler.ResourceNotFoundException("Card is not registered");
            }
        }
        throw new GlobalExceptionHandler.ResourceNotFoundException("Account with email " + email + " not found");
    }
}
