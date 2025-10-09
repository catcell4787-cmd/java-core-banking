package org.bank.authservice.feign;

import org.bank.authservice.common.cards.dto.CardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cards-service", url = "http://localhost:8082")
public interface CardsFeignClient {

    @GetMapping("/cards/{email}/getCards")
    ResponseEntity<CardDTO> getCardByCardHolder(@PathVariable String email);

    @PostMapping("/cards/{email}/registerCard")
    ResponseEntity<CardDTO> registerCard(@PathVariable String email);

}
