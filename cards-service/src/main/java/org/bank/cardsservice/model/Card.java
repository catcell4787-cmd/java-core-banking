package org.bank.cardsservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.bank.cardsservice.enums.Status;
import org.bank.cardsservice.utils.BankCardNumberGenerator;

@Entity
@Data
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "balance")
    private double balance;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "cardHolder")
    private String cardHolder;

    @Column(name = "card_number")
    private String cardNumber;

    @PrePersist
    @PreUpdate
    private void generateCardNumber() {
        BankCardNumberGenerator bankCardNumberGenerator = new BankCardNumberGenerator();
        cardNumber = bankCardNumberGenerator.generateBankCardNumber("4");
    }
}
