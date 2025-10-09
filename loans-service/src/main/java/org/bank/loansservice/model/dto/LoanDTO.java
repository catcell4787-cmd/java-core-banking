package org.bank.loansservice.model.dto;

import lombok.Data;

@Data
public class LoanDTO {

    private String cardNumber;
    private double amount;
    private double termInMonths;

}
