package org.bank.cardsservice.controller;

import lombok.RequiredArgsConstructor;
import org.bank.cardsservice.model.dto.CardDto;
import org.bank.cardsservice.service.CardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class CardController {

    private final CardService cardService;

    @GetMapping("/{email}/cards/getCards")
    public CardDto getCards(@PathVariable String email) {
        return cardService.getCard(email);
    }

    @PostMapping("/{email}/cards/registerCard")
    public CardDto registerCard(@PathVariable String email) {
        return cardService.registerCard(email);
    }
}
