package org.bank.authservice.model.dto;

import lombok.Data;

@Data
public class CardDto {

    private String cardHolder;
    private String cardNumber;
    private String cardBalance;
    private String cardStatus;
}
