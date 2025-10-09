package org.bank.authservice.common.cards.controller;
import lombok.RequiredArgsConstructor;
import org.bank.authservice.common.cards.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/{email}/registerCard")
    public ResponseEntity<?> registerCard(@PathVariable String email) {
        return ResponseEntity.ok(cardService.registerCard(email));
    }

    @GetMapping("/{email}/getCards")
    public ResponseEntity<?> getCards(@PathVariable String email) {
        return ResponseEntity.ok(cardService.getCard(email));
    }
}
