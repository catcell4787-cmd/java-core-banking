package org.bank.loansservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_holder")
    private String cardHolder;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "amount")
    private double amount;                  // Сумма кредита

    @Column(name = "current_balance")
    private double currentBalance;          // Текущий остаток задолженности

    @Column(name = "interestRate")
    private double interestRate;            // Годовая процентная ставка

    @Column(name = "term", scale = 2)
    private double termInMonths;            // Срок кредита в месяцах

    @Column(name = "issue_date")
    private LocalDate issueDate;            // Дата оформления кредита

    @Column(name = "maturity_date")
    private LocalDate maturityDate;         // Дата погашения кредита

}
