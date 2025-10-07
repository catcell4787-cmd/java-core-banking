package org.bank.cardsservice.controller;

import lombok.RequiredArgsConstructor;
import org.bank.cardsservice.model.dto.CardDto;
import org.bank.cardsservice.service.CardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @GetMapping("/{email}/list")
    public CardDto getCards(@PathVariable String email) {
        return cardService.getCard(email);
    }
}
