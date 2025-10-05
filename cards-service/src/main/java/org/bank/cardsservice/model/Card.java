package org.bank.cardsservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.bank.cardsservice.enums.Status;

@Entity
@Data
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "balance")
    private double balance;
    @Column(name = "status")
    private Status status;
    @Column(name = "cardHolder")
    private String cardHolder;
}
