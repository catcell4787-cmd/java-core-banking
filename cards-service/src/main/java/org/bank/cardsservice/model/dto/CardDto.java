package org.bank.cardsservice.model.dto;

import lombok.Data;
import org.bank.cardsservice.enums.Status;

@Data
public class CardDto {

    private String cardHolder;
    private String cardNumber;
    private String cardBalance;
    private Status cardStatus;

}
