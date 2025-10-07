package org.bank.authservice.model.dto;

import lombok.Data;

@Data
public class CardsDto {

    private String cardHolder;
    private String cardNumber;
    private String balance;
}
