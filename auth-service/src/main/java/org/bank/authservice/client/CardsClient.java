package org.bank.authservice.client;

import org.bank.authservice.model.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cards-service", url = "http://localhost:8082")
public interface CardsClient {

    @GetMapping("/cards/{email}/list")
    ResponseEntity<CardDto> getCardsList(@PathVariable String email);

    @PostMapping("/cards/{email}/registerCard")
    ResponseEntity<CardDto> registerCard(@PathVariable String email);

}
