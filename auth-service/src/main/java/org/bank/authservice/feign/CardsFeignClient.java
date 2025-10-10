package org.bank.authservice.feign;

import org.bank.authservice.common.cards.dto.CardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cards-service")
public interface CardsFeignClient {

    @GetMapping("clients/{email}/cards/getCards")
    ResponseEntity<CardDTO> getCardByCardHolder(@PathVariable String email);

    @PostMapping("clients/{email}/cards/registerCard")
    ResponseEntity<CardDTO> registerCard(@PathVariable String email);

}
