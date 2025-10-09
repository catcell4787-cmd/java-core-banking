package org.bank.authservice.common.account.dto;

import lombok.Data;
import org.bank.authservice.common.cards.dto.CardDTO;
import org.bank.authservice.common.loans.dto.LoanDTO;


@Data
public class FullDataAccountDTO {

    private String email;
    private String role;
    private boolean enabled;
    private CardDTO cards;
    private LoanDTO loans;

}
