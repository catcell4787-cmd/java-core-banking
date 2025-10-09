package org.bank.loansservice.model.dto;

import lombok.Data;

@Data
public class LoanDTO {

    private String cardHolder;
    private double amount;
    private double termInMonths;

}
