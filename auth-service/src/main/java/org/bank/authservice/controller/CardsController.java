package org.bank.authservice.controller;
import lombok.RequiredArgsConstructor;
import org.bank.authservice.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardsController {
    private final AccountService accountService;

    @PostMapping("/{email}/registerCard")
    public ResponseEntity<?> registerCard(@PathVariable String email) {
        return ResponseEntity.ok(accountService.registerCard(email));
    }
}
