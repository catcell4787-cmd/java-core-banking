package org.bank.authservice.common.account.dto;

import lombok.Data;
import org.bank.authservice.common.cards.dto.CardDTO;
import org.bank.authservice.common.loans.dto.LoanDTO;


@Data
public class AccountDTO {

    private String email;
    private String role;
    private CardDTO cards;
    private LoanDTO loans;
    private boolean enabled;
}
