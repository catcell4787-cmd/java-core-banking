package org.bank.authservice.common.loans.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LoanDTO {

    private String cardNumber;
    private Double amount;
    private Double interestRate;
    private Integer termInMonths;
    private LocalDate issueDate;
    private LocalDate maturityDate;
    private Double currentBalance;
    private String loanStatus;

}
