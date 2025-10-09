package org.bank.authservice.common.cards.dto;

import lombok.Data;

@Data
public class CardDTO {

    private String cardHolder;
    private String cardNumber;
    private String cardBalance;
    private String cardStatus;
}
