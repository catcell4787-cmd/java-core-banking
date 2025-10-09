package org.bank.authservice.common.account.dto;

import lombok.Data;
import org.bank.authservice.common.cards.dto.CardDTO;


@Data
public class AccountDTO {

    private String email;
    private String role;
    private CardDTO cards;
    private boolean enabled;
}
