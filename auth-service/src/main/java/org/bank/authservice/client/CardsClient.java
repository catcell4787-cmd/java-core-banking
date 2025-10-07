package org.bank.authservice.client;

import org.bank.authservice.model.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cards-service", url = "http://localhost:8082")
public interface CardsClient {

    @GetMapping("/cards/{email}/list")
    ResponseEntity<CardsDto> getCardsList(@PathVariable String email);

}
