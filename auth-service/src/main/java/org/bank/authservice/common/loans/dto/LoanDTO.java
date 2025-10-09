package org.bank.authservice.common.loans.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanDTO {

    private String cardHolder;
    private double currentBalance;
    private double amount;
    private double interestRate;
    private int termInMonths;
    private LocalDate issueDate;
    private LocalDate maturityDate;
}
