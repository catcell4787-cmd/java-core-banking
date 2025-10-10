package org.bank.authservice.common.cards.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bank.authservice.common.account.dto.FullDataAccountDTO;
import org.bank.authservice.common.loans.dto.LoanDTO;
import org.bank.authservice.feign.CardsFeignClient;
import org.bank.authservice.exception.GlobalExceptionHandler;
import org.bank.authservice.common.cards.dto.CardDTO;
import org.bank.authservice.common.account.entity.Account;
import org.bank.authservice.common.account.repository.AccountRepository;
import org.bank.authservice.common.cards.service.CardService;
import org.bank.authservice.feign.LoansFeignClient;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {

    private final AccountRepository accountRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> registerCard(String email) {
        if (!accountRepository.existsByEmail(email)) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Account with email " + email + " not found");
        }
        try {
            cardsFeignClient.registerCard(email);
            return ResponseEntity.ok("Card registration request sent");
        } catch (FeignException e) {
            throw new GlobalExceptionHandler.ConflictException("Card is already registered");
        }
    }

    @Override
    public ResponseEntity<?> getCard(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isPresent()) {
            try {
                ResponseEntity<CardDTO> cardsResponse = cardsFeignClient.getCardByCardHolder(email);
                return ResponseEntity.ok(cardsResponse.getBody());
            } catch (FeignException e) {
                throw new GlobalExceptionHandler.ResourceNotFoundException("Card is not registered");
            }
        }
        throw new GlobalExceptionHandler.ResourceNotFoundException("Account with email " + email + " not found");
    }

    public ResponseEntity<?> getFullAccountData(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isPresent()) {
            try {
                FullDataAccountDTO fullDataAccountDTO = modelMapper.map(optionalAccount.get(), FullDataAccountDTO.class);
                ResponseEntity<CardDTO> cardsResponse = cardsFeignClient.getCardByCardHolder(email);
                ResponseEntity<LoanDTO> loansResponse = loansFeignClient.getLoanByCardHolder(email);
                fullDataAccountDTO.setCards(cardsResponse.getBody());
                fullDataAccountDTO.setLoans(loansResponse.getBody());
                return ResponseEntity.ok(fullDataAccountDTO);
            } catch (FeignException e) {
                throw new GlobalExceptionHandler.ResourceNotFoundException("Card is not registered");
            }
        }
        throw new GlobalExceptionHandler.ResourceNotFoundException("Account with email " + email + " not found");
    }
}
