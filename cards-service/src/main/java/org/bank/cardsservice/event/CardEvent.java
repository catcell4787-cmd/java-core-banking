package org.bank.cardsservice.event;

import lombok.Data;

import java.io.Serializable;

@Data
public class CardEvent implements Serializable {
    private String accountEmail;
}
